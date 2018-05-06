using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

using Microsoft.AspNetCore.Authorization;
using Microsoft.EntityFrameworkCore;
using FoodServiceAPI.Database;
using FoodServiceAPI.Models;

namespace FoodServiceAPI.Controllers
{
    [Produces("application/json")]
    [Route("api/business")]
    public class BusinessController : Controller
    {
        private readonly FoodContext dbContext;

        public class BusinessIdentifier
        {
            public int bid { get; set; }
        }

        public class BusinessInfo
        {
            public string email { get; set; }
            public string address { get; set; }
            public string zip { get; set; }
            public string name { get; set; }
            public string work_phone { get; set; }
            public string instructions { get; set; }
        }

        public BusinessController(FoodContext dbContext)
        {
            this.dbContext = dbContext;
        }

        [Route("getbusiness")]
        [HttpPost]
        [AllowAnonymous]
        public async Task<IActionResult> GetBusiness([FromBody]BusinessIdentifier businessID)
        {
            Business business = await dbContext.Businesses.Include(b => b.User).FirstOrDefaultAsync(b => b.bid == businessID.bid);

            if(business == null)
                return BadRequest(new ResultBody("No business with given ID"));

            return Ok(
                new BusinessInfo
                {
                    email = business.User.email,
                    address = business.User.address,
                    zip = business.User.zip,
                    name = business.name,
                    work_phone = business.work_phone,
                    instructions = business.instructions
                }
            );
        }
    }
}