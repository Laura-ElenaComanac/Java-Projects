package app.ui;

import app.config.ApplicationContext;
import app.controller.ClientController;
import app.domain.SpecialOffer;
import app.repository.file.*;
import app.service.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    ClientFile clientFile;
    ClientService clientService;
    LocationFile locationFile;
    LocationService locationService;
    ReservationFile reservationFile;
    ReservationService reservationService;
    SpecialOfferFile specialOfferFile;
    SpecialOfferService specialOfferService;
    HotelFile hotelFile;
    HotelService hotelService;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        String fileNameClient = ApplicationContext.getPROPERTIES().getProperty("data.app.client");
        clientFile = new ClientFile(fileNameClient);
        clientService = new ClientService(clientFile);

        String fileNameLocation = ApplicationContext.getPROPERTIES().getProperty("data.app.location");
        locationFile = new LocationFile(fileNameLocation);
        locationService = new LocationService(locationFile);

        String fileNameReservation = ApplicationContext.getPROPERTIES().getProperty("data.app.reservation");
        reservationFile = new ReservationFile(fileNameReservation);
        reservationService = new ReservationService(reservationFile);

        String fileNameSpecialOffer = ApplicationContext.getPROPERTIES().getProperty("data.app.specialOffer");
        specialOfferFile = new SpecialOfferFile(fileNameSpecialOffer);
        specialOfferService = new SpecialOfferService(specialOfferFile);

        String fileNameHotel = ApplicationContext.getPROPERTIES().getProperty("data.app.hotel");
        hotelFile = new HotelFile(fileNameHotel);
        hotelService = new HotelService(hotelFile);

        getParameters().getRaw().forEach(this::createClient);

    }



//        initView(primaryStage);
//        primaryStage.setTitle("Profesor");
//        primaryStage.show();


//    private void initView(Stage profesorStage) throws IOException {
//        FXMLLoader intrebareLoader = new FXMLLoader();
//        intrebareLoader.setLocation(getClass().getResource("/views/ClientView.fxml"));
//        GridPane intrebareLayout = intrebareLoader.load();
//        profesorStage.setScene(new Scene(intrebareLayout));
//
//        ClientController clientController = intrebareLoader.getController();
//        clientController.setMainStage(profesorStage);
//        clientController.setIntrebareService(hotelService);
//        clientController.initModel();
//        getParameters().getRaw().forEach(this::createStudent);
//    }

    private void createClient(String id)
    {
        try {
            Long clientId = Long.parseLong(id.split(":")[1]);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/ClientView.fxml"));
            GridPane root =  loader.load();

            Stage clientStage = new Stage();
            clientStage.setTitle(clientService.findOne(clientId).getName());
            clientStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            clientStage.setScene(scene);

            ClientController clientController = loader.getController();
            clientController.setReservationService(reservationService);
            clientController.setClientService(clientService);
            clientController.setLocationService(locationService);
            clientController.setHotelService(hotelService);
            clientController.setSpecialOfferServicee(specialOfferService);
            clientController.setClientId(clientId);

            clientController.initModel();

            //testService.addObserver(raspunsController);

            clientController.setMainStage(clientStage);
            clientStage.show();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
