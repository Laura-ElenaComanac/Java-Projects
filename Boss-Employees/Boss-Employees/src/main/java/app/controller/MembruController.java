package app.controller;

import app.domain.Discutie;
import app.domain.DiscutieSef;
import app.domain.Echipa;
import app.service.DiscutieService;
import app.service.EchipaService;
import app.utils.events.DiscutieChangeEvent;
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

public class MembruController implements Observer<Event>  {
    private EchipaService echipaService, echipaActivaService;
    private DiscutieService discutieService;

    ObservableList<Echipa> modelMembri = FXCollections.observableArrayList();
    ObservableList<Echipa> modelMembriActivi = FXCollections.observableArrayList();
    ObservableList<Discutie> modelMesaje = FXCollections.observableArrayList();

    Stage mainStage;

    Boolean aFostInactiv = false;
    Boolean esteActiv = true;

    @FXML
    TextField mesajField;

    @FXML
    TableView<Echipa> tableViewMembri;
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

    public void initModel(Event event){
        Iterable<Echipa> membriActivi = echipaActivaService.getAllEntities();
        List<Echipa> membriActiviList = StreamSupport.stream(membriActivi.spliterator(), false).collect(Collectors.toList());

        modelMembriActivi.setAll(membriActiviList);
        tableViewMembriActivi.setItems(modelMembriActivi);

        if(event instanceof DiscutieChangeEvent) {
            Discutie d  = ((DiscutieChangeEvent) event).getData();

            Boolean cond1 = !d.getNumeExpeditor().equals(mainStage.getTitle());
            if(d instanceof DiscutieSef) {
                if (((DiscutieSef) d).getDestinatar().equals(mainStage.getTitle())) {
                    modelMesaje.add(d);
                    tableViewMesajePrimite.setItems(modelMesaje);
                }
            }else {
                if (cond1) {
                    modelMesaje.add(d);
                    tableViewMesajePrimite.setItems(modelMesaje);
                }
            }

        }
    }

    @Override
    public void update(Event event) {
        initModel(event);
    }

    @FXML
    public void handleMaRetrag(ActionEvent actionEvent) {
        Echipa membru = echipaActivaService.getUserByName(mainStage.getTitle());
        echipaActivaService.deleteEntity(membru);
        discutieService.removeObserver(this);
        aFostInactiv = true;
        esteActiv = false;
    }

    @FXML
    public void handleRevin(ActionEvent actionEvent) {
        Echipa membru = echipaService.getUserByName(mainStage.getTitle());
        echipaActivaService.addEntity(membru);
        discutieService.addObserver(this);
        esteActiv = true;
    }

    @FXML
    public void handleMesajePierdute(ActionEvent actionEvent) {
        if(!aFostInactiv) {
            MessageAlert.showErrorMessage(null, "Nu ai fost inactiv!");
            return;
        }
        if(!esteActiv)
            return;

        aFostInactiv = false;
        Iterable<Discutie> discutii = discutieService.getAllEntities();
        List<Discutie> discutiiList = StreamSupport.stream(discutii.spliterator(), false)
                .filter(d->{
                    Boolean cond1 = !d.getNumeExpeditor().equals(mainStage.getTitle());
                    if(d instanceof DiscutieSef) {
                        if (((DiscutieSef) d).getDestinatar().equals(mainStage.getTitle()))
                            return true;
                    }else {
                        if(cond1)
                            return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());

        modelMesaje.setAll(discutiiList);
        tableViewMesajePrimite.setItems(modelMesaje);
    }

    @FXML
    public void handletrimiteMesaj(ActionEvent actionEvent) {
        Echipa membru = echipaActivaService.getUserByName(mainStage.getTitle());

        String mesajText = mesajField.getText();

        ZoneId zid = ZoneId.of("Europe/Bucharest");
        LocalDateTime dt = LocalDateTime.now(zid);
        String date = dt.toString().replace('T', ' ').substring(0,16);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

        Discutie mesaj = new Discutie(membru.getNume(),mesajText,dateTime);

        discutieService.addEntity(mesaj);
    }
}
