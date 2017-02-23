package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.model.NexusNetwork;
import main.model.NexusParser;
import main.view.Graph;

import java.io.File;
import java.util.List;

/**
 * Created by NW on 22.02.2017.
 */
public class UI extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();

        HBox hBox = new HBox();
        Menu menuFile = new Menu("File");
        Menu menuOptions = new Menu("Options");

        MenuItem menuItemLoad = new MenuItem("Load");
        MenuItem menuItemExit = new MenuItem("Exit");

        Pane pane = new Pane();


        menuItemLoad.setOnAction((value) -> {
            final FileChooser fileChooser = new FileChooser();
            List<File> list =
                    fileChooser.showOpenMultipleDialog(primaryStage);
            if (list != null) {
                //TODO: Multiple File handling
                for (File file : list) {
                    try {
                        NexusNetwork nexusNetwork = NexusParser.parseNetworkData(file);
                        Graph g = new Graph(nexusNetwork);
                        g.drawOnPane(pane);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        menuItemExit.setOnAction((value) ->{
            Platform.exit();
            System.exit(0);
        });

        menuFile.getItems().addAll(menuItemLoad, menuItemExit);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menuFile, menuOptions);


        root.setTop(menuBar);
        root.setCenter(pane);
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }
}
