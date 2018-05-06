using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FoodServiceAPI.Models
{
    public class ReturnData<T>
    {
        public T Data { get; set; }
    }
}
