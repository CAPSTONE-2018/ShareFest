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
    public partial class EditAccountSettings : System.Web.UI.Page
    {
        protected class ClientInfo
        {
            public string username { get; set; }
            public string email { get; set; }
            public string address { get; set; }
            public string zip { get; set; }
            public string user_type { get; set; }
            public string first_name { get; set; }
            public string last_name { get; set; }
            public string cell_phone { get; set; }
        }

        protected async void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                using (var client = new HttpClient())
                {
                    if (Request.Cookies["SessionInfo"] != null)
                    {
                        client.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue("Bearer", Request.Cookies["SessionInfo"].Values["SessionToken"]);
                    }
                    else
                    {
                        System.Diagnostics.Debug.WriteLine("Session Token Invalid. Check server output to determine why.");
                    }

                    var response = await client.PostAsync(
                    "http://localhost:50576/api/user/getinfo", null);
                    var contents = await response.Content.ReadAsStringAsync();

                    if(response.StatusCode.ToString() == "OK" && contents != null)
                    {
                        var clientInfo = JsonConvert.DeserializeObject<ClientInfo>(contents);

                        FirstName.Text = clientInfo.first_name;
                        LastName.Text = clientInfo.last_name;
                        Email.Text = clientInfo.email;
                        Address.Text = clientInfo.address;
                        Zip.Text = clientInfo.zip;
                        Phone.Text = clientInfo.cell_phone;
                    }
                    else
                    {
                        Response.Redirect("/Default"); //Redirect to default assuming session expired or user has no info / deleted. Beter way to handle?
                    }
                }
            }
        }

        protected async void EditButtonClick(object sender, EventArgs e)
        {
            Dictionary<string, string> pairs = new Dictionary<string, string>
            {
                {"first_name" , FirstName.Text},
                {"last_name", LastName.Text },
                {"username", CurrentUsername.Text },
                {"password", CurrentPassword.Text },
                {"new_username", NewUsername.Text },
                {"email", Email.Text },
                {"address", Address.Text },
                {"zip", Zip.Text },
                {"cell_phone", Phone.Text }

            };

            using (var client = new HttpClient())
            {
                string json = JsonConvert.SerializeObject(pairs);

                if (Request.Cookies["SessionInfo"] != null)
                {
                    client.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue("Bearer", Request.Cookies["SessionInfo"].Values["SessionToken"]);
                }
                else
                {
                    System.Diagnostics.Debug.WriteLine("Session Token Invalid. Check server output to determine why.");
                }
                var response = await client.PostAsync(
                "http://localhost:50576/api/user/setinfo", 
                new StringContent(json, Encoding.UTF8, "application/json"));

                if (response.StatusCode.ToString() == "OK")
                {
                    Response.Redirect("/InfoUpdateSuccess");
                }
                else
                {
                    Response.Redirect("/UnabletoUpdateInfo");
                }
            }
        }

    }
}