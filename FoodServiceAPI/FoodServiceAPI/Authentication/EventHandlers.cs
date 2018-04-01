using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

using Microsoft.AspNetCore.Authentication.JwtBearer;
using System.Security.Claims;
using FoodServiceAPI.Database;
using FoodServiceAPI.Models;

namespace FoodServiceAPI.Authentication
{
    public class EventHandlers
    {
        // FIXME: What should Fail messages contain? Should they influence what's sent to the client?

        public async Task ValidateTokenSession(TokenValidatedContext context)
        {
            // Validate session
            ClaimsPrincipal principal = context.Principal;

            Claim sidClaim = principal.FindFirst("sid");
            Claim createdClaim = principal.FindFirst("created");

            if(sidClaim == null || createdClaim == null)
            {
                context.Fail("token missing claims");
                return;
            }

            FoodContext dbContext = context.HttpContext.RequestServices.GetService(typeof(FoodContext)) as FoodContext;
            SessionData session = await dbContext.Sessions.FindAsync(Convert.ToInt32(sidClaim.Value));

            /* Compare created time string of session and token to verify that the token is
             * actually tied to this session instead of a deleted session that by chance had the
             * same sid and still has an unexpired token. The time is not stored as DATETIME to
             * prevent database's storage from slightly changing the value.
             * 
             * FIXME: This is a temporary hack, the sid should probably be a UUID so the created
             * attribute can be removed. */
            if(session == null || createdClaim.Value != session.created)
            {
                context.Fail("invalid session");
                return;
            }

            // Add user identity
            ClaimsIdentity userIdentity = new ClaimsIdentity("SessionUser");
            userIdentity.AddClaim(new Claim("uid", session.uid.ToString()));
            principal.AddIdentity(userIdentity);
        }
    }
}
