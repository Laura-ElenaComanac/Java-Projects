using TouristAttractions.model;
using System;
using System.Collections.Generic;
using System.Data;
using log4net;
using Persistence.repository;

namespace TouristAttractions.repository
{
    public class TripDbRepository : ITripRepository
    {
        private static readonly ILog log = LogManager.GetLogger("TripDbRepository");
        private IDictionary<String, string> properties;

        public TripDbRepository(IDictionary<String, string> properties)
        {
            log.Info("Creating TripDbRepository");
            this.properties = properties;
        }

        public Trip FindOne(int id)
        {
            log.InfoFormat("Entering findOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection(properties);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from trips where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int idV = dataR.GetInt32(0);
                        String touristAttraction = dataR.GetString(1);
                        String transportCompany = dataR.GetString(2);
                        String leavingHour = dataR.GetString(3);
                        Double price = dataR.GetFloat(4);
                        int nrSeats = dataR.GetInt32(5);

                        Trip trip = new Trip(idV, touristAttraction, transportCompany, TimeSpan.Parse(leavingHour), price, nrSeats);
                        log.InfoFormat("Exiting findOne with value {0}", trip);
                        return trip;
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }

        public IEnumerable<Trip> FindAll()
        {
            IDbConnection con = DBUtils.getConnection(properties);
            IList<Trip> trips = new List<Trip>();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from trips";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int idV = dataR.GetInt32(0);
                        String touristAttraction = dataR.GetString(1);
                        String transportCompany = dataR.GetString(2);
                        String leavingHour = dataR.GetString(3);
                        Double price = dataR.GetFloat(4);
                        int nrSeats = dataR.GetInt32(5);

                        Trip trip = new Trip(idV, touristAttraction, transportCompany, TimeSpan.Parse(leavingHour), price, nrSeats);
                        trips.Add(trip);
                    }
                }
            }

            return trips;
        }

        public void Save(Trip entity)
        {
            var con = DBUtils.getConnection(properties);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into trips values (@idT, @touristAttraction, @transportCompany, @leavingHour, @price, @nrSeats)";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@idT";
                paramId.Value = entity.Id;
                comm.Parameters.Add(paramId);

                var paramTA = comm.CreateParameter();
                paramTA.ParameterName = "@touristAttraction";
                paramTA.Value = entity.TouristAttraction;
                comm.Parameters.Add(paramTA);

                var paramTC = comm.CreateParameter();
                paramTC.ParameterName = "@transportCompany";
                paramTC.Value = entity.TransportCompany;
                comm.Parameters.Add(paramTC);

                var paramLH = comm.CreateParameter();
                paramLH.ParameterName = "@leavingHour";
                paramLH.Value = entity.LeavingHour;
                comm.Parameters.Add(paramLH);

                var paramP = comm.CreateParameter();
                paramP.ParameterName = "@price";
                paramP.Value = entity.Price;
                comm.Parameters.Add(paramP);

                var paramNS = comm.CreateParameter();
                paramNS.ParameterName = "@nrSeats";
                paramNS.Value = entity.NrSeats;
                comm.Parameters.Add(paramNS);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new Exception("No trip added !");
            }
        }

        public void Delete(int id)
        {
            IDbConnection con = DBUtils.getConnection(properties);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from trips where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                var dataR = comm.ExecuteNonQuery();
                if (dataR == 0)
                    throw new Exception("No trip deleted!");
            }
        }


        public void Update(Trip entity)
        {
            var con = DBUtils.getConnection(properties);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "update trips set id=@idT, touristAttraction=@touristAttraction, transportCompany=@transportCompany, leavingHour=@leavingHour, price=@price, nrSeats=@nrSeats where id=@idT";

                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@idT";
                paramId.Value = entity.Id;
                comm.Parameters.Add(paramId);

                var paramTA = comm.CreateParameter();
                paramTA.ParameterName = "@touristAttraction";
                paramTA.Value = entity.TouristAttraction;
                comm.Parameters.Add(paramTA);

                var paramTC = comm.CreateParameter();
                paramTC.ParameterName = "@transportCompany";
                paramTC.Value = entity.TransportCompany;
                comm.Parameters.Add(paramTC);

                var paramLH = comm.CreateParameter();
                paramLH.ParameterName = "@leavingHour";
                paramLH.Value = entity.LeavingHour;
                comm.Parameters.Add(paramLH);

                var paramP = comm.CreateParameter();
                paramP.ParameterName = "@price";
                paramP.Value = entity.Price;
                comm.Parameters.Add(paramP);

                var paramNS = comm.CreateParameter();
                paramNS.ParameterName = "@nrSeats";
                paramNS.Value = entity.NrSeats;
                comm.Parameters.Add(paramNS);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new Exception("No trip updated !");
            }
        }

        public int Size()
        {
            IDbConnection con = DBUtils.getConnection(properties);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select count(*) from trips";
                var dataR = comm.ExecuteNonQuery();
                return dataR;
            }
        }

        public List<Trip> SearchTripByTouristAttractionAndLeavingHour(string touristAttraction, TimeSpan hour1, TimeSpan hour2)
        {
            log.InfoFormat("SearchTripByTouristAttractionAndLeavingHour with value {0}", touristAttraction);
            IDbConnection con = DBUtils.getConnection(properties);
            List<Trip> trips = new List<Trip>();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from trips where touristAttraction=@touristAttraction and leavingHour between @hour1 AND @hour2";
                var paramTA = comm.CreateParameter();
                paramTA.ParameterName = "@touristAttraction";
                paramTA.Value = touristAttraction;
                comm.Parameters.Add(paramTA);

                var paramLH1 = comm.CreateParameter();
                paramLH1.ParameterName = "@hour1";
                paramLH1.Value = hour1.ToString();
                comm.Parameters.Add(paramLH1);

                var paramLH2 = comm.CreateParameter();
                paramLH2.ParameterName = "@hour2";
                paramLH2.Value = hour2.ToString();
                comm.Parameters.Add(paramLH2);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int idV = dataR.GetInt32(0);
                        String touristAttraction2 = dataR.GetString(1);
                        String transportCompany = dataR.GetString(2);
                        String leavingHour = dataR.GetString(3);
                        Double price = dataR.GetFloat(4);
                        int nrSeats = dataR.GetInt32(5);

                        Trip trip = new Trip(idV, touristAttraction2, transportCompany, TimeSpan.Parse(leavingHour), price, nrSeats);
                        trips.Add(trip);
                        log.InfoFormat("Exiting findOne with value {0}", trips);

                    }
                }
            }
            log.InfoFormat("Exiting SearchTripByTouristAttractionAndLeavingHour with value {0}", touristAttraction);
            return trips;
        }
    }
}