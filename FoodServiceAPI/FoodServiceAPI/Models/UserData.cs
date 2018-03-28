using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FoodServiceAPI.Models
{
    public class UserData
    {
        public int uid { get; set; }
        public string username { get; set; }
        public byte[] password { get; set; }
        public string email { get; set; }
        public string address { get; set; }
        public string zip { get; set; }

        public List<SessionData> Sessions { get; set; }
        public Business Business { get; set; }
        public Client Client { get; set; }
    }
}
