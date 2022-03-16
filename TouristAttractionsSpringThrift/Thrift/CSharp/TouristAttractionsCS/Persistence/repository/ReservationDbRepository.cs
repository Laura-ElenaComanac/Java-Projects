using TouristAttractions.model;
using System;
using System.Collections.Generic;
using System.Data;
using log4net;
using Persistence.repository;

namespace TouristAttractions.repository
{
    public class ReservationDbRepository : IReservationRepository
    {
        private static readonly ILog log = LogManager.GetLogger("ReservationDbRepository");
        private IDictionary<String, string> properties;

        public ReservationDbRepository(IDictionary<String, string> properties)
        {
            log.Info("Creating ReservationDbRepository");
            this.properties = properties;
        }

        public Reservation FindOne(int id)
        {
            log.InfoFormat("Entering findOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection(properties);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from reservations where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int idV = dataR.GetInt32(0);
                        int nrTickets = dataR.GetInt32(1);
                        string clientName = dataR.GetString(2);
                        string telephone = dataR.GetString(3);
                        int tripId = dataR.GetInt32(4);
                        int agencyId = dataR.GetInt32(5);

                        Reservation reservation = new Reservation(idV, nrTickets, clientName, telephone, agencyId, tripId);
                        log.InfoFormat("Exiting findOne with value {0}", reservation);
                        return reservation;
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }

        public IEnumerable<Reservation> FindAll()
        {
            IDbConnection con = DBUtils.getConnection(properties);
            IList<Reservation> reservations = new List<Reservation>();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from reservations";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int idV = dataR.GetInt32(0);
                        int nrTickets = dataR.GetInt32(1);
                        string clientName = dataR.GetString(2);
                        string telephone = dataR.GetString(3);
                        int tripId = dataR.GetInt32(4);
                        int agencyId = dataR.GetInt32(5);

                        Reservation reservation = new Reservation(idV, nrTickets, clientName, telephone, agencyId, tripId);
                        reservations.Add(reservation);
                    }
                }
            }

            return reservations;
        }

        public void Save(Reservation entity)
        {
            var con = DBUtils.getConnection(properties);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into reservations values (@idV, @nrTickets, @clientName, @telephone, @agencyId, @tripId)";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@idV";
                paramId.Value = entity.Id;
                comm.Parameters.Add(paramId);

                var paramTA = comm.CreateParameter();
                paramTA.ParameterName = "@nrTickets";
                paramTA.Value = entity.NrTickets;
                comm.Parameters.Add(paramTA);

                var paramTC = comm.CreateParameter();
                paramTC.ParameterName = "@clientName";
                paramTC.Value = entity.ClientName;
                comm.Parameters.Add(paramTC);

                var paramLH = comm.CreateParameter();
                paramLH.ParameterName = "@telephone";
                paramLH.Value = entity.Telephone;
                comm.Parameters.Add(paramLH);

                var paramId1 = comm.CreateParameter();
                paramId1.ParameterName = "@agencyId";
                paramId1.Value = entity.AgencyUserId;
                comm.Parameters.Add(paramId1);

                var paramId2 = comm.CreateParameter();
                paramId2.ParameterName = "@tripId";
                paramId2.Value = entity.TripId;
                comm.Parameters.Add(paramId2);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new Exception("No reservation added !");
            }
        }

        public void Delete(int id)
        {
            IDbConnection con = DBUtils.getConnection(properties);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from reservations where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                var dataR = comm.ExecuteNonQuery();
                if (dataR == 0)
                    throw new Exception("No reservation deleted!");
            }
        }


        public void Update(Reservation entity)
        {
            var con = DBUtils.getConnection(properties);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "update reservations set id=@idV, nrTickets=@nrTickets, clientName=@clientName, telephone=@telephone, AgencyUserId=@agencyId, TripId=@tripId where id=@idV";

                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@idV";
                paramId.Value = entity.Id;
                comm.Parameters.Add(paramId);

                var paramTA = comm.CreateParameter();
                paramTA.ParameterName = "@nrTickets";
                paramTA.Value = entity.NrTickets;
                comm.Parameters.Add(paramTA);

                var paramTC = comm.CreateParameter();
                paramTC.ParameterName = "@clientName";
                paramTC.Value = entity.ClientName;
                comm.Parameters.Add(paramTC);

                var paramLH = comm.CreateParameter();
                paramLH.ParameterName = "@telephone";
                paramLH.Value = entity.Telephone;
                comm.Parameters.Add(paramLH);

                var paramId1 = comm.CreateParameter();
                paramId1.ParameterName = "@agencyId";
                paramId1.Value = entity.AgencyUserId;
                comm.Parameters.Add(paramId1);

                var paramId2 = comm.CreateParameter();
                paramId2.ParameterName = "@tripId";
                paramId2.Value = entity.TripId;
                comm.Parameters.Add(paramId2);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new Exception("No reservation updated !");
            }
        }

        public int Size()
        {
            IDbConnection con = DBUtils.getConnection(properties);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select count(*) from reservations";
                var dataR = comm.ExecuteScalar();
                Console.WriteLine(dataR);
                return Int32.Parse(dataR.ToString());
            }
        }


        public List<Reservation> SearchReservationByClientNameAndTelephone(string clientName, string telephone)
        {
            log.InfoFormat("SearchReservationByClientNameAndTelephone with values {0} and {0}", clientName, telephone);
            IDbConnection con = DBUtils.getConnection(properties);
            List<Reservation> reservations = new List<Reservation>();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from reservations where clientName=@clientName and telephone=@telephone";
                var paramTC = comm.CreateParameter();
                paramTC.ParameterName = "@clientName";
                paramTC.Value = clientName;
                comm.Parameters.Add(paramTC);

                var paramLH = comm.CreateParameter();
                paramLH.ParameterName = "@telephone";
                paramLH.Value = telephone;
                comm.Parameters.Add(paramLH);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int idV = dataR.GetInt32(0);
                        int nrTickets = dataR.GetInt32(1);
                        string clientName2 = dataR.GetString(2);
                        string telephone2 = dataR.GetString(3);
                        int tripId = dataR.GetInt32(4);
                        int agencyId = dataR.GetInt32(5);

                        Reservation reservation = new Reservation(idV, nrTickets, clientName, telephone, agencyId, tripId);
                        reservations.Add(reservation);
                        log.InfoFormat("Exiting findOne with value {0}", reservations);

                    }
                }
            }
            log.InfoFormat("Exiting SearchReservationByClientNameAndTelephone with values {0} and {0}", clientName, telephone);
            return reservations;
        }
    }
}