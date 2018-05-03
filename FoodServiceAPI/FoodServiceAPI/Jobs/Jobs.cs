using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
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
}

