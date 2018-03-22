using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace FoodServiceAPI.Controllers
{
    public class UserRegistrationController : ApiController
    {
        // POST: api/UserRegistration
        [HttpPost]
        [Route("api/userresgistration/register")]
        public void RegisterUser([FromBody]string value) //Need to Create DTO classes for posting back data to the server 
        {
        }

        [HttpPost]
        [Route("api/userresgistration/getinfo")]
        public void GetUserInfo([FromBody]string value)
        {

        }

        [HttpPost]
        [Route("api/userresgistration/logoutall")]
        public void LogOutAllSessions([FromBody]string value)
        {

        }

        [HttpPost]
        [Route("api/userresgistration/setinfo")]
        public void SetUserInfo([FromBody]string value)
        {

        }

        [HttpPost]
        [Route("api/userresgistration/setpassword")]
        public void SetUserPassword([FromBody]string value)
        {

        }

        [HttpPost]
        [Route("api/userresgistration/delete")]
        public void DeleteUser([FromBody] string value)
        {

        }








        // PUT: api/UserRegistration/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE: api/UserRegistration/5
        public void Delete(int id)
        {
        }
    }
}
