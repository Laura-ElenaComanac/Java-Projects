import network.utils.AbstractServer;
import network.utils.AttractionsObjectConcurrentServer;
import network.utils.ServerException;
import repository.*;
import server.Service;
import service.IService;

import java.io.IOException;
import java.util.Properties;


public class StartObjectServer {
    
    private static int defaultPort = 55555;
    
    public static void main(String[] args) {
       Properties serverProps=new Properties();
        try {
            serverProps.load(StartObjectServer.class.getResourceAsStream("/touristAttractionsServer.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find touristAttractionsServer.properties "+e);
            return;
        }
        AgencyUserRepository agencyUserRepository=new AgencyUserDBRepository(serverProps);
        TripRepository tripRepository=new TripDBRepository(serverProps);
        ReservationRepository reservationRepository=new ReservationDBRepository(serverProps);
        IService service = new Service(agencyUserRepository, tripRepository, reservationRepository);

        int serverPort=defaultPort;
        try {
            serverPort = Integer.parseInt(serverProps.getProperty("touristAttractions.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number "+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+serverPort);
        AbstractServer server = new AttractionsObjectConcurrentServer(serverPort, service);
        try {
                server.start();
        } catch (ServerException e) {
                System.err.println("Error starting the server" + e.getMessage());
        }
    }
}
