using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using FluentScheduler;
using FoodServiceAPI.Database;
using FoodServiceAPI.Models;

namespace FoodServiceAPI.Jobs
{
    public class SessionTokenMaintainence : IJob
    {
        private FoodContext dbContext;

        public SessionTokenMaintainence(FoodContext dbContext)
        {
            this.dbContext = dbContext;
        }

        public void Execute()
        {
            dbContext.Sessions.RemoveRange(dbContext.Sessions.Where(s => s.expires <= DateTime.UtcNow));
            dbContext.SaveChanges();
        }
    }

    public class PackageNotifyJob : IJob
    {
        private DbContextOptions dbOptions;
        private int pid;
        private int secInterval;
        private int numNotify;

        private struct QueueInfo
        {
            public int cid { get; set; }
            public bool paying { get; set; }
            public DateTime? claimed { get; set; }
        };

        public PackageNotifyJob(DbContextOptions dbOptions, int pid, int secInterval, int numNotify)
        {
            this.dbOptions = dbOptions;
            this.pid = pid;
            this.secInterval = secInterval;
            this.numNotify = numNotify;
        }

        public void Execute()
        {
            using(FoodContext dbContext = new FoodContext(dbOptions))
            {

            Package package = dbContext.Find<Package>(pid);

            if (package == null || package.claimed != null)
                return; // Package doesn't exist or is claimed; stop notifying

            var query =
                // From clients without notice for this package
                from c in dbContext.Clients
                where !(
                    from c2 in dbContext.Clients
                    from n in dbContext.Notices.Where(n => n.cid == c2.cid && n.pid == package.pid)
                    select c2.cid
                ).Contains(c.cid)
                // Joined with each client's latest-claimed-package's info (or null if none claimed)
                from p in dbContext.Packages.
                    Where(p => p.claimer_cid == c.cid).
                    DefaultIfEmpty().
                    OrderByDescending(p => p.claimed).
                    Take(1)
                // Retrieve this data
                select new QueueInfo()
                {
                    cid = c.cid,
                    paying = c.paying,
                    claimed = p.claimed
                };

            // If package is priced, exclude non-paying clients
            if (package.price != 0.0m)
                query = query.Where(c => c.paying == true);

            /*
            Sort remaining clients with these preferences:
            1. No claimed package or older claim
            2. Older client (FIXME: assuming lower cid means older; need 'created' DATETIME field)
            */
            query = query.OrderBy(c => c.claimed ?? DateTime.MinValue).ThenBy(c => c.cid);

            if (!NotifyClients(dbContext, query))
                return; // All viable clients notified

            // More viable clients exist, reschedule this job
            JobManager.AddJob(
                this,
                s => s.ToRunOnceIn(secInterval).Seconds()
            );
            
            }
        }

        // Returns true if more clients can be notified
        private bool NotifyClients(FoodContext dbContext, IQueryable<QueueInfo> query)
        {
            // Retrieve one more than the number to notify
            List<QueueInfo> queue = query.Take(numNotify + 1).ToList();

            if (queue.Count <= 0)
                return false; // No viable clients; notification done

            for(int i = 0; i < Math.Min(numNotify, queue.Count); i++)
            {
                QueueInfo q = queue[i];
                dbContext.Notices.Add(new Notice { cid = q.cid, pid = this.pid });
            }

            dbContext.SaveChanges();

            return queue.Count > numNotify;
        }
    }
}