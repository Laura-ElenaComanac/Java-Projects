using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TouristAttractions.model;

namespace Networking
{
    public interface Request
    {
    }

    [Serializable]
    public class LoginRequest : Request
    {
        private AgencyUser agencyUser;
        public LoginRequest(AgencyUser agencyUser)
        {
            this.agencyUser = agencyUser;
        }

        public virtual AgencyUser AgencyUser
        {
            get
            {
                return agencyUser;
            }
        }
    }

    [Serializable]
    public class LogoutRequest : Request
    {
        private AgencyUser agencyUser;

        public LogoutRequest(AgencyUser agencyUser)
        {
            this.agencyUser = agencyUser;
        }

        public virtual AgencyUser AgencyUser
        {
            get
            {
                return agencyUser;
            }
        }
    }

    [Serializable]
    public class GetTripsRequest : Request
    {
    }

    [Serializable]
    public class AddReservationRequest : Request
    {
        private Reservation reservation;

        public AddReservationRequest(Reservation reservation)
        {
            this.reservation = reservation;
        }

        public virtual Reservation Reservation
        {
            get
            {
                return reservation;
            }
        }
    }

    [Serializable]
    public class GetSearchedTripsRequest : Request
    {
        private TripDTO tripDTO;

        public GetSearchedTripsRequest(TripDTO tripDTO)
        {
            this.tripDTO = tripDTO;
        }

        public virtual TripDTO TripDTO
        {
            get
            {
                return tripDTO;
            }
        }
    }

    [Serializable]
    public class FindAgencyUserRequest : Request
    {
        private AgencyUserDTO agencyUser;

        public FindAgencyUserRequest(AgencyUserDTO agencyUser)
        {
            this.agencyUser = agencyUser;
        }

        public virtual AgencyUserDTO AgencyUser
        {
            get
            {
                return agencyUser;
            }
        }
    }

    [Serializable]
    public class FindTripRequest : Request
    {
        private Trip trip;

        public FindTripRequest(Trip trip)
        {
            this.trip = trip;
        }

        public virtual Trip Trip
        {
            get
            {
                return trip;
            }
        }
    }

    [Serializable]
    public class GetReservsationSizeRequest : Request
    {

    }

    [Serializable]
    public class UpdateTripRequest : Request
    {
        private Trip trip;

    public UpdateTripRequest(Trip trip)
    {
        this.trip = trip;
    }

    public virtual Trip Trip
    {
        get
        {
            return trip;
        }
    }
}
}
