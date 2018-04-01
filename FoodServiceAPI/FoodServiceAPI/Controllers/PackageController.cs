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
    [Route("api/package")]
    public class PackageController : Controller
    {
        private readonly FoodContext dbContext;

        public PackageController(FoodContext dbContext)
        {
            this.dbContext = dbContext;
        }

        [Route("getpackages")]
        [HttpPost]
        [Authorize("Session")]
        public async Task<string> GetPackages([FromBody]string json)
        {
            throw new NotImplementedException(); // FIXME
        }

        [Route("createpackage")]
        [HttpPost]
        [Authorize("Session")]
        public async Task<string> CreatePackage([FromBody]string json)
        {
            throw new NotImplementedException(); // FIXME
        }

        [Route("deletepackage")]
        [HttpPost]
        [Authorize("Session")]
        public async Task<string> DeletePackage([FromBody]string json)
        {
            throw new NotImplementedException(); // FIXME
        }

        [Route("claim")]
        [HttpPost]
        [Authorize("Session")]
        public async Task<string> ClaimPackage([FromBody]string json)
        {
            throw new NotImplementedException(); // FIXME
        }

        [Route("markreceived")]
        [HttpPost]
        [Authorize("Session")]
        public async Task<string> MarkReceived([FromBody]string json)
        {
            throw new NotImplementedException(); // FIXME
        }
    }
}