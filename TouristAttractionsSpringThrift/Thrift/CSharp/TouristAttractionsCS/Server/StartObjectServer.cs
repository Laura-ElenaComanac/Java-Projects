using Persistence;
using System;
using System.Collections.Generic;
using System.Configuration;
using log4net.Config;
using Services;
using System.Threading;
using System.Net.Sockets;
using System.Runtime.Remoting.Channels;
using System.Collections;
using System.Runtime.Remoting.Channels.Tcp;
using System.Runtime.Remoting;
using TouristAttractions.model;
using TouristAttractions.repository;
using TouristAttractionsCS.utils;
using TouristAttractionsCS.service;
using Networking;

namespace flightserver
{
    public class StartObjectServer
    {
        static string GetConnectionStringByName(string name)
        {
            string returnValue = null;
            ConnectionStringSettings settings = ConfigurationManager.ConnectionStrings[name];
            if (settings != null)
                returnValue = settings.ConnectionString;

            return returnValue;
        }

        static void Main(string[] args)
        {
            XmlConfigurator.Configure(new System.IO.FileInfo("Z:\\Desktop\\ProiectMPP\\mpp-proiect-repository-Laura-ElenaOlaru\\CSharp\\TouristAttractionsCS\\Persistence\\log4net.config"));
            IDictionary<String, string> properties = new SortedList<String, String>();
            properties.Add("db-ta", GetConnectionStringByName("db-ta"));

            IAgencyUserRepository agencyUserRepository = new AgencyUserDbRepository(properties);
            ITripRepository tripRepository = new TripDbRepository(properties);
            IReservationRepository reservationRepository = new ReservationDbRepository(properties);

            IService service = new Service(agencyUserRepository, tripRepository, reservationRepository);

            SerialServer server = new SerialServer("127.0.0.1", 55555, service);
            server.Start();
            Console.WriteLine("Server started ...");
            Console.ReadLine();
        }
    }
    
    public class SerialServer : ConcurrentServer
    {
        private IService server;
        private ClientObjectWorker worker;
        public SerialServer(string host, int port, IService server) : base(host, port)
        {
            this.server = server;
            Console.WriteLine("SerialServer...");
        }
        protected override Thread createWorker(TcpClient client)
        {
            worker = new ClientObjectWorker(server, client);
            return new Thread(new ThreadStart(worker.run));
        }
    }
}
