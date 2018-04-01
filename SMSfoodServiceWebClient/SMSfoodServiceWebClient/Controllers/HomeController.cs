using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace SMSfoodServiceWebClient.Controllers
{
    public class HomeController : Controller
    {
        public ActionResult Index()
        {
            return View();
        }

        public ActionResult Client()
        {
            ViewBag.Message = "Client Signup";

            return View();
        }

        public ActionResult Business()
        {
            ViewBag.Message = "Business Signup";

            return View();
        }
    }
}