package socialnetwork.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import socialnetwork.config.ApplicationContext;
import socialnetwork.controller.EditUtilizatorController;
import socialnetwork.controller.UtilizatorController;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.*;
import socialnetwork.repository.Repository;
import socialnetwork.repository.file.*;
import socialnetwork.service.FriendRequestService;
import socialnetwork.service.MessageService;
import socialnetwork.service.PrietenieService;
import socialnetwork.service.UtilizatorService;

import java.io.IOException;

public class MainApp extends Application {
    Repository<Long, Utilizator> userRepository;
    Repository<Tuple<Long, Long>, Prietenie> userRepositoryPrietenie;
    UtilizatorService userService;
    PrietenieService userServicePrietenie;

    MessageFile messageRepository;
    RecipientFile recipientRepository;
    MessageService messageService;

    FriendRequestFile friendRequestRepository;
    FriendRequestService friendRequestService;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        String fileNameUtilizatori = ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.users");
        String fileNamePrietenii = ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.prietenii");
        String fileNameMesaje = ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.mesaje");
        String fileNameRecipients = ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.recipients");
        String fileNameFriendRequests = ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.friendrequests");

        userRepository = new UtilizatorFile(fileNameUtilizatori, new UtilizatorValidator());
        userRepositoryPrietenie = new PrietenieFile(fileNamePrietenii, new PrietenieValidator());

        userService = new UtilizatorService(userRepository, userRepositoryPrietenie);
        userServicePrietenie = new PrietenieService(userRepository, userRepositoryPrietenie);

        messageRepository = new MessageFile(fileNameMesaje, new MessageValidator());
        recipientRepository = new RecipientFile(fileNameRecipients, new RecipientValidator());

        messageService = new MessageService(userRepository, messageRepository, recipientRepository);

        friendRequestRepository = new FriendRequestFile(fileNameFriendRequests, new FriendRequestValidator());
        friendRequestService = new FriendRequestService(friendRequestRepository, userRepository, userServicePrietenie);

        initView(primaryStage);
        primaryStage.setWidth(800);
        primaryStage.setTitle("My Social Network");
        primaryStage.show();
    }

    private void initView(Stage primaryStage) throws IOException {
        FXMLLoader utilizatorLoader = new FXMLLoader();
        utilizatorLoader.setLocation(getClass().getResource("/views/UtilizatorView.fxml"));
        AnchorPane utilizatorLayout = utilizatorLoader.load();
        primaryStage.setScene(new Scene(utilizatorLayout));

        UtilizatorController utilizatorController = utilizatorLoader.getController();
        friendRequestService.addObserver(utilizatorController);
        utilizatorController.setUtilizatorService(userService);
        utilizatorController.setPrietenieService(userServicePrietenie);
        utilizatorController.setServiceFriendRequest(friendRequestService);
        utilizatorController.setMessageService(messageService);
        utilizatorController.setMainStage(primaryStage);
    }
}
