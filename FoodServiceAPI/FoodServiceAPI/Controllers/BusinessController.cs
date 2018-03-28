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

        public BusinessController(FoodContext dbContext)
        {
            this.dbContext = dbContext;
        }

        [Route("getbusiness")]
        [HttpPost]
        [AllowAnonymous]
        public async Task<string> GetBusiness([FromBody] string json)
        {
            throw new NotImplementedException(); // FIXME
        }
    }
}