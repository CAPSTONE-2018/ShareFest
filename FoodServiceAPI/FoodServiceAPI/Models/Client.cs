using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FoodServiceAPI.Models
{
    public class Client
    {
        public int cid { get; set; }
        public int uid { get; set; }
        public string first_name { get; set; }
        public string last_name { get; set; }
        public string cell_phone { get; set; }
        public bool paying { get; set; }

        public UserData User { get; set; }
        public List<Package> ClaimedPackages { get; set; }
        public List<Notice> Notices { get; set; }
    }
}
