using TouristAttractions.model;
using System;
using System.Collections.Generic;
using System.Data;
using log4net;
using System.Data.SqlClient;
using Persistence.repository;

namespace TouristAttractions.repository
{
    public class AgencyUserDbRepository : IAgencyUserRepository
    {
        private static readonly ILog log = LogManager.GetLogger("AgencyUserDbRepository");
        private IDictionary<String, string> properties;
        protected SqlConnection dbConnection;

        public AgencyUserDbRepository(IDictionary<String, string> properties)
        {
            log.Info("Creating AgencyUserDbRepository");
            this.properties = properties;
        }

        public AgencyUser FindOne(int id)
        {
            log.InfoFormat("Entering findOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection(properties);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from agencyUsers where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int idV = dataR.GetInt32(0);
                        String name = dataR.GetString(1);
                        String password = dataR.GetString(2);

                        AgencyUser agencyUser = new AgencyUser(idV, name, password);
                        log.InfoFormat("Exiting findOne with value {0}", agencyUser);
                        return agencyUser;
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }

        public IEnumerable<AgencyUser> FindAll()
        {
            IDbConnection con = DBUtils.getConnection(properties);
            IList<AgencyUser> agencyUsers = new List<AgencyUser>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from agencyUsers";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int idV = dataR.GetInt32(0);
                        String name = dataR.GetString(1);
                        String password = dataR.GetString(2);

                        AgencyUser agencyUser = new AgencyUser(idV, name, password);
                        agencyUsers.Add(agencyUser);
                    }
                }
            }

            return agencyUsers;
        }

        public void Save(AgencyUser entity)
        {
            var con = DBUtils.getConnection(properties);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into agencyUsers values (@idT, @name, @pass)";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@idT";
                paramId.Value = entity.Id;
                comm.Parameters.Add(paramId);

                var paramName = comm.CreateParameter();
                paramName.ParameterName = "@name";
                paramName.Value = entity.UserName;
                comm.Parameters.Add(paramName);

                var paramPass = comm.CreateParameter();
                paramPass.ParameterName = "@pass";
                paramPass.Value = entity.Password;
                comm.Parameters.Add(paramPass);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new Exception("No agencyUser added !");
            }
        }

        public void Delete(int id)
        {
            IDbConnection con = DBUtils.getConnection(properties);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from agencyUsers where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                var dataR = comm.ExecuteNonQuery();
                if (dataR == 0)
                    throw new Exception("No agencyUser deleted!");
            }
        }


        public void Update(AgencyUser entity)
        {
            var con = DBUtils.getConnection(properties);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "update agencyUsers set id=@idT, userName=@name, password=@pass where id=@idT";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@idT";
                paramId.Value = entity.Id;
                comm.Parameters.Add(paramId);

                var paramName = comm.CreateParameter();
                paramName.ParameterName = "@name";
                paramName.Value = entity.UserName;
                comm.Parameters.Add(paramName);

                var paramPass = comm.CreateParameter();
                paramPass.ParameterName = "@pass";
                paramPass.Value = entity.Password;
                comm.Parameters.Add(paramPass);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new Exception("No agencyUser updated !");
            }
        }

        public int Size()
        {
            IDbConnection con = DBUtils.getConnection(properties);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select count(*) from agencyUsers";
                var dataR = comm.ExecuteNonQuery();
                return dataR;
            }
        }

        public AgencyUser SearchAgencyByNameAndPassword(string name, string password)
        {
            log.InfoFormat("SearchAgencyByNameAndPassword with values {0} and {0}", name, password);
            IDbConnection con = DBUtils.getConnection(properties);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from agencyUsers where userName=@name and password=@pass";
                IDbDataParameter paramName = comm.CreateParameter();
                paramName.ParameterName = "@name";
                paramName.Value = name;
                comm.Parameters.Add(paramName);

                IDbDataParameter paramPass = comm.CreateParameter();
                paramPass.ParameterName = "@pass";
                paramPass.Value = password;
                comm.Parameters.Add(paramPass);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int idV = dataR.GetInt32(0);
                        String name2 = dataR.GetString(1);
                        String password2 = dataR.GetString(2);

                        AgencyUser agencyUser = new AgencyUser(idV, name2, password2);
                        log.InfoFormat("Exiting findOne with value {0}", agencyUser);
                        return agencyUser;
                    }
                }
            }
            log.InfoFormat("Exiting SearchAgencyByNameAndPassword with value {0} and {0}", name, password);
            return null;
        }
    }
}