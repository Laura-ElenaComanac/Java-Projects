import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repository.*;
import server.Service;
import service.IService;

import java.io.IOException;
import java.rmi.ServerException;
import java.util.Properties;


public class StartObjectServer {
    
    // static int defaultPort = 55555;
    
    public static void main(String[] args) {
               /*Properties serverProps=new Properties();
        try {
            serverProps.load(StartObjectServer.class.getResourceAsStream("/touristAttractionsServer.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find touristAttractionsServer.properties "+e);
            return;
        }

        int serverPort=defaultPort;
        try {
            serverPort = Integer.parseInt(serverProps.getProperty("touristAttractions.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number "+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+serverPort);
        AbstractServer server = new AttractionsObjectConcurrentServer(serverPort, service);
        */

        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-server.xml");
        //IService server=(IService)factory.getBean("service");
        //System.out.println("Obtained a reference to remote server");


        /*AgencyUserRepository agencyUserRepository= (AgencyUserDBRepository) factory.getBean("agencyUserRepo");
        TripRepository tripRepository= (TripDBRepository) factory.getBean("tripRepo");
        ReservationRepository reservationRepository= (ReservationDBRepository) factory.getBean("reservationRepo");
        IService service = (IService) factory.getBean("serviceInterface");

        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }*/
    }
}
