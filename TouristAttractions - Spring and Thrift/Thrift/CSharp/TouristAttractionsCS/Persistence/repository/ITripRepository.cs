using TouristAttractions.model;
using System.Collections.Generic;
using System;

namespace TouristAttractions.repository
{
    public interface ITripRepository : IRepository<Trip, int>
    {
        List<Trip> SearchTripByTouristAttractionAndLeavingHour(string touristAttraction, TimeSpan hour1, TimeSpan hour2);
    }
}