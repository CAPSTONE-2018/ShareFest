using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FoodServiceAPI.Models
{
    public class ResultBody
    {
        public string data { get; set; }

        public ResultBody(string dataIn)
        {
            data = dataIn;
        }
    }
}
