package towerbloxx;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class Block extends Pane{
    int blockX = 50;
    int blockY = 50;
    int blockWidth = 100;
    int blockHeight = 100;
    int blockSpeed = 5;
    int totalBlock = 0;
    int score = 0;
    boolean isDetach = false;
    boolean isOver = false;
    int count = 0;
    int angle = 0;
    int rotation = 0;
    int radius = 1;
    Path path;
    PathTransition pathTransition;
    AnimationTimer blockMovement;
    InputStream block;
    ImageView imgBlock;
    Block newBlock;
    
    public Block()
    {
        try {
        block = Files.newInputStream(Paths.get("/Users/namedojimo/NetBeansProjects/Project/src/towerbloxx/box.png"));
        imgBlock = new ImageView(new Image(block));
        imgBlock.setFitWidth(blockWidth);
        imgBlock.setFitHeight(blockWidth);
        imgBlock.setTranslateX(blockX);
        imgBlock.setTranslateY(blockY);
        
        getChildren().add(imgBlock);
        } catch (IOException e)
        {
            System.out.println("Cannot load box.png");
        }
    }
    
    public void swing()
    { 
        path = new Path();
        MoveTo moveTo = new MoveTo(0, 0);
        CubicCurveTo cubicCurveTo = new CubicCurveTo(0, 0, 250, 250, 500, 0);

        path.getElements().add(moveTo);
        path.getElements().add(cubicCurveTo);

        pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(1000));
         if (count == 0)
        {
            pathTransition.setNode(imgBlock);
            count++;
        }
        else
        {      
            newBlock = new Block();
            getChildren().add(newBlock); 
            pathTransition.setNode(newBlock);
        }
        //pathTransition.setNode(imgBlock);
        pathTransition.setPath(path);
        pathTransition.setCycleCount(100);
     
        pathTransition.setAutoReverse(true);
        pathTransition.play();
        
        
    }
// 
    public void detach() 
    {
        //isDetach = true;
        pathTransition.stop();
        totalBlock++;
        score += 10;
        blockY = (int) getTranslateY();
        blockX = (int) getTranslateX();
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(1000));
        if (count == 1)
        {
            translateTransition.setNode(imgBlock);
            count++;
        }
        else
        {      
            translateTransition.setNode(newBlock);
        }
        
        translateTransition.setToY(500);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
        
        //swing();
        swing();
        //pathTransition.setDelay(Duration.millis(10000));
        
    }
      
    public Block makeNew()
    {    
        newBlock = new Block();
        newBlock.blockX = 100;
        newBlock.blockY = 100;
        getChildren().add(newBlock); 
      
        //swing(newBlock);
        return newBlock;
    }
}
