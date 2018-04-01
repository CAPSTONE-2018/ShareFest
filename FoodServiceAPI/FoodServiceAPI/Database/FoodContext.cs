using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using FoodServiceAPI.Models;

namespace FoodServiceAPI.Database
{
    public class FoodContext : DbContext
    {
        public DbSet<UserData> Users { get; set; }
        public DbSet<SessionData> Sessions { get; set; }
        public DbSet<Business> Businesses { get; set; }
        public DbSet<Client> Clients { get; set; }
        public DbSet<Package> Packages { get; set; }
        public DbSet<Notice> Notices { get; set; }

        public FoodContext(DbContextOptions options)
            : base(options)
        { }

        protected override void OnModelCreating(ModelBuilder builder)
        {
            /*
             * This function and the model classes are hand-written since the Scaffold-DbContext
             * command of the MySql.Data.EntityFrameworkCore.Design package (which supposedly
             * generates this code based on the database schema) is either not implemented or is
             * bugged.
             
             * If the database changes, this code and/or the models must be updated.
             */

            EntityTypeBuilder<UserData> userDataBuilder = builder.Entity<UserData>();
            userDataBuilder.ToTable("user_data");
            userDataBuilder.HasKey(u => u.uid);

            EntityTypeBuilder<SessionData> sessionDataBuilder = builder.Entity<SessionData>();
            sessionDataBuilder.ToTable("session_data");
            sessionDataBuilder.HasKey(s => s.sid);
            sessionDataBuilder.HasOne(s => s.User).WithMany(u => u.Sessions)
                .HasForeignKey(s => s.uid);

            EntityTypeBuilder<Business> businessBuilder = builder.Entity<Business>();
            businessBuilder.ToTable("business");
            businessBuilder.HasKey(b => b.bid);
            businessBuilder.HasOne(b => b.User).WithOne(u => u.Business)
                .HasForeignKey<Business>(b => b.uid);

            EntityTypeBuilder<Client> clientBuilder = builder.Entity<Client>();
            clientBuilder.ToTable("client");
            clientBuilder.HasKey(c => c.cid);
            clientBuilder.HasOne(c => c.User).WithOne(u => u.Client)
                .HasForeignKey<Client>(c => c.uid);

            EntityTypeBuilder<Package> packageBuilder = builder.Entity<Package>();
            packageBuilder.ToTable("package");
            packageBuilder.HasKey(p => p.pid);
            packageBuilder.HasOne(p => p.Owner).WithMany(b => b.OwnedPackages)
                .HasForeignKey(p => p.owner_bid);
            packageBuilder.HasOne(p => p.Claimer).WithMany(c => c.ClaimedPackages)
                .HasForeignKey(p => p.claimer_cid)
                .IsRequired(false);

            EntityTypeBuilder<Notice> noticeBuilder = builder.Entity<Notice>();
            noticeBuilder.ToTable("notice");
            noticeBuilder.HasKey(n => new { n.cid, n.pid });
            noticeBuilder.HasOne(n => n.Client).WithMany(c => c.Notices)
                .HasForeignKey(n => n.cid);
            noticeBuilder.HasOne(n => n.Package).WithMany(p => p.Notices)
                .HasForeignKey(n => n.pid);
        }
    }
}
