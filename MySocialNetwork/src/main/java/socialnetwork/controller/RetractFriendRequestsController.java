package socialnetwork.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import socialnetwork.domain.FriendRequest;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.exceptions.ValidationException;
import socialnetwork.service.FriendRequestService;
import socialnetwork.service.UtilizatorService;
import socialnetwork.utils.events.ChangeEventType;
import socialnetwork.utils.events.UtilizatorChangeEvent;
import socialnetwork.utils.observer.Observer;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RetractFriendRequestsController implements Observer<UtilizatorChangeEvent> {
    private UtilizatorService serviceUtilizator;
    private FriendRequestService serviceFriendRequest;
    ObservableList<FriendRequestEntity> model = FXCollections.observableArrayList();

    Stage dialogStage;
    Utilizator utilizator;

    @FXML
    TableView<FriendRequestEntity> tableView;

    @FXML
    TableColumn<FriendRequestEntity, String> tableColumnNume;
    @FXML
    TableColumn<FriendRequestEntity, String> tableColumnPrenume;
    @FXML
    TableColumn<FriendRequestEntity, String> tableColumnStatus;
    @FXML
    TableColumn<FriendRequestEntity, String> tableColumnData;

    public void setUtilizatorService(UtilizatorService serviceUtilizator) {
        this.serviceUtilizator = serviceUtilizator;
    }

    public void setFriendRequestService(FriendRequestService serviceFriendRequest,  Stage stage, Utilizator utilizator) {
        this.serviceFriendRequest = serviceFriendRequest;
        this.dialogStage=stage;
        this.utilizator = utilizator;
        initModel();
    }

    public void initModel(){
        Iterable<FriendRequest> friendRequests = serviceFriendRequest.getAllFriendRequests();
        List<FriendRequestEntity> friendRequestsEntities = StreamSupport.stream(friendRequests.spliterator(), false)
                .filter(f -> f.getFrom().equals(utilizator.getId()))
                .map(f -> {
                            Utilizator util = serviceUtilizator.getUserById(f.getTo());
                            return new FriendRequestEntity(util.getFirstName(),util.getLastName(),f.getStatus(),f.getData().toString().replace("T"," "));
                        }
                )
                .collect(Collectors.toList());

        model.setAll(friendRequestsEntities);

    }

    @Override
    public void update(UtilizatorChangeEvent utilizatorChangeEvent) {
        initModel();
    }

    @FXML
    public void initialize(){
        tableColumnNume.setCellValueFactory(new PropertyValueFactory<FriendRequestEntity,String>("nume"));
        tableColumnPrenume.setCellValueFactory(new PropertyValueFactory<FriendRequestEntity,String>("prenume"));
        tableColumnStatus.setCellValueFactory(new PropertyValueFactory<FriendRequestEntity,String>("status"));
        tableColumnData.setCellValueFactory(new PropertyValueFactory<FriendRequestEntity,String>("data"));
        tableView.setItems(model);
    }

    @FXML
    public void handleLoadFriends(ActionEvent actionEvent) {
        initModel();
    }

    @FXML
    public void handleCancel(){
        //serviceFriendRequest.removeObserver(this);
        dialogStage.close();
    }

    @FXML
    public void handleRetract(){
        FriendRequestEntity selected = (FriendRequestEntity) tableView.getSelectionModel().getSelectedItem();

        if(selected!=null){
            Utilizator to = new Utilizator(selected.getNume(), selected.getPrenume());
            try {
                serviceFriendRequest.retractFriendRequest(utilizator, to);
                initModel();
                serviceFriendRequest.notifyObservers(new UtilizatorChangeEvent(ChangeEventType.UPDATE, this.utilizator));
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Retrage cerere", "Cerere retrasa cu succes!");
            }
            catch (ValidationException e){
                MessageAlert.showErrorMessage(null, e.getMessage());
            }
        }
        else{
            MessageAlert.showErrorMessage(null, "Nu s-a selectat niciun prieten!");
        }
    }


    public static class FriendRequestEntity{
        String nume;
        String prenume;
        String status;
        String data;

        public FriendRequestEntity(String nume, String prenume, String status, String data){
            this.nume = nume;
            this.prenume = prenume;
            this.status = status;
            this.data = data;
        }

        public String getNume() {
            return nume;
        }
        public String getPrenume() {
            return prenume;
        }
        public String getStatus() {
            return status;
        }
        public String getData() {
            return data;
        }

        public void setNume(String nume) {
            this.nume = nume;
        }

        public void setPrenume(String prenume) {
            this.prenume = prenume;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setData(String  data) {
            this.data = data;
        }
    }
}
