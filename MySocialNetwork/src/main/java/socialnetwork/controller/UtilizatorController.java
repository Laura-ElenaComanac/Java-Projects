package socialnetwork.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import socialnetwork.domain.FriendRequest;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.service.FriendRequestService;
import socialnetwork.service.MessageService;
import socialnetwork.service.PrietenieService;
import socialnetwork.service.UtilizatorService;

import javafx.event.ActionEvent;
import socialnetwork.utils.events.ChangeEventType;
import socialnetwork.utils.events.UtilizatorChangeEvent;
import socialnetwork.utils.observer.Observer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UtilizatorController implements Observer<UtilizatorChangeEvent> {
    private UtilizatorService serviceUtilizator;
    private PrietenieService servicePrietenie;
    private FriendRequestService serviceFriendRequest;
    private MessageService serviceMessage;
    ObservableList<Utilizator> model = FXCollections.observableArrayList();
    Stage mainStage;

    @FXML
    TableView<Utilizator> tableView;

    @FXML
    TextField textFieldNume;
    @FXML
    TextField textFieldPrenume;
    @FXML
    TableColumn<Utilizator, String> tableColumnNume;
    @FXML
    TableColumn<Utilizator, String> tableColumnPrenume;

    public void setUtilizatorService(UtilizatorService utilizatorService){
        serviceUtilizator = utilizatorService;
        serviceUtilizator.addObserver(this);
    }

    public void setPrietenieService(PrietenieService prietenieService){
        servicePrietenie = prietenieService;
        servicePrietenie.addObserver(this);
    }

    public void setServiceFriendRequest(FriendRequestService serviceFriendRequest){
        this.serviceFriendRequest = serviceFriendRequest;
    }

    public void setMessageService(MessageService serviceMessage){
        this.serviceMessage = serviceMessage;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @FXML
    public void initialize(){
        tableColumnNume.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("firstName"));
        tableColumnPrenume.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("lastName"));
        tableView.setItems(model);
    }

    public void initModel(Utilizator utilizator){
        Iterable<Utilizator> prieteni = utilizator.getFriends();
        List<Utilizator> prieteniList = StreamSupport.stream(prieteni.spliterator(), false).collect(Collectors.toList());

        model.setAll(prieteniList);
    }

    @Override
    public void update(UtilizatorChangeEvent utilizatorChangeEvent) {
        initModel(utilizatorChangeEvent.getData());
    }

    @FXML
    public void handleLoadFriends(ActionEvent actionEvent) {
        try {
            initModel(serviceUtilizator.getUserByName(textFieldNume.getText(),textFieldPrenume.getText()));
        }
        catch (NoSuchElementException e){
            model.clear();
            MessageAlert.showErrorMessage(null, "Nu exista utilizatorul in retea!");
        }
    }

    @FXML
    public void handleDeletePrieten(ActionEvent actionEvent){
        Utilizator selected = (Utilizator) tableView.getSelectionModel().getSelectedItem();

        if(selected!=null){
            Utilizator utilizator1 = serviceUtilizator.getUserByName(selected.getFirstName(),selected.getLastName());
            Utilizator utilizator2 = serviceUtilizator.getUserByName(textFieldNume.getText(),textFieldPrenume.getText());

            Prietenie prietenie = servicePrietenie.getFriendshipByUsers(utilizator1,utilizator2);
            Prietenie deleted = servicePrietenie.deletePrietenie(utilizator1,utilizator2,prietenie.getDate()).get();
            if(deleted!=null){
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Stergere", "Prieten sters cu suces!");
                initModel(utilizator2);
            }
        }
        else{
            MessageAlert.showErrorMessage(null, "Nu s-a selectat niciun prieten!");
        }
    }

    @FXML
    public void handleAddPrieten(ActionEvent actionEvent) {
        Utilizator utilizator;
        try {
            utilizator = serviceUtilizator.getUserByName(textFieldNume.getText(), textFieldPrenume.getText());
            serviceFriendRequest.notifyObservers(new UtilizatorChangeEvent(ChangeEventType.ADD, utilizator));
        }
        catch (NoSuchElementException e){
            MessageAlert.showErrorMessage(null, "Utilizator inexistent in lista de utilizatori ai aplicatiei!");
            return;
        }
        showUtilizatorEditDialog(utilizator);
    }

    public void showUtilizatorEditDialog(Utilizator utilizator){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/editUtilizatorView.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            // stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Adauga un nou prieten");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            EditUtilizatorController editController = loader.getController();
            editController.setUtilizatorService(serviceUtilizator);
            editController.setFriendRequestService(serviceFriendRequest, dialogStage, utilizator);
            dialogStage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void handleShowFriendRequests(ActionEvent actionEvent) {
        Utilizator utilizator;
        try {
            utilizator = serviceUtilizator.getUserByName(textFieldNume.getText(), textFieldPrenume.getText());
        }
        catch (NoSuchElementException e){
            MessageAlert.showErrorMessage(null, "Utilizator inexistent in lista de utilizatori ai aplicatiei!");
            return;
        }
        showFriendRequestsEditDialog(utilizator);
    }

    public void showFriendRequestsEditDialog(Utilizator utilizator) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/FriendRequestsView.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            // stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cereri de prietenie");
            dialogStage.initOwner(mainStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            FriendRequestsController friendRequestsController = loader.getController();
            //serviceFriendRequest.addObserver(friendRequestsController);

            friendRequestsController.setUtilizatorService(serviceUtilizator);
            friendRequestsController.setFriendRequestService(serviceFriendRequest, dialogStage, utilizator);
            dialogStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRetractFriendRequests(ActionEvent actionEvent) {
        Utilizator utilizator;
        try {
            utilizator = serviceUtilizator.getUserByName(textFieldNume.getText(), textFieldPrenume.getText());
        }
        catch (NoSuchElementException e){
            MessageAlert.showErrorMessage(null, "Utilizator inexistent in lista de utilizatori ai aplicatiei!");
            return;
        }
        showRetractFriendRequestsEditDialog(utilizator);
    }

    public void showRetractFriendRequestsEditDialog(Utilizator utilizator) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/RetractFriendRequestsView.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            // stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Retrage cereri de prietenie");
            dialogStage.initOwner(mainStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            RetractFriendRequestsController retractfriendRequestsController = loader.getController();
            //serviceFriendRequest.addObserver(friendRequestsController);

            retractfriendRequestsController.setUtilizatorService(serviceUtilizator);
            retractfriendRequestsController.setFriendRequestService(serviceFriendRequest, dialogStage, utilizator);
            dialogStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleMessages(ActionEvent actionEvent) {
        Utilizator utilizator;
        try {
            utilizator = serviceUtilizator.getUserByName(textFieldNume.getText(), textFieldPrenume.getText());
        }
        catch (NoSuchElementException e){
            MessageAlert.showErrorMessage(null, "Utilizator inexistent in lista de utilizatori ai aplicatiei!");
            return;
        }
        showMessagesEditDialog(utilizator);
    }

    public void showMessagesEditDialog(Utilizator utilizator) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/MessageView.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            // stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Trimite mesaje");
            dialogStage.initOwner(mainStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            MessageController messageController = loader.getController();
            //serviceFriendRequest.addObserver(friendRequestsController);

            messageController.setUtilizatorService(serviceUtilizator);
            messageController.setMessageService(serviceMessage, dialogStage, utilizator);
            messageController.initModel();
            dialogStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
