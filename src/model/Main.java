package model;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Main class used to start the JavaFX window for the main game.
 *
 * @author  Miina Lervik
 * @author  Elise Vannes
 * @author  Alexander Kingdon
 * @since   1.0
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/GOL.fxml"));
        primaryStage.setTitle("Game of Life");
        primaryStage.getIcons().add(new Image("icons/gol_icon.png"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
