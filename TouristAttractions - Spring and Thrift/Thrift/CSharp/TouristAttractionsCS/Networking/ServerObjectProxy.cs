using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;
using Services;
using TouristAttractions.model;
using TouristAttractionsCS.utils;

namespace Networking
{
    public class ServerObjectProxy : IService
    {
        private string host;
        private int port;

        private IObserver client;

        private NetworkStream stream;

        private IFormatter formatter;
        private TcpClient connection;

        private Queue<Response> responses;
        private volatile bool finished;
        private EventWaitHandle _waitHandle;

        public ServerObjectProxy(string host, int port)
        {
            this.host = host;
            this.port = port;
            responses = new Queue<Response>();
        }

        public void AddReservation(Reservation reservation)
        {
            sendRequest(new AddReservationRequest(reservation));
            Response response = readResponse();
            if (!(response is OkResponse))
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new TouristAttractiontException(err.Message);
            }
        }

        public IEnumerable<AgencyUser> FindAllAgencyUsers()
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Trip> FindAllTrips()
        {
            sendRequest(new GetTripsRequest());
            Response response = readResponse();
            if (response is GetTripsResponse)
                return ((GetTripsResponse)response).Trips;
            else
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new TouristAttractiontException(err.Message);
            }
        }

        public int GetReservationsSize()
        {
            sendRequest(new GetReservsationSizeRequest());
            Response response = readResponse();
            if (response is GetReservationSizeRespons)
                return ((GetReservationSizeRespons)response).Size;
            else
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new TouristAttractiontException(err.Message);
            }
        }

        public void Login(AgencyUser agencyUser, IObserver observer)
        {
            initializeConnection();
            sendRequest(new LoginRequest(agencyUser));
            Response response = readResponse();
            if (response is OkResponse)
            {
                this.client = observer;
                return;
            }
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                closeConnection();
                throw new TouristAttractiontException(err.Message);
            }
        }

        public void Logout(AgencyUser agencyUser, IObserver observer)
        {
            sendRequest(new LogoutRequest(agencyUser));
            Response response = readResponse();
            closeConnection();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new TouristAttractiontException(err.Message);
            }
            if (response is OkResponse)
            {
                this.client = observer;
                return;
            }
        }

        public AgencyUser SearchAgencyUserByUserNameAndPassword(string userName, string password)
        {
            sendRequest(new FindAgencyUserRequest(new AgencyUserDTO(userName, password)));
            Response response = readResponse();
            if (response is FindAgencyUserResponse)
            {
                return ((FindAgencyUserResponse)response).AgencyUser;
            }
            else
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new TouristAttractiontException(err.Message);
            }
        }

        public List<Trip> SearchTripByTouristAttractionAndLeavingHour(string touristAttraction, TimeSpan hour1, TimeSpan hour2)
        {
            sendRequest(new GetSearchedTripsRequest(new TripDTO(touristAttraction, hour1, hour2)));
            Response response = readResponse();
            if(response is GetSearchedTripsResponse)
            {
                return ((GetSearchedTripsResponse)response).Trips;
            }
            else
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new TouristAttractiontException(err.Message);
            }
        }

        public void UpdateTrip(Trip trip)
        {
            sendRequest(new UpdateTripRequest(trip));
            Response response = readResponse();
            if (!(response is OkResponse))
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new TouristAttractiontException(err.Message);
            }
        }

        private void sendRequest(Request request)
        {
            try
            {
                formatter.Serialize(stream, request);
                stream.Flush();
            }
            catch (Exception e)
            {
                throw new TouristAttractiontException("Error sending object " + e);
            }

        }
        private void closeConnection()
        {
            finished = true;
            try
            {
                stream.Close();
                connection.Close();
                _waitHandle.Close();
                client = null;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }

        }
        private Response readResponse()
        {
            Response response = null;
            try
            {
                _waitHandle.WaitOne();
                lock (responses)
                {
                    //Monitor.Wait(responses); 
                    response = responses.Dequeue();

                }


            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
            return response;
        }

        private void initializeConnection()
        {

            try
            {
                connection = new TcpClient(host, port);
                stream = connection.GetStream();
                formatter = new BinaryFormatter();
                finished = false;
                _waitHandle = new AutoResetEvent(false);
                startReader();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        private void startReader()
        {
            Thread tw = new Thread(run);
            tw.Start();
        }

        public virtual void run()
        {
            while (!finished)
            {
                try
                {
                    object response = formatter.Deserialize(stream);
                    Console.WriteLine("response received " + response);
                    if (response is UpdateResponse)
                    {
                        handleUpdate((UpdateResponse)response);
                    }
                    else
                    {

                        lock (responses)
                        {


                            responses.Enqueue((Response)response);

                        }
                        _waitHandle.Set();
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine("Reading error " + e);
                }

            }
        }

        private void handleUpdate(UpdateResponse response)
        {
            BookTripResponse responseR = (BookTripResponse)response;
            IEnumerable<Trip> trips = responseR.Trips;
            try
            {
                client.BookedTrip(trips);
            }
            catch (TouristAttractiontException e)
            {
                Console.WriteLine(e.Message);
            }
        }
    }
}
