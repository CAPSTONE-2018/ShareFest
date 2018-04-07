using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FoodServiceAPI.Models
{
    public class Package
    {
        public int pid { get; set; }
        public int owner_bid { get; set; }
        public int? claimer_cid { get; set; }
        public string name { get; set; }
        public string description { get; set; }
        public string quantity { get; set; }
        public decimal price { get; set; }
        public DateTime created { get; set; }
        public DateTime? expires { get; set; }
        public DateTime? claimed { get; set; }
        public DateTime? received { get; set; }

        public Business Owner { get; set; }
        public Client Claimer { get; set; }
        public List<Notice> Notices { get; set; }
    }
}
