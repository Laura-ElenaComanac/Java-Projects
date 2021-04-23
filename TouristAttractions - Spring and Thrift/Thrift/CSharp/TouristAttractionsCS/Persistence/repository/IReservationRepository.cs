using TouristAttractions.model;
using System.Collections.Generic;

namespace TouristAttractions.repository
{
    public interface IReservationRepository : IRepository<Reservation, int>
    {
        List<Reservation> SearchReservationByClientNameAndTelephone(string clientName, string telephone);
    }
}