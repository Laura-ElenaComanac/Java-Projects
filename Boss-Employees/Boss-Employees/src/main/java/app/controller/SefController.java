package app.controller;

import app.domain.Discutie;
import app.domain.DiscutieSef;
import app.domain.Echipa;
import app.service.DiscutieService;
import app.service.EchipaService;
import app.utils.events.Event;
import app.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SefController implements Observer<Event>  {
    private EchipaService echipaService, echipaActivaService;
    private DiscutieService discutieService;

    ObservableList<Echipa> modelMembri = FXCollections.observableArrayList();
    ObservableList<Echipa> modelMembriActivi = FXCollections.observableArrayList();
    ObservableList<Discutie> modelMesaje = FXCollections.observableArrayList();

    Stage mainStage;

    @FXML
    TextField mesajField;

    @FXML
    TableView<Echipa> tableViewMembriActivi;
    @FXML
    TableView<Discutie> tableViewMesajePrimite;

    @FXML
    TableColumn<Echipa, String> tableColumnNumeMembru;
    @FXML
    TableColumn<Echipa, String> tableColumnRolMembru;

    @FXML
    TableColumn<Discutie, String> tableColumnNumeMesaj;
    @FXML
    TableColumn<Discutie, String> tableColumnMesaj;
    @FXML
    TableColumn<Discutie, LocalDateTime> tableColumnOraMesaj;


    public void setEchipaActivaService(EchipaService echipaActivaService){
        this.echipaActivaService = echipaActivaService;
        echipaActivaService.addObserver(this);
    }

    public void setEchipaService(EchipaService echipaService){
        this.echipaService = echipaService;
        echipaService.addObserver(this);
    }

    public void setDiscutieService(DiscutieService discutieService){
        this.discutieService = discutieService;
        discutieService.addObserver(this);
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @FXML
    public void initialize(){
        tableColumnNumeMembru.setCellValueFactory(new PropertyValueFactory<Echipa,String>("nume"));
        tableColumnRolMembru.setCellValueFactory(new PropertyValueFactory<Echipa,String>("rol"));

        tableColumnNumeMesaj.setCellValueFactory(new PropertyValueFactory<Discutie, String>("numeExpeditor"));
        tableColumnMesaj.setCellValueFactory(new PropertyValueFactory<Discutie, String>("mesaj"));
        tableColumnOraMesaj.setCellValueFactory(new PropertyValueFactory<Discutie, LocalDateTime>("ora"));
    }

    public String numeSef(){
        String numeSef = "";
        for (Echipa membru : echipaActivaService.getAllEntities()) {
            if (membru.getRol().equals("sef"))
                numeSef = membru.getNume();
        }
        return numeSef;
    }

    public void initModel(){
        Iterable<Echipa> membriActivi = echipaActivaService.getAllEntities();
        List<Echipa> membriActiviList = StreamSupport.stream(membriActivi.spliterator(), false).collect(Collectors.toList());

        modelMembriActivi.setAll(membriActiviList);
        tableViewMembriActivi.setItems(modelMembriActivi);

        Iterable<Discutie> discutii = discutieService.getAllEntities();
        List<Discutie> discutiiList = StreamSupport.stream(discutii.spliterator(), false)
                .filter(d->!d.getNumeExpeditor().equals(numeSef()))
                .collect(Collectors.toList());

        modelMesaje.setAll(discutiiList);
        tableViewMesajePrimite.setItems(modelMesaje);
    }

    @Override
    public void update(Event event) {
        initModel();
    }

    @FXML
    public void handleMaRetrag(ActionEvent actionEvent) {
        if(echipaActivaService.getAllEntities().size()==1) {
            for (Echipa membru : echipaActivaService.getAllEntities())
                echipaActivaService.deleteEntity(membru);
        }
        else{
            MessageAlert.showErrorMessage(null, "Mai sunt membri acitivi in echipa!");
        }
    }

    @FXML
    public void handleTrimiteMesajLaToti(ActionEvent actionEvent) {
        String mesajText = mesajField.getText();

        ZoneId zid = ZoneId.of("Europe/Bucharest");
        LocalDateTime dt = LocalDateTime.now(zid);
        String date = dt.toString().replace('T', ' ').substring(0,16);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

        Discutie mesaj = new Discutie(numeSef(),mesajText,dateTime);

        discutieService.addEntity(mesaj);
    }


    @FXML
    public void handleTrimiteMesajLaUnul(ActionEvent actionEvent) {
        Echipa selected = tableViewMembriActivi.getSelectionModel().getSelectedItem();

        String mesajText = mesajField.getText();

        ZoneId zid = ZoneId.of("Europe/Bucharest");
        LocalDateTime dt = LocalDateTime.now(zid);
        String date = dt.toString().replace('T', ' ').substring(0,16);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

        DiscutieSef mesaj = new DiscutieSef(numeSef(),mesajText,dateTime,selected.getNume());

        discutieService.addEntity(mesaj);
    }
}
