using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace CapProj
{
    public partial class _Default : Page
    {
       
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected async void LogInButtonClick(object sender, EventArgs e)
        {

            Dictionary<string, string> pairs = new Dictionary<string, string>
            {
                {"username", Username.Text },
                {"password", Password.Text }
            };

            Acknowledgement<object> ack = await FoodAPI.Call<object>("api/user/login", pairs);


            if (ack.status == "OK")
            {
                string json = ack.data.ToString();
                var result = JObject.Parse(json)["session_token"];

                // FIXME : Need to ensure this actually saves cookie as intended.
                // Generally must create cookies before page is rendered.
                // This should take place after login before rendering of next page.
                HttpCookie httpCookie = new HttpCookie("SessionInfo");
                httpCookie["SessionToken"] = result.ToString();
                Response.Cookies.Add(httpCookie);
                Response.Redirect("/Success"); // FIXME: Direct to new page? Show login at top of screen?
            }
            else
            {
                Response.Redirect("/default"); // FIXME: Handle incorrect login better.
            }
        }

    }
}