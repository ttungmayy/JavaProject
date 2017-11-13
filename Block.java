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
    
    int angle = 0;
    int rotation = 0;
    int radius = 1;
    Path path;
    PathTransition pathTransition;
    AnimationTimer blockMovement;
    
    public Block()
    {
        try {
        InputStream block = Files.newInputStream(Paths.get("/Users/namedojimo/NetBeansProjects/Project/src/towerbloxx/box.png"));
        ImageView imgBlock = new ImageView(new Image(block));
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
        pathTransition.setDuration(Duration.millis(2000)); 
        pathTransition.setNode(this);
        pathTransition.setPath(path);
        pathTransition.setCycleCount(100);
     
        pathTransition.setAutoReverse(true);
        pathTransition.play();
    }
//    public void move()
//    {      
//        blockMovement = new AnimationTimer()
//        { 
//            public void handle(long arg0)
//            {
//                if (angle < 50)
//                {
//                    angle++;
//                    blockX += blockSpeed + Math.sin(Math.toRadians(angle)) * radius;
//                    blockY += blockSpeed + Math.cos(Math.toRadians(angle)) * radius;
//                }
//                
//                else
//                {
//                    angle++;
//                    blockX += blockSpeed + Math.cos(Math.toRadians(angle)) * radius;
//                    blockY -= blockSpeed + Math.sin(Math.toRadians(angle)) * radius;
//                }
//                
//                setTranslateX(blockX);
//                setTranslateY(blockY);
//            }
//        };
//        blockMovement.start();
//    }  
    public void detach() throws InterruptedException
    {
        isDetach = true;
        pathTransition.pause();
        totalBlock++;
        score += 10;
        blockY = (int) getTranslateY();
        blockX = (int) getTranslateX();
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(2000));
        translateTransition.setNode(this);
        translateTransition.setToY(500);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
        //pathTransition.setDelay(Duration.millis(3000));
        
        swing();
    }
    
    public Block makeNew()
    {
        Block newBlock = new Block();
        newBlock.blockX = 0;
        isDetach = false;
        return newBlock;
    }
}
