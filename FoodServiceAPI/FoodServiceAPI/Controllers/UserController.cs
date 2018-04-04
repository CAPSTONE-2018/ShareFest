using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

using Microsoft.AspNetCore.Authorization;
using Microsoft.IdentityModel.Tokens;
using Microsoft.EntityFrameworkCore;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using FoodServiceAPI.Authentication;
using FoodServiceAPI.Database;
using FoodServiceAPI.Models;

namespace FoodServiceAPI.Controllers
{
    [Produces("application/json")]
    [Route("api/user")]
    public class UserController : Controller
    {
        private readonly FoodContext dbContext;

        public class UserRegistration
        {
            public string username { get; set; }
            public string password { get; set; }
            public string email { get; set; }
            public string address { get; set; }
            public string zip { get; set; }
            public string user_type { get; set; }

            // Client
            public string first_name { get; set; }
            public string last_name { get; set; }
            public string cell_phone { get; set; }
            public bool paying { get; set; }

            // Business
            public string name { get; set; }
            public string work_phone { get; set; }
            public string instructions { get; set; }
        }

        public class UserInfo
        {
            public string username { get; set; }
            public string email { get; set; }
            public string address { get; set; }
            public string zip { get; set; }

            public UserInfo(UserData user)
            {
                username = user.username;
                email = user.email;
                address = user.address;
                zip = user.zip;
            }
        }

        public class ClientInfo : UserInfo
        {
	        public string first_name { get; set; }
	        public string last_name { get; set; }
	        public string cell_phone { get; set; }

            public ClientInfo(Client client) : base(client.User)
            {
                first_name = client.first_name;
                last_name = client.last_name;
                cell_phone = client.cell_phone;
            }
        }

        public class BusinessInfo : UserInfo
        {
            public string name { get; set; }
            public string work_phone { get; set; }
            public string instructions { get; set; }

            public BusinessInfo(Business business) : base(business.User)
            {
                name = business.name;
                work_phone = business.work_phone;
                instructions = business.instructions;
            }
        }

        public class LoginReturn
        {
            public string session_token { get; set; }
        }

        public UserController(FoodContext dbContext)
        {
            this.dbContext = dbContext;
        }

        [Route("register")]
        [HttpPost]
        [AllowAnonymous]
        public async Task<JsonResult> Register([FromBody]UserRegistration reg)
        {
            // FIXME: validate
            UserData user = new UserData
            {
                username = reg.username,
                password = new PasswordProtector().Protect(reg.password),
                email = reg.email,
                address = reg.address,
                zip = reg.zip
            };

            await dbContext.Users.AddAsync(user);

            if (reg.user_type.ToLower() == "client")
            {
                Client client = new Client
                {
                    uid = user.uid,
                    first_name = reg.first_name,
                    last_name = reg.last_name,
                    cell_phone = reg.cell_phone,
                    paying = reg.paying
                };

                await dbContext.Clients.AddAsync(client);
            }
            else if (reg.user_type.ToLower() == "business")
            {
                Business business = new Business
                {
                    uid = user.uid,
                    name = reg.name,
                    work_phone = reg.work_phone,
                    instructions = reg.instructions
                };

                await dbContext.Businesses.AddAsync(business);
            }
            else
            {
                Acknowledgement<object> invalid_user_ack = new Acknowledgement<object>("INVALID_USER_TYPE", "User type must be specified as business or client", null);
                return Json(invalid_user_ack); 
            }

            await dbContext.SaveChangesAsync();

            Acknowledgement<object> ack = new Acknowledgement<object>("OK", "Successfully created new user", null);
            return Json(ack); 
        }

        [Route("login")]
        [HttpPost]
        [Authorize("UserPass")]
        public async Task<JsonResult> Login()
        {
            // Create session
            SessionData session = new SessionData
            {
                uid = Convert.ToInt32(User.FindFirst("uid").Value),
                created = DateTime.UtcNow.ToString(),
                expires = DateTime.UtcNow.AddMinutes(20)
            };

            await dbContext.Sessions.AddAsync(session);
            await dbContext.SaveChangesAsync();

            // Create token
            Claim[] claims = new[]
            {
                new Claim("sid", session.sid.ToString()),
                new Claim("created", session.created)
            };

            SymmetricSecurityKey key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes("secretsecretsupersecret"));
            SigningCredentials credentials = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);

            JwtSecurityToken token = new JwtSecurityToken(
                issuer: "http://localhost:50576/",
                audience: "http://localhost:50576/",
                claims: claims,
                expires: session.expires,
                signingCredentials: credentials
            );

            LoginReturn ret = new LoginReturn
            {
                session_token = new JwtSecurityTokenHandler().WriteToken(token)
            };

            Acknowledgement<LoginReturn> ack = new Acknowledgement<LoginReturn>("OK", "Token succesfully created", ret);
            return Json(ack);
        }

        [Route("logout")]
        [HttpPost]
        [Authorize("Session")]
        public async Task<JsonResult> Logout()
        {
            SessionData session = await dbContext.Sessions.FindAsync(Convert.ToInt32(User.FindFirstValue("sid")));
            dbContext.Sessions.Remove(session);
            await dbContext.SaveChangesAsync();

            Acknowledgement<object> ack = new Acknowledgement<object>("OK", "User logout was a success", null);
            return Json(ack);
        }

        [Route("getinfo")]
        [HttpPost]
        [Authorize("Session")]
        public async Task<JsonResult> GetInfo()
        {
            int uid = Convert.ToInt32(User.FindFirstValue("uid"));
            UserData user = await dbContext.Users.Include(u => u.Client).Include(u => u.Business).FirstOrDefaultAsync(u => u.uid == uid);

            
            if (user.Client != null)
            {
                Acknowledgement<ClientInfo> ack = new Acknowledgement<ClientInfo>("OK", "Request for user info success,", new ClientInfo(user.Client));
                return Json(ack);
            }
            else if (user.Business != null)
            {
                Acknowledgement<BusinessInfo> ack = new Acknowledgement<BusinessInfo>("OK", "Request for business info success.", new BusinessInfo(user.Business));
                return Json(ack);
            }
            else
            {
                Acknowledgement<object> ack = new Acknowledgement<object>("OTHER", "Function does not support this user type", null);
                return Json(ack);
            }
        }

        [Route("logoutall")]
        [HttpPost]
        [Authorize("UserPass")]
        public async Task<JsonResult> LogoutAllSessions()
        {
            int uid = Convert.ToInt32(User.FindFirstValue("uid"));
            SessionData[] sessions = await dbContext.Sessions.Where(s => s.uid == uid).ToArrayAsync();
            dbContext.Sessions.RemoveRange(sessions);
            await dbContext.SaveChangesAsync();

            Acknowledgement<object> ack = new Acknowledgement<object>("OK", "Successfully logged out of all sessions.", null);
            return Json(ack);
        }

        [Route("setinfo")]
        [HttpPost]
        [Authorize("UserPass")]
        public async Task<string> SetInfo([FromBody] string json)
        {
            throw new NotImplementedException(); // FIXME
        }

        [Route("setpassword")]
        [HttpPost]
        [Authorize("UserPass")]
        public async Task<string> SetPassword([FromBody] string json)
        {
            throw new NotImplementedException(); // FIXME
        }

        [Route("delete")]
        [HttpPost]
        [Authorize("UserPass")]
        public async Task<string> DeleteUser()
        {
            throw new NotImplementedException(); // FIXME
        }
    }
}