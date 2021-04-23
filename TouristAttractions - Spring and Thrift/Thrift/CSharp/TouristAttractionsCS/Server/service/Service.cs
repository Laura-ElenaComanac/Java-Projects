using Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TouristAttractions.model;
using TouristAttractions.repository;
using TouristAttractionsCS.utils;

namespace TouristAttractionsCS.service
{
    public class Service : IService
    {
        private IAgencyUserRepository AgencyUserRepository;
        private ITripRepository TripRepository;
        private IReservationRepository ReservationRepository;

        private Dictionary<int, IObserver> loggedAgencyUsers;

        public Service(IAgencyUserRepository AgencyUserRepository, ITripRepository TripRepository, IReservationRepository ReservationRepository)
        {
            this.AgencyUserRepository = AgencyUserRepository;
            this.TripRepository = TripRepository;
            this.ReservationRepository = ReservationRepository;
            loggedAgencyUsers = new Dictionary<int, IObserver>();
        }

        private void NotifyAll()
        {
            IEnumerable<AgencyUser> agencyUsers = AgencyUserRepository.FindAll();
            foreach (AgencyUser a in agencyUsers)
            {
                if (loggedAgencyUsers.ContainsKey(a.Id))
                {
                    IObserver observer = loggedAgencyUsers[a.Id];
                    observer.BookedTrip(TripRepository.FindAll());
                }
            }
        }

        public void Login(AgencyUser agencyUser, IObserver observer)
        {
            AgencyUser newAgencyUser = AgencyUserRepository.SearchAgencyByNameAndPassword(agencyUser.UserName, agencyUser.Password);
            if (newAgencyUser != null)
            {
                if (loggedAgencyUsers.ContainsKey(newAgencyUser.Id))
                    throw new TouristAttractiontException("Agency User already logged in!");
                loggedAgencyUsers[newAgencyUser.Id] = observer;

            }
            else
                throw new TouristAttractiontException("Autthetication failed!");
        }

        public void Logout(AgencyUser agencyUser, IObserver observer)
        {
            IObserver localAgencyUser = loggedAgencyUsers[agencyUser.Id];
            if (localAgencyUser == null)
                throw new TouristAttractiontException("Agency User logged out!");
            loggedAgencyUsers.Remove(agencyUser.Id);
        }

        public int GetReservationsSize()
        {
            return ReservationRepository.Size();
        }

        public void AddReservation(Reservation reservation)
        {
            ReservationRepository.Save(reservation);
            NotifyAll();
        }

        public void UpdateTrip(Trip trip)
        {
            TripRepository.Update(trip);
            //NotifyAll();
        }


        public IEnumerable<AgencyUser> FindAllAgencyUsers()
        {
            return AgencyUserRepository.FindAll();
        }

        public IEnumerable<Trip> FindAllTrips()
        {
            return TripRepository.FindAll();
        }

        public List<Trip> SearchTripByTouristAttractionAndLeavingHour(string touristAttraction, TimeSpan hour1, TimeSpan hour2)
        {
            return TripRepository.SearchTripByTouristAttractionAndLeavingHour(touristAttraction, hour1, hour2);
        }

        public AgencyUser SearchAgencyUserByUserNameAndPassword(string userName, string password)
        {
            return AgencyUserRepository.SearchAgencyByNameAndPassword(userName, password);
        }
    }
}
