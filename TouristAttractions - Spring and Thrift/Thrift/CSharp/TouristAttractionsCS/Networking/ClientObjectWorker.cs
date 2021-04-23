using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using TouristAttractions.model;
using TouristAttractionsCS.utils;
using Services;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;

namespace Networking
{
    public class ClientObjectWorker : IObserver
    {
        private IService server;
        private TcpClient connection;

        private NetworkStream stream;
        private IFormatter formatter;
        private volatile bool connected;

        public ClientObjectWorker(IService server, TcpClient connection)
        {
            this.server = server;
            this.connection = connection;
            try
            {
                stream = connection.GetStream();
                formatter = new BinaryFormatter();
                connected = true;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        public virtual void run()
        {
            while (connected)
            {
                try
                {
                    object request = formatter.Deserialize(stream);
                    object response = handleRequest((Request)request);
                    if (response != null)
                    {
                        sendResponse((Response)response);
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.Message);
                    Console.WriteLine(e.StackTrace);
                }

                try
                {
                    Thread.Sleep(1000);
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.Message);
                    Console.WriteLine(e.StackTrace);
                }
            }
            try
            {
                stream.Close();
                connection.Close();
            }
            catch (Exception e)
            {
                Console.WriteLine("Error " + e);
            }
        }
        private void sendResponse(Response response)
        {
            Console.WriteLine("Sending response " + response);
            formatter.Serialize(stream, response);
            stream.Flush();
        }

        private object handleRequest(Request request)
        {
            Response response = null;

            if (request is LoginRequest)
            {
                Console.WriteLine("Login request ...");
                LoginRequest logReq = (LoginRequest)request;
                AgencyUser agencyUser = logReq.AgencyUser;
                try
                {
                    lock (server)
                    {
                        server.Login(agencyUser, this);
                    }
                    return new OkResponse();
                }
                catch (TouristAttractiontException e)
                {
                    connected = false;
                    return new ErrorResponse(e.Message);
                }
            }


            if (request is LogoutRequest)
            {
                Console.WriteLine("Logout request ...");
                LogoutRequest logReq = (LogoutRequest)request;
                AgencyUser agencyUser = logReq.AgencyUser;
                try
                {
                    lock (server)
                    {
                        server.Logout(agencyUser, this);
                    }
                    return new OkResponse();
                }
                catch (TouristAttractiontException e)
                {
                    connected = false;
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is GetTripsRequest)
            {
                Console.WriteLine("Get Trips request");
                GetTripsRequest getReq = (GetTripsRequest)request;
                IEnumerable<Trip> trips;
                try
                {
                    lock (server)
                    {
                        trips = server.FindAllTrips();
                    }
                    return new GetTripsResponse(trips);
                }
                catch (TouristAttractiontException e)
                {
                    connected = false;
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is GetSearchedTripsRequest)
            {
                Console.WriteLine("Find searched trips request");
                GetSearchedTripsRequest getReq = (GetSearchedTripsRequest)request;
                TripDTO tripDTO = getReq.TripDTO;
                List<Trip> trips;

                try
                {
                    lock (server)
                    {
                        trips = (List<Trip>)server.SearchTripByTouristAttractionAndLeavingHour(tripDTO.TouristAttraction, tripDTO.Hour1, tripDTO.Hour2);
                    }
                    return new GetSearchedTripsResponse(trips);
                }
                catch (TouristAttractiontException e)
                {
                    connected = false;
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is FindAgencyUserRequest)
            {
                Console.WriteLine("Finding agency user");
                FindAgencyUserRequest findReq = (FindAgencyUserRequest)request;
                AgencyUserDTO agencyUserDTO = findReq.AgencyUser;
                AgencyUser agencyUser;
                try
                {
                    lock (server)
                    {
                        agencyUser = server.SearchAgencyUserByUserNameAndPassword(agencyUserDTO.userName, agencyUserDTO.password);
                    }
                    return new FindAgencyUserResponse(agencyUser);
                }
                catch (TouristAttractiontException e)
                {
                    connected = false;
                    return new ErrorResponse(e.Message);
                }
            }


            if (request is AddReservationRequest)
            {
                Console.WriteLine("Add Reservation Request");
                AddReservationRequest findReq = (AddReservationRequest)request;
                Reservation reservation = findReq.Reservation;
                try
                {
                    lock (server)
                    {
                        server.AddReservation(reservation);
                    }
                    return new OkResponse();
                }
                catch (TouristAttractiontException e)
                {
                    connected = false;
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is UpdateTripRequest)
            {
                Console.WriteLine("Update Trip Request");
                UpdateTripRequest findReq = (UpdateTripRequest)request;
                Trip trip = findReq.Trip;
                try
                {
                    lock (server)
                    {
                        server.UpdateTrip(trip);
                    }
                    return new OkResponse();
                }
                catch (TouristAttractiontException e)
                {
                    connected = false;
                    return new ErrorResponse(e.Message);
                }
            }


            if (request is GetReservsationSizeRequest)
            {
                Console.WriteLine("Reservation Size Request");
                GetReservsationSizeRequest findReq = (GetReservsationSizeRequest)request;
                int size = 0;
                try
                {
                    lock (server)
                    {
                        size = server.GetReservationsSize();
                    }
                    return new GetReservationSizeRespons(size);
                }
                catch (TouristAttractiontException e)
                {
                    connected = false;
                    return new ErrorResponse(e.Message);
                }
            }

            return response;
        }

        public void BookedTrip(IEnumerable<Trip> trips)
        {
            try
            {
                sendResponse(new BookTripResponse(trips));
            }
            catch (TouristAttractiontException e)
            {
                Console.WriteLine(e.Message);
            }
        }
    }
}
