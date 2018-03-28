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

        public UserController(FoodContext dbContext)
        {
            this.dbContext = dbContext;
        }

        // FIXME: This creates a placeholder user without validation; implement full registration
        // FIXME: JSON input
        [Route("register")]
        [HttpPost]
        [AllowAnonymous]
        public async Task<string> Register(string username, string password)
        {
            UserData user = new UserData
            {
                username = username,
                password = new PasswordProtector().Protect(password),
                email = "no_email",
                address = "no_address",
                zip = "no_zip"
            };

            await dbContext.Users.AddAsync(user);
            await dbContext.SaveChangesAsync();

            return "OK"; // FIXME: Standard acknowledge
        }

        [Route("login")]
        [HttpPost]
        [Authorize("UserPass")]
        public async Task<string> Login()
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

            return new JwtSecurityTokenHandler().WriteToken(token); // FIXME: Standard acknowledge
        }

        [Route("logout")]
        [HttpPost]
        [Authorize("Session")]
        public async Task<string> Logout()
        {
            throw new NotImplementedException(); // FIXME
        }

        // FIXME: This placeholder returns username; implement actual getinfo
        [Route("getinfo")]
        [HttpPost]
        [Authorize("Session")]
        public async Task<string> GetInfo()
        {
            UserData user = await dbContext.Users.FindAsync(Convert.ToInt32(User.FindFirstValue("uid")));
            return user.username;
        }

        [Route("logoutall")]
        [HttpPost]
        [Authorize("UserPass")]
        public async Task<string> LogoutAllSessions()
        {
            throw new NotImplementedException(); // FIXME
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