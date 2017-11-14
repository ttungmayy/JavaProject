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
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.application.Application;
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
public class CollapseRight extends Application{
    
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
        
        } catch (IOException e)
        {
            System.out.println("Cannot load box.png");
        }
        
        Path path = new Path();

        //Creating the MoveTo path element 
        MoveTo moveTo = new MoveTo(200, 200);

        //Creating the Cubic curve path element 
        //CubicCurveTo cubicCurveTo = new CubicCurveTo();
        CubicCurveTo cubicCurveTo = new CubicCurveTo(300, 250, 350, 300, 400, 350);
        

        //Adding the path elements to Observable list of the Path class 
        path.getElements().add(moveTo);
        path.getElements().add(cubicCurveTo);

        //Creating a path transition 
        PathTransition pathTransition = new PathTransition();

        //Setting the duration of the path transition 
        pathTransition.setDuration(Duration.millis(2000));

        //Setting the node for the transition 
        pathTransition.setNode(imgBlock);

        //Setting the path 
        pathTransition.setPath(path);

        //Setting the orientation of the path 
        //pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TAN GENT); 
        //Setting the cycle count for the transition 
        pathTransition.setCycleCount(50);

        //Setting auto reverse value to false 
        pathTransition.setAutoReverse(false);

        //Playing the animation 
        pathTransition.play();
        
        RotateTransition rt = new RotateTransition(Duration.millis(1000), imgBlock);
     rt.setByAngle(210);
     rt.setCycleCount(10);
     rt.setAutoReverse(true);
     rt.play();
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
        
        
    

