using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Newtonsoft.Json;

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

            string json = JsonConvert.SerializeObject(pairs);
            using (var client = new HttpClient())
            {
                var response = await client.PostAsync(
                "http://localhost:50576/api/user/login",
                new StringContent(json, Encoding.UTF8, "application/json"));
                var contents = await response.Content.ReadAsStringAsync();

                var session_token_def = new { session_token = "" };

                if(response.StatusCode.ToString() == "OK")
                {
                    var ses_tok = JsonConvert.DeserializeAnonymousType(contents, session_token_def);
                    HttpCookie httpCookie = new HttpCookie("SessionInfo");
                    httpCookie["SessionToken"] = ses_tok.session_token;
                    Response.Cookies.Add(httpCookie);
                    Response.Redirect("/LoginSuccess");
                }
                else
                {
                    Response.Redirect("/UnabletoLogin");
                }

            }
        }

    }
}