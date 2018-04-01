using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(SMSfoodServiceWebClient.Startup))]
namespace SMSfoodServiceWebClient
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
