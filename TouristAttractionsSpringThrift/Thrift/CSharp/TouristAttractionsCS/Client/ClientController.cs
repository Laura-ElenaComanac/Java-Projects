using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TouristAttractionsCS.controller;
using transformer;

namespace TouristAttractionsCS
{
    public class ClientController
    {
        public ThriftService.Client server;
        public TripsForm tripsForm;

        public ClientController(ThriftService.Client server)
        {
            this.server = server;
        }

        public void Set(TripsForm tripsForm)
        {
            this.tripsForm = tripsForm;
        }

        public void Login(TouristAttractions.model.AgencyUser agencyUser)
        {
            server.Login(new AgencyUser() { Id = agencyUser.Id, UserName = agencyUser.UserName, Password = agencyUser.Password });
        }

        public void Logout(int port)
        {
            server.Logout(port);
        }

        public void NotifyServer()
        {
            server.notifyServer();
        }

        public int GetReservationsSize()
        {
            return Int32.Parse(server.GetReservationsSize().ToString());
        }

        public void AddReservation(TouristAttractions.model.Reservation reservation)
        {
            server.AddReservation(new Reservation() { Id = reservation.Id, NrTickets = reservation.NrTickets, ClientName = reservation.ClientName, Telephone = reservation.Telephone, AgencyUserId = reservation.AgencyUserId, TripId = reservation.TripId});
        }

        public void UpdateTrip(TouristAttractions.model.Trip trip)
        {
            LocalTime time = new LocalTime();
            time.Hour = trip.LeavingHour.Hours;
            time.Minute = trip.LeavingHour.Minutes;
            time.Second = trip.LeavingHour.Seconds;

            server.UpdateTrip(new Trip() { Id = trip.Id, TouristAttraction = trip.TouristAttraction, TransportCompany = trip.TransportCompany, LeavingHour = time, Price = trip.Price, NrSeats = trip.NrSeats });
        }

        public List<TouristAttractions.model.AgencyUser> FindAllAgencyUsers()
        {
            List<TouristAttractions.model.AgencyUser> agencyUsers = new List<TouristAttractions.model.AgencyUser>();
            List<AgencyUser> agusers = server.FindAllAgencyUsers();

            foreach (AgencyUser agencyUser in agusers)
                agencyUsers.Add(new TouristAttractions.model.AgencyUser() { Id = agencyUser.Id, UserName = agencyUser.UserName, Password = agencyUser.Password });
            
            return agencyUsers;
        }

        public List<TouristAttractions.model.Trip> FindAllTrips()
        {
            List<TouristAttractions.model.Trip> trips = new List<TouristAttractions.model.Trip>();
            List<Trip> ts = server.FindAllTrips();

            foreach (Trip trip in ts)
            {
                TimeSpan time = new TimeSpan(trip.LeavingHour.Hour, trip.LeavingHour.Minute, trip.LeavingHour.Second);

                trips.Add(new TouristAttractions.model.Trip() { Id = trip.Id, TouristAttraction = trip.TouristAttraction, TransportCompany = trip.TransportCompany, LeavingHour = time, Price = trip.Price, NrSeats = trip.NrSeats });
            }
            return trips;
        }

        public TouristAttractions.model.AgencyUser SearchAgencyUserByUserNameAndPassword(string userName, string password)
        {
            AgencyUser agencyUser = server.SearchAgencyUserByUserNameAndPassword(userName, password);
            return new TouristAttractions.model.AgencyUser(agencyUser.Id, agencyUser.UserName, agencyUser.Password);
        }

        public List<TouristAttractions.model.Trip> SearchTripByTouristAttractionAndLeavingHour(string touristAttraction, TimeSpan hour1, TimeSpan hour2)
        {
            List<TouristAttractions.model.Trip> trips = new List<TouristAttractions.model.Trip>();

            LocalTime time1 = new LocalTime();
            time1.Hour = hour1.Hours;
            time1.Minute = hour1.Minutes;

            LocalTime time2 = new LocalTime();
            time2.Hour = hour2.Hours;
            time2.Minute = hour2.Minutes;

            List<Trip> ts = server.SearchTripByTouristAttractionAndLeavingHour(touristAttraction, time1, time2);

            foreach(Trip trip in ts)
            {
                TimeSpan time = new TimeSpan(trip.LeavingHour.Hour, trip.LeavingHour.Minute, trip.LeavingHour.Second);

                trips.Add(new TouristAttractions.model.Trip() { Id = trip.Id, TouristAttraction = trip.TouristAttraction, TransportCompany = trip.TransportCompany, LeavingHour = time, Price = trip.Price, NrSeats = trip.NrSeats });
            }

            return trips;
        }

    }
}
