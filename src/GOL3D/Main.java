package GOL3D;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Main class used to start the window for the 3D Game of Life.
 */
public class Main extends Application {

    private Stage primaryStage;

    public Stage getPrimaryStage(){
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        this.primaryStage = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("view3D.fxml"));
        Scene scene = new Scene(root,1200,650);
        scene.setFill(Color.BLACK);

        primaryStage.setTitle("Game of Life 3D");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}