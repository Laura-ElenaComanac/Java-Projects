import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import repository.*;
import transformer.ThriftServer;
import transformer.ThriftService;

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

        /*AgencyUserRepository agencyUserRepository=new AgencyUserDBRepository(serverProps);
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
        }*/

        AgencyUserDBRepository agencyUserDBRepository = new AgencyUserDBRepository(serverProps);
        TripDBRepository tripDBRepository = new TripDBRepository(serverProps);
        ReservationDBRepository reservationDBRepository = new ReservationDBRepository(serverProps);

        try {
            ThriftServer thriftService = new ThriftServer(agencyUserDBRepository, tripDBRepository, reservationDBRepository);
            ThriftService.Processor<ThriftServer> processor = new ThriftService.Processor<>(thriftService);
            TServerTransport serverTransport = new TServerSocket(55556);
            TServer thriftServer = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
            System.out.println("Server running...");
            thriftServer.serve();

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
