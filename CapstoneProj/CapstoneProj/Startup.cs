using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(CapstoneProj.Startup))]
namespace CapstoneProj
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
