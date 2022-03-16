using TouristAttractions.model;
using System;
using System.Collections.Generic;
using System.Text;

namespace TouristAttractions.repository
{
    public interface IRepository<TE, ID> where TE:Entity<ID>
    {
        TE FindOne(ID id);
        IEnumerable<TE> FindAll();
        void Save(TE entity);
        void Delete(ID id);
        void Update(TE entity);
        int Size();
    }
}
