package GOL3D;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    //protected Parent root;

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*Box box = new Box(5, 5, 5);
        box.setMaterial(new PhongMaterial(Color.GREENYELLOW));

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.getTransforms().addAll (rotateX, rotateY, new Translate(0, 0, -20));

        Group root3D = new Group(camera,box);

        SubScene subScene = new SubScene(root3D, 300, 300, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.AQUAMARINE);
        subScene.setCamera(camera);*/

        Parent root = FXMLLoader.load(getClass().getResource("view3D.fxml"));
        Scene scene = new Scene(root,1200,650);
        //scene.setFill(Color.BLACK);

        primaryStage.setTitle("3D Test");
        primaryStage.setScene(scene);
        primaryStage.show();

        //SubScene subScene = new SubScene(pane,700,600,true,SceneAntialiasing.BALANCED);
        /*Camera camera = new PerspectiveCamera();
        camera.setRotationAxis(new Point3D(10,10,0));
        camera.setTranslateX(175);
        camera.setTranslateY(-400);
        camera.setTranslateZ(-100);
        camera.setRotate(-35);
        subScene.setCamera(camera);
        gridPane.getChildren().add(subScene);*/
    }

    public static void main(String[] args) {
        launch(args);
    }
}