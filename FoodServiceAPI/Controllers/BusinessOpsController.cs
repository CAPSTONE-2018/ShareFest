using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace FoodServiceAPI.Controllers
{
    public class BusinessOpsController : ApiController
    {

        [HttpPost]
        [Route("api/BusinessOps/getBusiness")]
        public void getBusiness([FromBody]int bId)
        {
        }
    }
}
