package app.controller;

import app.domain.Hotel;
import app.domain.Reservation;
import app.service.*;
import app.utils.events.Event;
import app.utils.observer.Observer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


public class RezervareController implements Observer<Event> {
    private SpecialOfferService specialOfferService;
    private LocationService locationService;
    private HotelService hotelService;
    private ReservationService reservationService;

    Hotel hotel;
    Long clientId;

    Stage mainStage;

    @FXML
    DatePicker datePickerStart;
    @FXML
    DatePicker datePickerEnd;


    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }


    public void setReservationService(ReservationService reservationService, Hotel hotel, Long clientId){
        this.hotel = hotel;
        this.reservationService = reservationService;
        this.clientId = clientId;
        reservationService.addObserver(this);
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

    @FXML
    public void initialize(){
    }

    public void initModel() {

    }


    @Override
    public void update(Event event) {
        initModel();
    }


    public void handleRezerva(ActionEvent event) {
        LocalDate dateStart = datePickerStart.getValue();
        LocalDate dateEnd = datePickerEnd.getValue();

        Reservation reservation = new Reservation(reservationService.size()+1.0,clientId,hotel.getHotelId(), dateStart.atStartOfDay(),dateEnd.getDayOfMonth()-dateStart.getDayOfMonth());

        reservationService.addEntity(reservation);
    }
}
