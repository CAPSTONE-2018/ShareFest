using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace FoodServiceAPI.Controllers
{
    public class PackageController : ApiController
    {

        [HttpPost]
        [Route("api/package/geteligible")]
        public void getPackages([FromBody]string value)
        {
        }

        [HttpPost]
        [Route("api/package/createPackage")]
        public void createPackage([FromBody]string value)
        {
        }

        [HttpPost]
        [Route("api/package/deletePackage")]
        public void deletePackage([FromBody]string value)
        {
        }

        [HttpPost]
        [Route("api/package/claim")]
        public void claimPackage([FromBody]string value)
        {
        }

        [HttpPost]
        [Route("api/package/markReceived")]
        public void markPackageRecieved([FromBody]string value)
        {
        }

    }
}
