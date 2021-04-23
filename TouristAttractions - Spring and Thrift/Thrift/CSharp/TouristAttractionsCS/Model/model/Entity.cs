using System;

namespace TouristAttractions.model
{
    [Serializable]
    public class Entity<TId>
    {
        public TId Id { get; set; }
    }
}
