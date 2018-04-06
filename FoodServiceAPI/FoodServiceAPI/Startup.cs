using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;

using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.IdentityModel.Tokens;
using Microsoft.EntityFrameworkCore;
using System.Text;
using FoodServiceAPI.Authentication;
using FoodServiceAPI.Database;

namespace FoodServiceAPI
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            ConfigureAuthentication(services);
            ConfigureAuthorization(services);
            ConfigureDatabase(services);
            services.AddMvc();
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IHostingEnvironment env)
        {
            // FIXME: Add global RequireHttps attribute to all routes when not debugging
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }

            app.UseAuthentication();
            app.UseMvc();
        }

        public void ConfigureAuthentication(IServiceCollection services)
        {
            AuthenticationBuilder authn = services.AddAuthentication(cfg =>
            {
                cfg.DefaultScheme = JwtBearerDefaults.AuthenticationScheme;
            });

            authn.AddJwtBearer(JwtBearerDefaults.AuthenticationScheme, cfg =>
            {
                cfg.Events = new JwtBearerEvents();
                cfg.Events.OnTokenValidated = new EventHandlers().ValidateTokenSession;

                cfg.TokenValidationParameters = new TokenValidationParameters()
                {
                    ValidateIssuer = true,
                    ValidateAudience = true,
                    ValidateLifetime = true,
                    ValidateIssuerSigningKey = true,
                    ValidIssuer = "http://localhost:50576/", // FIXME: Don't hardcode (in login route, too)
                    ValidAudience = "http://localhost:50576/",

                    // FIXME: Real secret key that's not stored anywhere in the code
                    IssuerSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes("secretsecretsupersecret"))
                };
            });

            authn.AddScheme<PostAuthOptions, PostAuthHandler>(PostAuthDefaults.AuthenticationScheme, null);
        }

        /* Registers [Authorize] policies that are selected for each route to enforce
         * authentication type. e.g. [Authorize("Session")] */
        public void ConfigureAuthorization(IServiceCollection services)
        {
            services.AddAuthorization(cfg =>
            {
                /*
                    This policy requires a valid bearer token in the HTTP Authorization header
                    that is tied to an existing session_data.

                    Custom retrieved claims:
                    "sid" - session ID
                    "uid" - user ID
                    "created" - UTC time token was created
                    "bid" - business ID if applicable
                    "cid" - client ID if applicable
                */

                cfg.AddPolicy("Session", policy =>
                {
                    policy.AddAuthenticationSchemes(JwtBearerDefaults.AuthenticationScheme).
                        RequireAuthenticatedUser();
                });

                /*
                    Like Session but requires a bid claim
                */

                cfg.AddPolicy("SessionBusiness", policy =>
                {
                    policy.AddAuthenticationSchemes(JwtBearerDefaults.AuthenticationScheme).
                        RequireAuthenticatedUser().
                        RequireClaim("bid");
                });

                /*
                    Like Session but requires a cid claim
                */

                cfg.AddPolicy("SessionClient", policy =>
                {
                    policy.AddAuthenticationSchemes(JwtBearerDefaults.AuthenticationScheme).
                        RequireAuthenticatedUser().
                        RequireClaim("cid");
                });

                /*
                    This policy requires a username and password in POST tied to a user_data.

                    Custom retrieved claims:
                    "uid" - user ID
                    "username"
                */

                cfg.AddPolicy("UserPass", policy =>
                {
                    policy.AddAuthenticationSchemes(PostAuthDefaults.AuthenticationScheme).
                        RequireAuthenticatedUser();
                });
            });
        }

        public void ConfigureDatabase(IServiceCollection services)
        {
            Action<DbContextOptionsBuilder> buildAction = optionsBuilder =>
            { optionsBuilder.UseMySQL(Configuration.GetConnectionString("FoodDatabase")); };

            services.AddDbContext<FoodContext>(buildAction);
        }
    }
}