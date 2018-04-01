using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FoodServiceAPI.Models
{
    public class Business
    {
        public int bid { get; set; }
        public int uid { get; set; }
        public string name { get; set; }
        public string work_phone { get; set; }
        public string instructions { get; set; }

        public UserData User { get; set; }
        public List<Package> OwnedPackages { get; set; }
    }
}
