package socialnetwork.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import socialnetwork.domain.*;
import socialnetwork.domain.exceptions.ValidationException;
import socialnetwork.service.MessageService;
import socialnetwork.service.UtilizatorService;
import socialnetwork.utils.events.ChangeEventType;
import socialnetwork.utils.events.UtilizatorChangeEvent;
import socialnetwork.utils.observer.Observer;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class MessageController implements Observer<UtilizatorChangeEvent> {
    private UtilizatorService serviceUtilizator;
    private MessageService serviceMessage;

    ObservableList<Message> modelMessages = FXCollections.observableArrayList();
    ObservableList<Utilizator> modelUsers = FXCollections.observableArrayList();

    Stage dialogStage;
    Utilizator utilizator;

    @FXML
    TableView<Message> tableViewMessages;
    @FXML
    TableView<Utilizator> tableViewUsers;

    @FXML
    TextField textFieldMessage;

    TableColumn<Message, Long> tableColumnID = new TableColumn<>();
    @FXML
    TableColumn<Message, Message> tableColumnMessage;
    @FXML
    TableColumn<Message, Utilizator> tableColumnFrom;
    @FXML
    TableColumn<Message, List<Utilizator>> tableColumnTo;
    @FXML
    TableColumn<Message, LocalDateTime> tableColumnData;

    @FXML
    TableColumn<Utilizator, String> tableColumnNume;
    @FXML
    TableColumn<Utilizator, String> tableColumnPrenume;


    public void setUtilizatorService(UtilizatorService serviceUtilizator) {
        this.serviceUtilizator = serviceUtilizator;
    }

    public void setMessageService(MessageService serviceMessage,  Stage stage, Utilizator utilizator) {
        this.serviceMessage = serviceMessage;
        this.dialogStage=stage;
        this.utilizator = utilizator;
        initModel();
    }

    public void initModel(){
        try {
            List<Message> messages = serviceMessage.getMessagesByUser(utilizator);
            modelMessages.setAll(messages);

            List<Utilizator> users = serviceUtilizator.getAllUsers();
            modelUsers.setAll(users);
        }
        catch (ValidationException e){
            MessageAlert.showErrorMessage(null, "Utilizator inexistent in lista de utilizatori ai aplicatiei!");
        }
    }

    @Override
    public void update(UtilizatorChangeEvent utilizatorChangeEvent) {
        initModel();
    }

    @FXML
    public void initialize(){
        tableColumnID.setCellValueFactory(new PropertyValueFactory<Message,Long>("id"));
        tableColumnMessage.setCellValueFactory(new PropertyValueFactory<Message,Message>("this"));
        tableColumnFrom.setCellValueFactory(new PropertyValueFactory<Message,Utilizator>("from"));
        tableColumnTo.setCellValueFactory(new PropertyValueFactory<Message,List<Utilizator>>("to"));
        tableColumnData.setCellValueFactory(new PropertyValueFactory<Message,LocalDateTime>("data"));

        tableColumnNume.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("firstName"));
        tableColumnPrenume.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("lastName"));

        tableViewMessages.setItems(modelMessages);
        tableViewUsers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableViewUsers.setItems(modelUsers);
    }

    @FXML
    public void handleCancel(){
        dialogStage.close();
    }

    public void handleSendMessage(ActionEvent actionEvent) {
        List<Utilizator> selected = tableViewUsers.getSelectionModel().getSelectedItems();

        if(selected!=null){
            try {
                LinkedList<Utilizator> list = new LinkedList<>(selected);

                ZoneId zid = ZoneId.of("Europe/Bucharest");
                LocalDateTime dt = LocalDateTime.now(zid);
                String date = dt.toString().replace('T', ' ').substring(0,16);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

                serviceMessage.sendMessage(utilizator, list, textFieldMessage.getText(), dateTime);

                initModel();
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Trimitere mesaj", "Mesaj trimis cu suces!");
            }
            catch (ValidationException e){
                MessageAlert.showErrorMessage(null, e.getMessage());
            }
        }
        else
            MessageAlert.showErrorMessage(null, "Nu s-a selectat niciun utilizator!");
    }

    public void handleReply(ActionEvent actionEvent) {
        Message selected = tableViewMessages.getSelectionModel().getSelectedItem();

        if(selected!=null){
            try {
                ZoneId zid = ZoneId.of("Europe/Bucharest");
                LocalDateTime dt = LocalDateTime.now(zid);
                String date = dt.toString().replace('T', ' ').substring(0,16);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

                serviceMessage.replyMessage(selected.getId(), utilizator, textFieldMessage.getText(), dateTime);

                initModel();
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Raspunde la mesaj", "Mesaj trimis cu suces!");
            }
            catch (ValidationException e){
                MessageAlert.showErrorMessage(null, e.getMessage());
            }
        }
        else
            MessageAlert.showErrorMessage(null, "Nu s-a selectat niciun mesaj!");
    }

    public void handleReplyAll(ActionEvent actionEvent) {
        Message selected = tableViewMessages.getSelectionModel().getSelectedItem();

        if(selected!=null){
            try {
                ZoneId zid = ZoneId.of("Europe/Bucharest");
                LocalDateTime dt = LocalDateTime.now(zid);
                String date = dt.toString().replace('T', ' ').substring(0,16);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

                serviceMessage.replyAllMessage(selected.getId(), utilizator, textFieldMessage.getText(), dateTime);

                initModel();
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Raspunde la mesaj", "Mesaj trimis cu suces!");
            }
            catch (ValidationException e){
                MessageAlert.showErrorMessage(null, e.getMessage());
            }
        }
        else
            MessageAlert.showErrorMessage(null, "Nu s-a selectat niciun mesaj!");
    }
}
