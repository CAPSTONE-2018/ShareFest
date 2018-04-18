using System;
using System.Collections.Generic;
using System.Linq;
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

            Acknowledgement<object> ack = await FoodAPI.Call<object>("api/user/register", pairs);

            if (ack.status == "OK")
            {
                Response.Redirect("/Success");
            }
            else
            {
                Response.Redirect("/Success");
                // FIX ME : Error handle
            }
        }
    }
}