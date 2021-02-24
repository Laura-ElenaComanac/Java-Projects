package app.controller;

import app.domain.Intrebare;
import app.domain.Raspuns;
import app.domain.Rezultat;
import app.service.IntrebareService;
import app.service.RaspunsService;
import app.service.RezultatService;
import app.utils.events.Event;
import app.utils.events.RaspunsChangeEvent;
import app.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class IntrebareController implements Observer<Event> {
    private IntrebareService intrebareService;
    private RezultatService rezultatService;
    private RaspunsService raspunsService;

    ObservableList<Intrebare> modelIntrebari = FXCollections.observableArrayList();
    ObservableList<Intrebare> modelTest = FXCollections.observableArrayList();
    ObservableList<Raspuns> modelRaspunsuri = FXCollections.observableArrayList();

    Stage mainStage;

    @FXML
    TableView<Intrebare> tableViewIntrebari;
    @FXML
    TableView<Intrebare> tableViewTest;
    @FXML
    TableView<Raspuns> tableViewRaspunsuri;

    @FXML
    TableColumn<Intrebare, String> tableColumnNrIntrebare;
    @FXML
    TableColumn<Intrebare, String> tableColumnDescriere;
    @FXML
    TableColumn<Intrebare, String> tableColumnNrIntrebareTest;
    @FXML
    TableColumn<Intrebare, String> tableColumnDescriereTest;

    @FXML
    TableColumn<Raspuns, Integer> tableColumnNrRaspuns;
    @FXML
    TableColumn<Raspuns, String> tableColumnNumeRaspuns;
    @FXML
    TableColumn<Raspuns, Double> tableColumnPunctajRaspuns;


    public void setIntrebareService(IntrebareService intrebareService){
        this.intrebareService = intrebareService;
        intrebareService.addObserver(this);
    }

    public void setRezultatService(RezultatService rezultatService){
        this.rezultatService = rezultatService;
        rezultatService.addObserver(this);
    }

    public void setRaspunsService(RaspunsService raspunsService){
        this.raspunsService = raspunsService;
        raspunsService.addObserver(this);
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @FXML
    public void initialize(){
        tableColumnNrIntrebare.setCellValueFactory(new PropertyValueFactory<Intrebare,String>("id"));
        tableColumnDescriere.setCellValueFactory(new PropertyValueFactory<Intrebare,String>("descriere"));
        tableColumnNrIntrebareTest.setCellValueFactory(new PropertyValueFactory<Intrebare,String>("id"));
        tableColumnDescriereTest.setCellValueFactory(new PropertyValueFactory<Intrebare,String>("descriere"));

        tableColumnNrRaspuns.setCellValueFactory(new PropertyValueFactory<Raspuns, Integer>("nrIntrebare"));
        tableColumnNumeRaspuns.setCellValueFactory(new PropertyValueFactory<Raspuns, String>("numeStudent"));
        tableColumnPunctajRaspuns.setCellValueFactory(new PropertyValueFactory<Raspuns, Double>("punctaj"));
    }

    public void initModel(){
        Iterable<Intrebare> intrebari = intrebareService.getAllIntrebari();
        List<Intrebare> intrebariList = StreamSupport.stream(intrebari.spliterator(), false)
                .filter(e->!intrebareService.intrebareAleasa(e))
                .collect(Collectors.toList());

        modelIntrebari.setAll(intrebariList);
        tableViewIntrebari.setItems(modelIntrebari);

        Iterable<Intrebare> intrebariTest = intrebareService.getAllIntrebariTest();
        List<Intrebare> intrebariListTest = StreamSupport.stream(intrebariTest.spliterator(), false).collect(Collectors.toList());

        modelTest.setAll(intrebariListTest);
        tableViewTest.setItems(modelTest);

        Iterable<Raspuns> raspunsuri = raspunsService.getAllRaspunsuri();
        List<Raspuns> raspunsuriList = StreamSupport.stream(raspunsuri.spliterator(), false).collect(Collectors.toList());

        modelRaspunsuri.setAll(raspunsuriList);
        tableViewRaspunsuri.setItems(modelRaspunsuri);
    }

    @Override
    public void update(Event event) {
        initModel();
    }

    @FXML
    public void handlePlaseazaIntrebare(ActionEvent actionEvent) {
        Intrebare selected = tableViewIntrebari.getSelectionModel().getSelectedItem();

        if(selected!=null) {
            intrebareService.addIntrebare(selected);
            //intrebareService.deleteIntrebare(selected);
        }
        else{
            MessageAlert.showErrorMessage(null, "Nu s-a selectat nicio intrebare!");
        }
    }

    @FXML
    public void handleTrimiteRezultatele(ActionEvent actionEvent) {
        List<Raspuns> raspunsuri = raspunsService.getAllRaspunsuri();
        List<Rezultat> note = rezultatService.calculeazaNote(raspunsuri);

        for(Rezultat rez : note)
            rezultatService.addRezultat(rez);

        raspunsService.appendNumber(modelTest.stream().map(Intrebare::getPunctaj).reduce(0, Integer::sum));
    }


}
