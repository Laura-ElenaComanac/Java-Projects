package app.ui;

import app.config.ApplicationContext;
import app.controller.MembruController;
import app.controller.SefController;
import app.domain.Echipa;
import app.repository.InMemoryRepository;
import app.repository.file.DiscutieFile;
import app.service.DiscutieService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import app.repository.file.EchipaFile;
import app.service.EchipaService;

import java.io.IOException;

public class MainApp extends Application {
    EchipaFile echipaRepo, echipaActivaRepo;
    EchipaService echipaService, echipaActivaService;
    DiscutieFile discutieRepo;
    DiscutieService discutieService;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        String fileNameDiscutii = ApplicationContext.getPROPERTIES().getProperty("data.app.discutii");
        discutieRepo = new DiscutieFile(fileNameDiscutii);
        discutieService = new DiscutieService(discutieRepo);

        String fileNammeEchipa= ApplicationContext.getPROPERTIES().getProperty("data.app.echipa");
        echipaRepo = new EchipaFile(fileNammeEchipa);
        echipaService = new EchipaService(echipaRepo);

        echipaActivaRepo = new EchipaFile(fileNammeEchipa);
        echipaActivaService = new EchipaService(echipaActivaRepo);

        initView(primaryStage);
        //primaryStage.setWidth(800);
        primaryStage.setTitle("Sefu");
        primaryStage.show();
    }

    private void initView(Stage sefuStage) throws IOException {
        FXMLLoader sefLoader = new FXMLLoader();
        sefLoader.setLocation(getClass().getResource("/views/SefView.fxml"));
        GridPane sefLayout = sefLoader.load();
        sefuStage.setScene(new Scene(sefLayout));

        SefController sefController = sefLoader.getController();
        sefController.setMainStage(sefuStage);
        sefController.setEchipaService(echipaService);
        sefController.setEchipaActivaService(echipaActivaService);
        sefController.setDiscutieService(discutieService);
        sefController.initModel();
        //getParameters().getRaw().forEach(this::createMembru);
        for(Echipa echipa : echipaService.getAllEntities())
            if(echipa.getRol().equals("membru"))
                createMembru(echipa.getNume());
    }

    private void createMembru(String nume)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/MembruView.fxml"));

            GridPane root =  loader.load();

            // stage
            Stage membruStage = new Stage();
            membruStage.setTitle(nume);
            membruStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            membruStage.setScene(scene);

            MembruController membruController = loader.getController();
            membruController.setEchipaService(echipaService);
            membruController.setEchipaActivaService(echipaActivaService);
            membruController.setDiscutieService(discutieService);
            membruController.initModel(null);

            echipaService.addObserver(membruController);
            echipaActivaService.addObserver(membruController);

            membruController.setMainStage(membruStage);
            membruStage.show();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
