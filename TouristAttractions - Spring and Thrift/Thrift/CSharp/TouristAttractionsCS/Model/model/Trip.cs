using System;

namespace TouristAttractions.model
{
    [Serializable]
    public class Trip : Entity<int>
    {
        public Trip()
        {
        }

        public Trip(int Id, String TouristAttraction, String TransportCompany, TimeSpan LeavingHour, double Price, int NrSeats)
        {
            this.Id = Id;
            this.TouristAttraction = TouristAttraction;
            this.TransportCompany = TransportCompany;
            this.LeavingHour = LeavingHour;
            this.Price = Price;
            this.NrSeats = NrSeats;
        }

        public string TouristAttraction { get; set; }
        public string TransportCompany  { get; set; }
        public TimeSpan LeavingHour  { get; set; }
        public double Price  { get; set; }
        public int NrSeats  { get; set; }

        public override string ToString()
        {
            return "TouristAttraction: " + TouristAttraction + " TransportCompany: " + TransportCompany + " LeavingHour: " + LeavingHour + " Price: " + Price + " NrSeats: " + NrSeats;
        }
    }
}
