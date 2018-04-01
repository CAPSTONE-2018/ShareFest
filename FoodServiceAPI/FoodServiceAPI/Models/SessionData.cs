using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FoodServiceAPI.Models
{
    public class SessionData
    {
        public int sid { get; set; }
        public int uid { get; set; }
        public string created { get; set; }
        public DateTime expires { get; set; }

        public UserData User { get; set; }
    }
}
