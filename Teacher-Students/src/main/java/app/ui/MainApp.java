package app.ui;

import app.config.ApplicationContext;
import app.controller.IntrebareController;
import app.controller.RaspunsController;
import app.repository.InMemoryRepository;
import app.repository.file.RaspunsFile;
import app.repository.file.RezultatFile;
import app.service.RaspunsService;
import app.service.RezultatService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import app.repository.file.IntrebareFile;
import app.service.IntrebareService;

import java.io.IOException;

public class MainApp extends Application {
    IntrebareFile intrebareRepo;
    IntrebareService intrebareService;
    RezultatFile rezultatRepo;
    RezultatService rezultatService;
    RaspunsFile raspunsRepo;
    RaspunsService raspunsService;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        String fileNameIntrebari = ApplicationContext.getPROPERTIES().getProperty("data.app.intrebari");
        intrebareRepo = new IntrebareFile(fileNameIntrebari);
        intrebareService = new IntrebareService(intrebareRepo);

        //String fileNammeRezultate = ApplicationContext.getPROPERTIES().getProperty("data.app.rezultate");
        //rezultatRepo = new RezultatFile(fileNammeRezultate);
        //rezultatService = new RezultatService(rezultatRepo);
        rezultatService = new RezultatService(new InMemoryRepository<>());

        String fileNammeRaspunsuri= ApplicationContext.getPROPERTIES().getProperty("data.app.raspunsuri");
        raspunsRepo = new RaspunsFile(fileNammeRaspunsuri);
        raspunsService = new RaspunsService(raspunsRepo);

        initView(primaryStage);
        //primaryStage.setWidth(800);
        primaryStage.setTitle("Profesor");
        primaryStage.show();
    }

    private void initView(Stage profesorStage) throws IOException {
        FXMLLoader intrebareLoader = new FXMLLoader();
        intrebareLoader.setLocation(getClass().getResource("/views/IntrebareView.fxml"));
        GridPane intrebareLayout = intrebareLoader.load();
        profesorStage.setScene(new Scene(intrebareLayout));

        IntrebareController intrebareController = intrebareLoader.getController();
        intrebareController.setMainStage(profesorStage);
        intrebareController.setIntrebareService(intrebareService);
        intrebareController.setRezultatService(rezultatService);
        intrebareController.setRaspunsService(raspunsService);
        intrebareController.initModel();
        getParameters().getRaw().forEach(this::createStudent);
    }

    private void createStudent(String nume)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/RaspunsView.fxml"));
            GridPane root =  loader.load();

            // stage
            Stage studentStage = new Stage();
            studentStage.setTitle(nume);
            studentStage.setUserData(nume);
            studentStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            studentStage.setScene(scene);

            RaspunsController raspunsController = loader.getController();
            raspunsController.setRaspunsService(raspunsService);
            raspunsController.setRezultatService(rezultatService);
            intrebareService.addObserver(raspunsController);
            raspunsController.setMainStage(studentStage);
            studentStage.show();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
