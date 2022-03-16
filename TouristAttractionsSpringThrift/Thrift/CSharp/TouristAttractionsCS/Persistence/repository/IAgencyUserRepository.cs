using TouristAttractions.model;
using System.Collections.Generic;

namespace TouristAttractions.repository
{
    public interface IAgencyUserRepository : IRepository<AgencyUser, int>
    {
        AgencyUser SearchAgencyByNameAndPassword(string name, string password);
    }
}