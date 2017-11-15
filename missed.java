/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecttest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author teerat
 */
public class missed extends Application {

    ImageView imgBlock;

    @Override
    public void start(Stage stage) throws Exception {
        try {
            InputStream block = Files.newInputStream(Paths.get("/Users/teerat/Documents/Java netbeans/JavaFXApplication3/src/javafxapplication3/box.png"));
            imgBlock = new ImageView(new Image(block));
            imgBlock.setFitWidth(100);
            imgBlock.setFitHeight(100);
            imgBlock.setTranslateX(0);
            imgBlock.setTranslateY(0);

        } catch (IOException e) {
            System.out.println("Cannot load box.png");
        }

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(imgBlock);
        translateTransition.setDuration(Duration.millis(2000));
        translateTransition.setToY(400);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false); 
        translateTransition.play();

        RotateTransition rt = new RotateTransition(Duration.millis(2000), imgBlock);
        rt.setByAngle(500);
        rt.setCycleCount(1);
        rt.setAutoReverse(false);
        rt.play();

        FadeTransition ft = new FadeTransition(Duration.millis(3000), imgBlock);
        ft.setFromValue(1.0);
        ft.setToValue(-1.0);
        ft.play();

        //Creating a Group object  
        Group root = new Group(imgBlock);

        //Creating a scene object 
        Scene scene = new Scene(root, 900, 700);

        //Setting title to the Stage 
        stage.setTitle("Path transition example");

        //Adding scene to the stage 
        stage.setScene(scene);

        //Displaying the contents of the stage 
        stage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }

}
