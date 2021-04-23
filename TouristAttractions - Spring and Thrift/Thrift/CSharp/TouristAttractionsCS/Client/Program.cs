using TouristAttractions;
using Networking;
using Services;
using System;
using System.Threading;
using System.Windows.Forms;
using Thrift.Protocol;
using Thrift.Server;
using Thrift.Transport;
using transformer;
using TouristAttractionsCS;
using TouristAttractionsCS.controller;
using TouristAttractionsCS.transformer;


/*using TouristAttractions.model;
using log4net;
using log4net.Config;
using TouristAttractionsCS.controller;
using TouristAttractionsCS;
using transformer;
using TouristAttractionsCS.transformer;*/



namespace TouristAttractionsCSharp
{
    class Program
    {
        [STAThread]
        static void Main(string[] args)
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);


            /*IService server = new ServerObjectProxy("127.0.0.1", 55555);

            LoginForm loginForm = new LoginForm();
            loginForm.setService(server);
            loginForm.Text = "Login";

            TripsForm tripsForm = new TripsForm();
            loginForm.setTripsForm(tripsForm);

            tripsForm.Text = "Trips";
            tripsForm.setService(server);
            tripsForm.SetLogin(loginForm);

            Application.Run(loginForm);*/


            var socket = new TSocket("localhost", 55556);
            var protocol = new TBinaryProtocol(socket);
            var client = new ThriftService.Client(protocol);
            socket.Open();

            ClientController controller = new ClientController(client);

            LoginForm loginForm = new LoginForm(controller);
            TripsForm tripsForm= new TripsForm(controller);

            var tripsControllerImpl = new TripsControllerImpl(tripsForm);
            var processor = new TripsController.Processor(tripsControllerImpl);

            int port = 55555 + new Random().Next() % 10;
            TServerTransport transport = new TServerSocket(port);
            TServer server = new TThreadPoolServer(processor, transport);

            loginForm.setTripsForm(tripsForm, port);
            loginForm.Text = "Client on port " + port;
            tripsForm.SetLogin(loginForm);
            tripsForm.Text = "Client on port " + port;

            Thread thread = new Thread(new ThreadStart(server.Serve));
            thread.Start();

            Application.Run(loginForm);
        }
    }
}
