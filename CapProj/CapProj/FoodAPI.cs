using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Net.Http;
using System.Threading.Tasks;
using System.Text;
using Newtonsoft.Json;

namespace CapProj
{
    public class Acknowledgement<T>
    {
        public string status { get; set; }
        public string message { get; set; }
        public T data { get; set; }
    }

    public class FoodAPI
    {
        static public async Task<Acknowledgement<T>> Call<T>(string route, Dictionary<string, string> pairs)
        {
            using (HttpClient client = new HttpClient())
            {
                StringContent content = new StringContent(JsonConvert.SerializeObject(pairs), Encoding.UTF8, "application/json");

                try
                {
                    HttpResponseMessage response = await client.PostAsync("http://localhost:50576/" + route, content);

                    if (!response.IsSuccessStatusCode)
                    {
                        return new Acknowledgement<T>
                        {
                            status = "OTHER",
                            message = "Server Error", // FIXME: Set based on status code?
                            data = default(T)
                        };
                    }

                    return JsonConvert.DeserializeObject<Acknowledgement<T>>(await response.Content.ReadAsStringAsync());
                }
                catch(HttpRequestException ex)
                {
                    return new Acknowledgement<T>
                    {
                        status = "OTHER",
                        message = ex.Message,
                        data = default(T)
                    };
                }
            }
        }
    }
}