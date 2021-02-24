package socialnetwork.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import socialnetwork.domain.Utilizator;
import socialnetwork.domain.exceptions.ValidationException;
import socialnetwork.service.FriendRequestService;
import socialnetwork.service.UtilizatorService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

public class EditUtilizatorController {
    private UtilizatorService serviceUtilizator;
    private FriendRequestService serviceFriendRequest;
    Stage dialogStage;
    Utilizator utilizator;

    @FXML
    TableView<Utilizator> tableView;

    @FXML
    javafx.scene.control.TextField textFieldNume;
    @FXML
    TextField textFieldPrenume;

    public void setUtilizatorService(UtilizatorService serviceUtilizator) {
        this.serviceUtilizator = serviceUtilizator;
    }

    public void setFriendRequestService(FriendRequestService serviceFriendRequest,  Stage stage, Utilizator utilizator) {
        this.serviceFriendRequest = serviceFriendRequest;
        this.dialogStage=stage;
        this.utilizator = utilizator;
    }

    @FXML
    public void initialize(){
    }

    @FXML
    public void handleSend(){
        String nume = textFieldNume.getText();
        String prenume = textFieldPrenume.getText();
        Utilizator prieten = null;
        try {
            prieten = serviceUtilizator.getUserByName(nume, prenume);
        }
        catch (NoSuchElementException e){
            MessageAlert.showErrorMessage(null, "Utilizator inexistent in lista de utilizatori ai aplicatiei!");
            return;
        }

        if(prieten == null){
            MessageAlert.showErrorMessage(null, "Nu exista utilizatorul in retea!");
        }
        else {
            ZoneId zid = ZoneId.of("Europe/Bucharest");
            LocalDateTime dt = LocalDateTime.now(zid);
            String date = dt.toString().replace('T', ' ').substring(0, 16);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

            try {
                serviceFriendRequest.sendFriendRequest(utilizator, prieten, dateTime);
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Trimitere cerere", "Cerere de prietenie trimisa cu suces!");
            }
            catch (ValidationException e){
                MessageAlert.showErrorMessage(null, e.getMessage());
            }
        }
        clearFields();
    }

    private void clearFields() {
        textFieldNume.setText("");
        textFieldPrenume.setText("");
    }

    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
}
