using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FoodServiceAPI.Models
{
    public class Acknowledgement<T>
    {
        public string status { get; set; }
        public string message { get; set; }
        public T Data { get; set; }

        public Acknowledgement(string statusIn, string messageIn, T dataIn)
        {
            status = statusIn;
            message = messageIn;
            Data = dataIn;

        }
    }


}
