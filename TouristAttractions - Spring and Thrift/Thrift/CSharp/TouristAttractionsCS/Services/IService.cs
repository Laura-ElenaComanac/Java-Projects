using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TouristAttractions.model;
using TouristAttractionsCS.utils;

namespace Services
{
    public interface IService
    {
        void Login(AgencyUser agencyUser, IObserver observer);
        void Logout(AgencyUser agencyUser, IObserver observer);
        int GetReservationsSize();
        void AddReservation(Reservation reservation);
        void UpdateTrip(Trip trip);
        IEnumerable<AgencyUser> FindAllAgencyUsers();
        IEnumerable<Trip> FindAllTrips();
        AgencyUser SearchAgencyUserByUserNameAndPassword(String userName, String password);
        List<Trip> SearchTripByTouristAttractionAndLeavingHour(String touristAttraction, TimeSpan hour1, TimeSpan hour2);
    }
}
