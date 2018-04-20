using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Net.Http;
using System.Net.Http.Headers;
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
        static public HttpClient httpClient = new HttpClient();

        static public async Task<Acknowledgement<T>> Call<T>(string route, Dictionary<string, string> pairs = null, string session = null)
        {
            using(StringContent content = pairs != null ? new StringContent(JsonConvert.SerializeObject(pairs), Encoding.UTF8, "application/json") : null)
            using(HttpRequestMessage request = new HttpRequestMessage(HttpMethod.Post, "http://localhost:50576/" + route))
            {
                try
                {
                    request.Content = content;

                    if(session != null)
                        request.Headers.Authorization = new AuthenticationHeaderValue("Bearer", session);

                    HttpResponseMessage response = await httpClient.SendAsync(request);

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