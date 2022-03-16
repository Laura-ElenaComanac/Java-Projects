using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking
{
    [Serializable]
    public class TripDTO
    {
        public String TouristAttraction { get; set; }
        public TimeSpan Hour1 { get; set; }
        public TimeSpan Hour2 { get; set; }

        public TripDTO(String TouristAttraction, TimeSpan Hour1, TimeSpan Hour2)
        {
            this.TouristAttraction = TouristAttraction;
            this.Hour1 = Hour1;
            this.Hour2 = Hour2;
        }
    }
}
