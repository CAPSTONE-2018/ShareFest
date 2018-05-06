using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace CapProj
{
    public partial class BusinessSignup : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected async void RegisterButtonClick(object sender, EventArgs e)
        {
            Dictionary<string, string> pairs = new Dictionary<string, string>
            {
                {"username", Username.Text },
                {"password", Password.Text },
                {"email", Email.Text },
                {"address", Address.Text },
                {"zip", Zip.Text },
                {"user_type", "business" },
                {"name", CompanyName.Text },
                {"work_phone", Phone.Text },
                {"instructions", Special.Text }
            };

            string json = JsonConvert.SerializeObject(pairs);
            using (var client = new HttpClient())
            {
                var response = await client.PostAsync(
                "http://localhost:50576/api/user/register",
                new StringContent(json, Encoding.UTF8, "application/json"));

                if (response.StatusCode.ToString() == "OK")
                {
                    Response.Redirect("/Success");
                }
                else
                {
                    Response.Redirect("/UnabletoCreateAccount");
                }
            }
        }
    }
}