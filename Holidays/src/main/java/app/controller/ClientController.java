package app.controller;

import app.domain.Hotel;
import app.domain.Location;
import app.domain.Reservation;
import app.domain.SpecialOffer;
import app.service.*;
import app.utils.events.Event;
import app.utils.events.ReservationChangeEvent;
import app.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClientController implements Observer<Event>  {
    private ClientService clientService;
    private LocationService locationService;
    private HotelService hotelService;
    private SpecialOfferService specialOfferService;
    private ReservationService reservationService;

    private Long clientId;

    ObservableList<Hotel> modelHotel = FXCollections.observableArrayList();
    ObservableList<SpecialOfferEntry> modelSpecialOffer = FXCollections.observableArrayList();

    Stage mainStage;

    @FXML
    ComboBox<Location> comboBoxLocatie;
    @FXML
    Label labelHobby;

    @FXML
    TableView<Hotel> tableViewHotel;
    @FXML
    TableView<SpecialOfferEntry> tableViewSpecialOffer;

    @FXML
    TableColumn<Hotel, String> tableColumnHotelName;
    @FXML
    TableColumn<Hotel, Integer> tableColumnNoRooms;
    @FXML
    TableColumn<Hotel, Double> tableColumnPricePerNight;
    @FXML
    TableColumn<Hotel, Hotel.type> tableColumnType;

    @FXML
    TableColumn<SpecialOfferEntry, String> tableColumnNumeHotelOferta;
    @FXML
    TableColumn<SpecialOfferEntry, String> tableColumnNumeLocatieOferta;
    @FXML
    TableColumn<SpecialOfferEntry, Date> tableColumnStartDateOferta;
    @FXML
    TableColumn<SpecialOfferEntry, Date> tableColumnEndDateOferta;


    public void setReservationService(ReservationService reservationService){
        this.reservationService = reservationService;
        reservationService.addObserver(this);
    }

    public void setClientService(ClientService clientService){
        this.clientService = clientService;
        clientService.addObserver(this);
    }

    public void setLocationService(LocationService locationService){
        this.locationService = locationService;
        locationService.addObserver(this);
    }

    public void setHotelService(HotelService hotelService){
        this.hotelService = hotelService;
        hotelService.addObserver(this);
    }

    public void setSpecialOfferServicee(SpecialOfferService specialOfferService){
        this.specialOfferService = specialOfferService;
        specialOfferService.addObserver(this);
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @FXML
    public void initialize(){
        tableColumnHotelName.setCellValueFactory(new PropertyValueFactory<>("hotelName"));
        tableColumnNoRooms.setCellValueFactory(new PropertyValueFactory<>("noRooms"));
        tableColumnPricePerNight.setCellValueFactory(new PropertyValueFactory<>("pricePerNight"));
        tableColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));

        tableColumnNumeHotelOferta.setCellValueFactory(new PropertyValueFactory<>("numeHotel"));
        tableColumnNumeLocatieOferta.setCellValueFactory(new PropertyValueFactory<>("numeLocatie"));
        tableColumnStartDateOferta.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        tableColumnEndDateOferta.setCellValueFactory(new PropertyValueFactory<>("endDate"));
    }

    public void initModel() throws ParseException {
        comboBoxLocatie.getItems().addAll(locationService.getAllEntities());
        Location location = comboBoxLocatie.getSelectionModel().getSelectedItem();

        if(location!=null)
        {
            Iterable<Hotel> hotels = hotelService.getAllEntities();
            List<Hotel> hotelsList = StreamSupport.stream(hotels.spliterator(),false)
                                        .filter(h -> h.getLocationId().equals(location.getLocationId()))
                                        .collect(Collectors.toList());

            modelHotel.setAll(hotelsList);
            tableViewHotel.setItems(modelHotel);
        }


        SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date(System.currentTimeMillis());

        Iterable<SpecialOffer> specialOffers= specialOfferService.getAllEntities();
        List<SpecialOfferEntry> specialOffersList = StreamSupport.stream(specialOffers.spliterator(), false)
                .filter(o -> {
                    if(date.compareTo(o.getStartDate()) < 0 || date.compareTo(o.getEndDate()) > 0)
                        return false;
                    return true;
                        })
                .filter(o -> clientService.findOne(clientId).getFidelityGrade() > o.getPercents())
                .map(o -> new SpecialOfferEntry(
                        hotelService.findOne(o.getHotelId()).getHotelName(),
                        locationService.findOne(hotelService.findOne(o.getHotelId()).getLocationId()).getLocationName(),
                        o.getStartDate(),
                        o.getEndDate()
        ))
                .collect(Collectors.toList());

        modelSpecialOffer.setAll(specialOffersList);
        tableViewSpecialOffer.setItems(modelSpecialOffer);
    }


    @Override
    public void update(Event event) {
        if(event instanceof ReservationChangeEvent) {
            Reservation reservation = ((ReservationChangeEvent) event).getData();
            if (!reservation.getClientId().equals(this.clientId))
                if(clientService.findOne(this.clientId).getHobby().equals(clientService.findOne(reservation.getClientId()).getHobby()))
                    labelHobby.setText("Inca un utilizator cu hobby " +clientService.findOne(this.clientId).getHobby() +" a facut o rezervare la hotelul " + hotelService.findOne(reservation.getHotelId()).getHotelName());
        }
        try {
            initModel();
        }
        catch (ParseException e){

        }
    }

    public void handleComboLocation(ActionEvent event) {
        try {
            initModel();
        }
        catch (ParseException e){

        }
    }

    public void handleClickHotel(MouseEvent mouseEvent) {
        Hotel selected = tableViewHotel.getSelectionModel().getSelectedItem();
        showRezervareDialog(selected);
    }

    public void showRezervareDialog(Hotel hotel) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/RezervareView.fxml"));
            GridPane root = (GridPane) loader.load();
            // stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Rezervare");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            RezervareController editController = loader.getController();
            editController.setReservationService(reservationService, hotel, clientId);

            editController.setHotelService(hotelService);
            editController.setLocationService(locationService);
            editController.setSpecialOfferServicee(specialOfferService);
            dialogStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class SpecialOfferEntry{
        String numeHotel;
        String numeLocatie;
        Date startDate;
        Date endDate;

        public SpecialOfferEntry(String numeHotel, String numeLocatie, Date startDate, Date endDate) {
            this.numeHotel = numeHotel;
            this.numeLocatie = numeLocatie;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public String getNumeHotel() {
            return numeHotel;
        }

        public void setNumeHotel(String numeHotel) {
            this.numeHotel = numeHotel;
        }

        public String getNumeLocatie() {
            return numeLocatie;
        }

        public void setNumeLocatie(String numeLocatie) {
            this.numeLocatie = numeLocatie;
        }

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }
    }
}
