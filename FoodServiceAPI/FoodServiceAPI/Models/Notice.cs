using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FoodServiceAPI.Models
{
    public class Notice
    {
        public int cid { get; set; }
        public int pid { get; set; }

        public Client Client { get; set; }
        public Package Package { get; set; }
    }
}
