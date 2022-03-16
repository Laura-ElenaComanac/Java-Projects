using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TouristAttractions.model;

namespace Networking
{
    public interface Response
    {
    }

    public interface UpdateResponse : Response
    {
    }


    [Serializable]
    public class OkResponse : Response
    {
    }

    [Serializable]
    public class ErrorResponse : Response
    {
        private string message;

        public ErrorResponse(string message)
        {
            this.message = message;
        }

        public virtual string Message
        {
            get
            {
                return message;
            }
        }
    }

    [Serializable]
    public class GetTripsResponse : Response
    {
        private IEnumerable<Trip> trips;

        public GetTripsResponse(IEnumerable<Trip> trips)
        {
            this.trips = trips;
        }

        public virtual IEnumerable<Trip> Trips
        {
            get
            {
                return trips;
            }
        }

    }

    [Serializable]
    public class GetSearchedTripsResponse : Response
    {
        private List<Trip> trips;

        public GetSearchedTripsResponse(List<Trip> trips)
        {
            this.trips = trips;
        }

        public virtual List<Trip> Trips
        {
            get
            {
                return trips;
            }
        }
    }

    [Serializable]
    public class FindTripResponse : Response
    {
        private Trip trip;

        public FindTripResponse(Trip trip)
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
    public class FindAgencyUserResponse : Response
    {
        private AgencyUser agencyUser;

        public FindAgencyUserResponse(AgencyUser agencyUser)
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
    public class FindIndexResponse : Response
    {
        private int index;
        public FindIndexResponse(int index)
        {
            this.index = index;
        }

        public virtual int Index
        {
            get
            {
                return index;
            }
        }
    }

    [Serializable]
    public class BookTripResponse : UpdateResponse
    {
        private IEnumerable<Trip> trips;

        public BookTripResponse(IEnumerable<Trip> trips)
        {
            this.trips = trips;
        }

        public virtual IEnumerable<Trip> Trips
        {
            get
            {
                return trips;
            }
        }
    }

    [Serializable]
    public class GetReservationSizeRespons : Response
    {
        int size;

        public GetReservationSizeRespons(int size)
        {
            this.size = size;
        }

        public virtual int Size
        {
            get
            {
                return size;
            }
        }
    }

}
