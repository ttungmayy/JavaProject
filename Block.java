package towerbloxx;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    int blockX = 100;
    int blockY = 100;
    int blockWidth = 100;
    int blockHeight = 100;
    int totalBlock = 0;
    int score = 0;
    int swingCount = 5;
    int detachPosY = 650;
    boolean gameOver = false;
    TranslateTransition translateTransition = new TranslateTransition();
    PathTransition pathTransition = new PathTransition();
    ImageView imgBlock;
    
    public Block()
    {
        try {
            InputStream block = Files.newInputStream(Paths.get("C:/Users/PM/Documents/NetBeansProjects/TowerBloxx/src/towerbloxx/box.png"));
            imgBlock = new ImageView(new Image(block));
            imgBlock.setFitWidth(blockWidth);
            imgBlock.setFitHeight(blockHeight);
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
        Path path = new Path();
        MoveTo moveTo = new MoveTo(100, 0);
        CubicCurveTo cubicCurveTo = new CubicCurveTo(100, 0, 200, 250, 700, 0);
        
        path.getElements().add(moveTo);
        path.getElements().add(cubicCurveTo);

        pathTransition.setDuration(Duration.millis(2000)); 
        pathTransition.setNode(this);
        pathTransition.setPath(path);
        pathTransition.setCycleCount(swingCount);
        pathTransition.setAutoReverse(true);
        pathTransition.play();
    }
    
    public void detach()
    {
        pathTransition.stop();
        totalBlock++;
        score += 10;
        
        translateTransition.setDuration(Duration.millis(1000));
        translateTransition.setNode(imgBlock);
        translateTransition.setToY(detachPosY);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
        
        detachPosY -= blockHeight;
        makeNew();
    }
    
    public void makeNew()
    {
        blockX = 100;
        blockY = 100;
        Block newBlock = new Block();
        getChildren().add(newBlock);
        newBlock.swing();
    }
    
    public boolean isGameOver()
    {
        return gameOver;
    }
}
