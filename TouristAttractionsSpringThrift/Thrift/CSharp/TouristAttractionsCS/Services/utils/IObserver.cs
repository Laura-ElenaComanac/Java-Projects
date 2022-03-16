using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TouristAttractions.model;

namespace TouristAttractionsCS.utils
{
    public interface IObserver
    {
        void BookedTrip(IEnumerable<Trip> trips);
    }
}
