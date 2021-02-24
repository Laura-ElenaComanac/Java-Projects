package app.controller;

import app.domain.Intrebare;
import app.domain.Raspuns;
import app.domain.Rezultat;
import app.service.IntrebareService;
import app.service.RaspunsService;
import app.service.RezultatService;
import app.utils.events.Event;
import app.utils.events.IntrebareChangeEvent;
import app.utils.events.RezultatChangeEvent;
import app.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RaspunsController implements Observer<Event> {
    private RaspunsService raspunsService;
    private RezultatService rezultatService;

    ObservableList<Intrebare> modelIntrebari = FXCollections.observableArrayList();
    ObservableList<Rezultat> modelRezultate = FXCollections.observableArrayList();

    Stage mainStage;

    @FXML
    RadioButton v1;
    @FXML
    RadioButton v2;
    @FXML
    RadioButton v3;

    @FXML
    TableView<Intrebare> tableViewIntrebari;
    @FXML
    TableView<Rezultat> tableViewRezultate;

    @FXML
    TableColumn<Intrebare, String> tableColumnNrIntrebare;
    @FXML
    TableColumn<Intrebare, String> tableColumnDescriere;
    @FXML
    TableColumn<Rezultat, String> tableColumnRezultatNume;
    @FXML
    TableColumn<Rezultat, Double> tableColumnRezultatNota;


    public void setRaspunsService(RaspunsService raspunsService){
        this.raspunsService = raspunsService;
        raspunsService.addObserver(this);
    }

    public void setRezultatService(RezultatService rezultatService){
        this.rezultatService = rezultatService;
        rezultatService.addObserver(this);
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @FXML
    public void initialize(){
        tableColumnNrIntrebare.setCellValueFactory(new PropertyValueFactory<Intrebare,String>("id"));
        tableColumnDescriere.setCellValueFactory(new PropertyValueFactory<Intrebare,String>("descriere"));

        tableColumnRezultatNume.setCellValueFactory((new PropertyValueFactory<Rezultat,String>("numeStudent")));
        tableColumnRezultatNota.setCellValueFactory(new PropertyValueFactory<Rezultat,Double>("nota"));

        tableViewIntrebari.setItems(modelIntrebari);
        tableViewRezultate.setItems(modelRezultate);
    }

    public void initRadio(Intrebare intrebare){
        this.v1.setText(intrebare.getV1());
        this.v2.setText(intrebare.getV2());
        this.v3.setText(intrebare.getV3());
    }

    public void initModelIntrebari(Intrebare intrebare){
        modelIntrebari.add(intrebare);
    }

    public void initModelRezultate(){
        Iterable<Rezultat> rezultate = rezultatService.getAllRezultate();
        List<Rezultat> rezultateList = StreamSupport.stream(rezultate.spliterator(), false).collect(Collectors.toList());

        modelRezultate.setAll(rezultateList);
        tableViewRezultate.setItems(modelRezultate);
    }

    @Override
    public void update(Event event) {
        if(event instanceof IntrebareChangeEvent) {
            initModelIntrebari(((IntrebareChangeEvent) event).getData());
        }
        initModelRezultate();
    }

    public void handlev1(ActionEvent actionEvent) {
        this.v2.setSelected(false);
        this.v3.setSelected(false);
    }
    public void handlev2(ActionEvent actionEvent) {
        this.v1.setSelected(false);
        this.v3.setSelected(false);
    }
    public void handlev3(ActionEvent actionEvent) {
        this.v1.setSelected(false);
        this.v2.setSelected(false);
    }

    public void handleSelectIntrebare(MouseEvent mouseEvent) {
        Intrebare selected = tableViewIntrebari.getSelectionModel().getSelectedItem();

        if(selected!=null) {
            this.initRadio(selected);
        }
        else{
            MessageAlert.showErrorMessage(null, "Nu s-a selectat nicio intrebare!");
        }
    }

    public void handleTrimiteRaspuns(ActionEvent actionEvent) {
        Intrebare selected = tableViewIntrebari.getSelectionModel().getSelectedItem();

        String raspunsSelected = "";
        if(v1.isSelected())
            raspunsSelected = v1.getText();
        if(v2.isSelected())
            raspunsSelected = v2.getText();
        if(v3.isSelected())
            raspunsSelected = v3.getText();

        //String rez = (String) mainStage.getUserData();

        Raspuns raspuns = new Raspuns(selected.getId(),mainStage.getTitle(),0);

        if(selected.getRaspunsCorect().equals(raspunsSelected))
            raspuns.setPunctaj(selected.getPunctaj());

        raspunsService.addRaspuns(raspuns);

        modelIntrebari.remove(selected);
    }
}
