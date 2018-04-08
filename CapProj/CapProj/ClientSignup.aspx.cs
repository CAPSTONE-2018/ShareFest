using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace CapProj
{
    public partial class ClientSignup : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
        }

        protected async void RegisterBtn_Click(object sender, EventArgs e)
        {
            Dictionary<string, string> pairs = new Dictionary<string, string>
            {
                { "username", Username.Text },
                { "password", Password.Text },
                { "email", Email.Text },
                { "address", Address.Text },
                { "zip", Zip.Text },
                { "user_type", "client" },
                { "first_name", FirstName.Text },
                { "last_name", LastName.Text },
                { "cell_phone", Phone.Text },
                { "paying", "true" } // FIXME: Add paying check box
            };

            Acknowledgement<object> ack = await FoodAPI.Call<object>("api/user/register", pairs);

            if(ack.status == "OK")
            {
                // FIXME: Handle success
            }
            else
            {
                // FIXME: Handle error
            }
        }
    }
}