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
    public partial class ChangePassword : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected async void ChangePWButtonClick(object sender, EventArgs e)
        {
            Dictionary<string, string> pairs = new Dictionary<string, string>
            {
                {"username", Username.Text },
                {"password", CurrentPassword.Text },
                {"new_password", NewPassword.Text }
            };
            
            using (var client = new HttpClient())
            {
                string json = JsonConvert.SerializeObject(pairs);
                var response = await client.PostAsync(
                "http://localhost:50576/api/user/setpassword",
                new StringContent(json, Encoding.UTF8, "application/json"));

                if (response.StatusCode.ToString() == "OK")
                {
                    Response.Redirect("/PWUpdateSuccess");
                }
                else
                {
                    Response.Redirect("/UnabletoUpdatePW");
                }
            }
        }
    }
}