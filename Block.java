package towerbloxx;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    int blockX = 0;
    int blockY = 0;
    int blockWidth = 100;
    int blockHeight = 100;
    int blockSpeed = 5;
    int totalBlock = 0;
    int score = 0;
    int detachPosY = 750;
    boolean isDetach = false;
    boolean isOver = false;
    int count = 0;
    Path path;
    PathTransition pathTransition;
    TranslateTransition translateTransition;
    InputStream block;
    ImageView imgBlock;
    Block newBlock;
    ArrayList<Block> blockList = new ArrayList<>();
    int blockPrev = 0;
    int blockCurrent = 0;
    
    public Block()
    {
        try {
            block = Files.newInputStream(Paths.get("C:/Users/PM/Documents/NetBeansProjects/TowerBloxx/src/towerbloxx/box.png"));
            imgBlock = new ImageView(new Image(block));
            imgBlock.setFitWidth(blockWidth);
            imgBlock.setFitHeight(blockHeight);
            //imgBlock.setTranslateX(blockX);
            //imgBlock.setTranslateY(blockY);
            getChildren().add(imgBlock);
        } catch (IOException e)
        {
            System.out.println("Cannot load box.png");
        }
    }
    
    public void swing()
    { 
        path = new Path();
        MoveTo moveTo = new MoveTo(200, 50);
        CubicCurveTo cubicCurveTo = new CubicCurveTo(200, 50, 250, 300, 824, 50);

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

        pathTransition.setPath(path);
        pathTransition.setCycleCount(20);
        pathTransition.setAutoReverse(true);
        pathTransition.play();  
    }
    public void detach() 
    {
        pathTransition.stop();
        totalBlock++;
        score += 10;
        
        translateTransition = new TranslateTransition();  
        translateTransition.setDuration(Duration.millis(1000));
        if (count == 1)         // first block 
        {
            translateTransition.setNode(imgBlock);
            translateTransition.setToY(750);
            count++;
            blockPrev = 0;
            blockCurrent = 0;
            this.blockX = (int)imgBlock.getTranslateX();
            blockList.add(this);
        }
        else
        {     
            if (count == 2)    // second block
                blockPrev = 0;
            else               // 3 , 4 , 5 , ... block
                blockPrev++;
            count++;
            blockCurrent = blockPrev + 1;
            detachPosY -= 83;
            translateTransition.setNode(newBlock);
            translateTransition.setToY(detachPosY);
            newBlock.blockX = (int)newBlock.getTranslateX();
            blockList.add(newBlock);
        }
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
       
        // check if it can be placed on previous block / fall out of window
        if (blockList.size() > 1)
        {
            if (blockList.get(blockPrev).blockX - blockList.get(blockCurrent).blockX >= 50)
                System.out.println("FALL LEFT");
            if (blockList.get(blockCurrent).blockX - blockList.get(blockPrev).blockX >= 50)
                System.out.println("FALL RIGHT");
        }
        swing();
    }
}
