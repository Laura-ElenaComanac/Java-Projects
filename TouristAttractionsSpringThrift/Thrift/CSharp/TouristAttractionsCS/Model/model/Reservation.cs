using System;

namespace TouristAttractions.model
{
    [Serializable]
    public class Reservation : Entity<int>
    {
        public Reservation(int Id, int NrTickets, string ClientName, string Telephone, int AgencyUserId, int TripId)
        {
            this.Id = Id;
            this.NrTickets = NrTickets;
            this.ClientName = ClientName;
            this.Telephone = Telephone;
            this.AgencyUserId = AgencyUserId;
            this.TripId = TripId;
        }

        public int NrTickets { get; set; }
        public string ClientName { get; set; }
        public string Telephone { get; set; }
        public int AgencyUserId { get; set; }
        public int TripId { get; set; }

        public override string ToString()
        {
            return "NrTickets: " + NrTickets + " ClientName: " + ClientName + " Telephone: " + Telephone;
        }
    }
}