using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using TouristAttractionsCS.controller;
using transformer;

namespace TouristAttractionsCS.transformer
{
    class TripsControllerImpl : TripsController.Iface
    {
        private TripsForm tripsForm;

        public TripsControllerImpl(TripsForm tripsForm)
        {
            this.tripsForm = tripsForm;
        }

        public void Update(List<Trip> res)
        {
            List<TouristAttractions.model.Trip> trips = new List<TouristAttractions.model.Trip>();
            foreach (Trip t in res)
            {
                int hour = t.LeavingHour.Hour;
                int minute = t.LeavingHour.Minute;
                int second = t.LeavingHour.Second;
                TouristAttractions.model.Trip trip = new TouristAttractions.model.Trip(t.Id, t.TouristAttraction, t.TransportCompany, new TimeSpan(hour, minute, second), t.Price, t.NrSeats);
                trips.Add(trip);
            }
            tripsForm.BookedTrip(trips);
        }
    }
}
