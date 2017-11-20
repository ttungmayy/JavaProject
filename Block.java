package towerbloxx;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class Block extends Pane {

    int blockX = 0;
    int blockY = 0;
    int blockWidth = 100;
    int blockHeight = 100;
    int blockSpeed = 5;
    int totalBlock = 0;
    int life = 3;
    Label score = new Label("");
    Label lifePoint = new Label("");
    int detachPosY = 750;
    boolean isDetach = false;
    boolean isOver = false;
    int count = 0;
    Path path;
    MoveTo moveTo;
    PathTransition pathTransition;
    TranslateTransition translateTransition;
    RotateTransition rt;
    FadeTransition ft;
    InputStream block;
    ImageView imgBlock;
    Block newBlock;
    ArrayList<Block> blockList = new ArrayList<>();
    ArrayList<Block> newBlockList = new ArrayList<>();
    int blockPrev = 0;
    int blockCurrent = 0;
    int scrollY = 215;
    int countnum = 250;
    int countScroll = 0;
    int bgPos = 50;
    TranslateTransition background;
    InputStream human;
    ImageView imgHuman;
    String mode = "default";

    public Block() 
    {
        try {
            block = Files.newInputStream(Paths.get("/Users/PM/Documents/NetBeansProjects/TowerBloxx/src/towerbloxx/block.png"));
            imgBlock = new ImageView(new Image(block));
            imgBlock.setFitWidth(blockWidth);
            imgBlock.setFitHeight(blockHeight);

        } catch (IOException e) {
            System.out.println("Cannot load block.png");
        }
        
        getChildren().add(imgBlock);
    }
    
    public Block(int blockNumber) 
    {

        try {
            block = Files.newInputStream(Paths.get("/Users/PM/Documents/NetBeansProjects/TowerBloxx/src/towerbloxx/block.png"));
            imgBlock = new ImageView(new Image(block));
            imgBlock.setFitWidth(blockWidth);
            imgBlock.setFitHeight(blockHeight);

        } catch (IOException e) {
            System.out.println("Cannot load block.png");
        }
        
        getChildren().add(imgBlock);
        mode = "story";
        totalBlock = blockNumber;
    }

    public void swing() 
    {
        path = new Path();
        moveTo = new MoveTo(200, 50);
        CubicCurveTo cubicCurveTo = new CubicCurveTo(200, 50, 250, 300, 824, 50);

        path.getElements().add(moveTo);
        path.getElements().add(cubicCurveTo);

        pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(1000));

        if (count == 0) {
            count++;
            pathTransition.setNode(imgBlock);
 
            score.setText("Total : " + totalBlock);
            score.setTranslateX(50);
            score.setTranslateY(30);
            score.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 60));
            
            lifePoint.setText("Life : " + life);
            lifePoint.setTranslateX(850);
            lifePoint.setTranslateY(30);
            lifePoint.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 60));
            
            getChildren().addAll(score , lifePoint); 
        }
        else 
        {
            if (mode.equals("story"))
                newBlock = new Block(totalBlock - 1);
            else
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
        translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(1000));
        
        if (count == 1) // first block 
        {
            translateTransition.setNode(imgBlock);
            translateTransition.setToY(750);
            count++;
            blockPrev = 0;
            blockCurrent = 0;
            this.blockX = (int) imgBlock.getTranslateX();
            this.blockY = detachPosY;
            blockList.add(this);
            if (mode.equals(("story")))
                totalBlock--;
            else
                totalBlock++;
            
            score.setText("Total : " + totalBlock);
        } 
        else 
        {
            if (count == 2) // second block
            {
                blockPrev = 0;
            } else // 3 , 4 , 5 , ... block
            {
                blockPrev++;
            }
            count++;
            blockCurrent = blockPrev + 1;
            detachPosY -= 100;
            translateTransition.setNode(newBlock);
            translateTransition.setToY(detachPosY);
            newBlock.blockX = (int) newBlock.getTranslateX();
            newBlock.blockY = detachPosY;
            blockList.add(newBlock);

        }
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(true);
        translateTransition.play();

        // check collapse or fall away
        if (blockList.size() > 1)
        {
            if (blockList.get(blockPrev).blockY - blockList.get(blockCurrent).blockY == 100) {
                if ((blockList.get(blockCurrent).blockX + blockWidth) - blockList.get(blockPrev).blockX > 0
                        && ((blockList.get(blockCurrent).blockX + blockWidth) - blockList.get(blockPrev).blockX <= 50)) 
                {
                    System.out.println("Collapse Left");
                    blockPrev--;
                    detachPosY += 100;
                    blockList.remove(newBlock);
                    collapseLeft();
                    life--;
                    lifePoint.setText("Life : " + life);
                } 
                else if ((blockList.get(blockPrev).blockX + blockWidth) - blockList.get(blockCurrent).blockX > 0
                        && ((blockList.get(blockPrev).blockX + blockWidth) - blockList.get(blockCurrent).blockX <= 50)) 
                {
                    System.out.println("Collapse Right");
                    blockPrev--;
                    detachPosY += 100;
                    blockList.remove(newBlock);
                    collapseRight();
                    life--;
                    lifePoint.setText("Life : " + life);
                } 
                else if ((blockList.get(blockCurrent).blockX + blockWidth) - blockList.get(blockPrev).blockX < 0
                        || ((blockList.get(blockPrev).blockX + blockWidth) - blockList.get(blockCurrent).blockX < 0)) 
                {
                    blockPrev--;
                    detachPosY += 100;
                    blockList.remove(newBlock);
                    falling();
                    life--;
                    lifePoint.setText("Life : " + life);
                } 
                else 
                {
                    newBlockList.add(newBlock);
                    if (mode.equals("story"))
                        totalBlock--;
                    else
                        totalBlock++;
                    
                     score.setText("Total : " + totalBlock);

                    if (newBlockList.size() % 3 == 0) 
                    {
                        scrolling();
                        detachPosY += 100;
                        if (countScroll <= 3) 
                        {
                            countScroll++;
                            background = new TranslateTransition();
                            background.setDuration(Duration.millis(1000));
                            if (mode.equals("story"))
                                background.setNode(StoryMode.bg);
                            else
                                background.setNode(GameApp.bg);
                            background.setToY(scrollY);
                            background.setCycleCount(1);
                            background.setAutoReverse(true);
                            background.play();
                        }
                        scrollY += 215;
                    }

                    if (newBlockList.size() > 3) 
                    {
                        Random rand = new Random();
                        int direction;
                        direction = rand.nextInt(2) + 1;

                        if (direction == 1) 
                        {
                            int start = this.blockX - 350;
                            moveTo = new MoveTo(start, 50);
                            CubicCurveTo cubicCurveTo = new CubicCurveTo(start + 150, 100, start + 250, 370, newBlock.blockX, newBlock.blockY + 180);

                            int character = rand.nextInt(3) + 1;

                            if (character == 1) {

                                try {
                                    human = Files.newInputStream(Paths.get("/Users/PM/Documents/NetBeansProjects/TowerBloxx/src/towerbloxx/oldJetRight.png"));
                                    imgHuman = new ImageView(new Image(human));
                                    imgHuman.setFitWidth(50);
                                    imgHuman.setFitHeight(50);
                                } catch (IOException e) {
                                    System.out.println("Cannot load oldJetRight.png");
                                }
                                getChildren().add(imgHuman);
                                path = new Path();
                                path.getElements().add(moveTo);
                                path.getElements().add(cubicCurveTo);

                                pathTransition = new PathTransition();
                                pathTransition.setDuration(Duration.millis(2000));
                                pathTransition.setNode(imgHuman);

                                pathTransition.setPath(path);
                                pathTransition.setCycleCount(1);
                                pathTransition.setAutoReverse(false);
                                pathTransition.play();
                                ft = new FadeTransition(Duration.millis(2000), imgHuman);
                                ft.setFromValue(1.0);
                                ft.setToValue(-1.0);
                                ft.setDelay(Duration.millis(1000));
                                ft.play();
                                System.out.println("human move");

                            }
                            if (character == 2) {
                                try {
                                    human = Files.newInputStream(Paths.get("/Users/PM/Documents/NetBeansProjects/TowerBloxx/src/towerbloxx/PenguinFartRight.png"));
                                    imgHuman = new ImageView(new Image(human));
                                    imgHuman.setFitWidth(50);
                                    imgHuman.setFitHeight(50);
                                } catch (IOException e) {
                                    System.out.println("Cannot load PenguinFartLeft.png");
                                }
                                getChildren().add(imgHuman);
                                path = new Path();
                                path.getElements().add(moveTo);
                                path.getElements().add(cubicCurveTo);

                                pathTransition = new PathTransition();
                                pathTransition.setDuration(Duration.millis(2000));
                                pathTransition.setNode(imgHuman);

                                pathTransition.setPath(path);
                                pathTransition.setCycleCount(1);
                                pathTransition.setAutoReverse(false);
                                pathTransition.play();
                                ft = new FadeTransition(Duration.millis(2000), imgHuman);
                                ft.setFromValue(1.0);
                                ft.setToValue(-1.0);
                                ft.setDelay(Duration.millis(1000));
                                ft.play();
                            }
                            if (character == 3) {
                                try {
                                    human = Files.newInputStream(Paths.get("/Users/PM/Documents/NetBeansProjects/TowerBloxx/src/towerbloxx/parachute.png"));
                                    imgHuman = new ImageView(new Image(human));
                                    imgHuman.setFitWidth(100);
                                    imgHuman.setFitHeight(100);
                                } catch (IOException e) {
                                    System.out.println("Cannot load parachute.png");
                                }
                                getChildren().add(imgHuman);
                                path = new Path();
                                path.getElements().add(moveTo);
                                path.getElements().add(cubicCurveTo);

                                pathTransition = new PathTransition();
                                pathTransition.setDuration(Duration.millis(2000));
                                pathTransition.setNode(imgHuman);

                                pathTransition.setPath(path);
                                pathTransition.setCycleCount(1);
                                pathTransition.setAutoReverse(false);
                                pathTransition.play();
                                ft = new FadeTransition(Duration.millis(2000), imgHuman);
                                ft.setFromValue(1.0);
                                ft.setToValue(-1.0);
                                ft.setDelay(Duration.millis(1000));
                                ft.play();
                            }
                        }
                        if (direction == 2) {
                int start = this.blockX + 250;
                
                moveTo = new MoveTo(start, 50);
                CubicCurveTo cubicCurveTo = new CubicCurveTo(start - 50, 100, start - 200, 370, newBlock.blockX , newBlock.blockY + 180);

                            int character = rand.nextInt(3) + 1;

                            if (character == 1) {

                                try {
                                    human = Files.newInputStream(Paths.get("/Users/PM/Documents/NetBeansProjects/TowerBloxx/src/towerbloxx/parachute.png"));
                                    imgHuman = new ImageView(new Image(human));
                                    imgHuman.setFitWidth(50);
                                    imgHuman.setFitHeight(50);
                                } catch (IOException e) {
                                    System.out.println("Cannot load parachute.png");
                                }
                                getChildren().add(imgHuman);
                                path = new Path();
                                path.getElements().add(moveTo);
                                path.getElements().add(cubicCurveTo);

                                pathTransition = new PathTransition();
                                pathTransition.setDuration(Duration.millis(2000));
                                pathTransition.setNode(imgHuman);

                                pathTransition.setPath(path);
                                pathTransition.setCycleCount(1);
                                pathTransition.setAutoReverse(false);
                                pathTransition.play();
                                ft = new FadeTransition(Duration.millis(2000), imgHuman);
                                ft.setFromValue(1.0);
                                ft.setToValue(-1.0);
                                ft.setDelay(Duration.millis(1000));
                                ft.play();
                                System.out.println("human move");

                            }
                            if (character == 2) {
                                try {
                                    human = Files.newInputStream(Paths.get("/Users/PM/Documents/NetBeansProjects/TowerBloxx/src/towerbloxx/PenguinFartLeft.png"));
                                    imgHuman = new ImageView(new Image(human));
                                    imgHuman.setFitWidth(50);
                                    imgHuman.setFitHeight(50);
                                } catch (IOException e) {
                                    System.out.println("Cannot load PenguinFartLeft.png");
                                }
                                getChildren().add(imgHuman);
                                path = new Path();
                                path.getElements().add(moveTo);
                                path.getElements().add(cubicCurveTo);

                                pathTransition = new PathTransition();
                                pathTransition.setDuration(Duration.millis(2000));
                                pathTransition.setNode(imgHuman);

                                pathTransition.setPath(path);
                                pathTransition.setCycleCount(1);
                                pathTransition.setAutoReverse(false);
                                pathTransition.play();
                                ft = new FadeTransition(Duration.millis(2000), imgHuman);
                                ft.setFromValue(1.0);
                                ft.setToValue(-1.0);
                                ft.setDelay(Duration.millis(1000));
                                ft.play();
                            }
                            if (character == 3) {
                                try {
                                    human = Files.newInputStream(Paths.get("/Users/PM/Documents/NetBeansProjects/TowerBloxx/src/towerbloxx/parachute.png"));
                                    imgHuman = new ImageView(new Image(human));
                                    imgHuman.setFitWidth(100);
                                    imgHuman.setFitHeight(100);
                                } catch (IOException e) {
                                    System.out.println("Cannot load parachute.png");
                                }
                                getChildren().add(imgHuman);
                                path = new Path();
                                path.getElements().add(moveTo);
                                path.getElements().add(cubicCurveTo);

                                pathTransition = new PathTransition();
                                pathTransition.setDuration(Duration.millis(2000));
                                pathTransition.setNode(imgHuman);

                                pathTransition.setPath(path);
                                pathTransition.setCycleCount(1);
                                pathTransition.setAutoReverse(false);
                                pathTransition.play();
                                ft = new FadeTransition(Duration.millis(2000), imgHuman);
                                ft.setFromValue(1.0);
                                ft.setToValue(-1.0);
                                ft.setDelay(Duration.millis(1000));
                                ft.play();
                            }
                        }
                        if (direction == 3) {
                int start = blockX - 400;
                moveTo = new MoveTo(start, newBlock.blockY);
                CubicCurveTo cubicCurveTo = new CubicCurveTo(start + 150, 300, start + 300, 200, newBlock.blockX, newBlock.blockY + 180);

                            int character = rand.nextInt(2) + 1;

                            if (character == 1) {

                                try {
                                    human = Files.newInputStream(Paths.get("/Users/PM/Documents/NetBeansProjects/TowerBloxx/src/towerbloxx/oldJetLeft.png"));
                                    imgHuman = new ImageView(new Image(human));
                                    imgHuman.setFitWidth(50);
                                    imgHuman.setFitHeight(50);
                                } catch (IOException e) {
                                    System.out.println("Cannot load oldJetLeft.png");
                                }
                                getChildren().add(imgHuman);
                                path = new Path();
                                path.getElements().add(moveTo);
                                path.getElements().add(cubicCurveTo);

                                pathTransition = new PathTransition();
                                pathTransition.setDuration(Duration.millis(2000));
                                pathTransition.setNode(imgHuman);

                                pathTransition.setPath(path);
                                pathTransition.setCycleCount(1);
                                pathTransition.setAutoReverse(false);
                                pathTransition.play();
                                ft = new FadeTransition(Duration.millis(2000), imgHuman);
                                ft.setFromValue(1.0);
                                ft.setToValue(-1.0);
                                ft.setDelay(Duration.millis(1000));
                                ft.play();
                                System.out.println("human move");

                            }
                            if (character == 2) {
                                try {
                                    human = Files.newInputStream(Paths.get("/Users/PM/Documents/NetBeansProjects/TowerBloxx/src/towerbloxx/PenguinFartLeft.png"));
                                    imgHuman = new ImageView(new Image(human));
                                    imgHuman.setFitWidth(50);
                                    imgHuman.setFitHeight(50);
                                } catch (IOException e) {
                                    System.out.println("Cannot load PenguinFartLeft.png");
                                }
                                getChildren().add(imgHuman);
                                path = new Path();
                                path.getElements().add(moveTo);
                                path.getElements().add(cubicCurveTo);

                                pathTransition = new PathTransition();
                                pathTransition.setDuration(Duration.millis(2000));
                                pathTransition.setNode(imgHuman);

                                pathTransition.setPath(path);
                                pathTransition.setCycleCount(1);
                                pathTransition.setAutoReverse(false);
                                pathTransition.play();
                                ft = new FadeTransition(Duration.millis(2000), imgHuman);
                                ft.setFromValue(1.0);
                                ft.setToValue(-1.0);
                                ft.setDelay(Duration.millis(1000));
                                ft.play();
                            }
                            if (character == 3) {
                                try {
                                    human = Files.newInputStream(Paths.get("/Users/PM/Documents/NetBeansProjects/TowerBloxx/src/towerbloxx/parachute.png"));
                                    imgHuman = new ImageView(new Image(human));
                                    imgHuman.setFitWidth(100);
                                    imgHuman.setFitHeight(100);
                                } catch (IOException e) {
                                    System.out.println("Cannot load parachute.png");
                                }
                                getChildren().add(imgHuman);
                                path = new Path();
                                path.getElements().add(moveTo);
                                path.getElements().add(cubicCurveTo);

                                pathTransition = new PathTransition();
                                pathTransition.setDuration(Duration.millis(2000));
                                pathTransition.setNode(imgHuman);

                                pathTransition.setPath(path);
                                pathTransition.setCycleCount(1);
                                pathTransition.setAutoReverse(false);
                                pathTransition.play();
                                ft = new FadeTransition(Duration.millis(2000), imgHuman);
                                ft.setFromValue(1.0);
                                ft.setToValue(-1.0);
                                ft.setDelay(Duration.millis(1000));
                                ft.play();
                            }
                        }
                    }
                }
            }
        }
        if (life == 0)
        {
            isOver = true;
            Pane over = new GameOver().runGameOver();
            Main.gameover = new Scene(over);
            Main.primaryStage.setScene(Main.gameover);
            Main.primaryStage.show();
        }
        
        swing();
    }
    
    public void collapseLeft() 
    {
        int xPos = newBlock.blockX;
        int yPos = newBlock.blockY;

        path = new Path();
        moveTo = new MoveTo(xPos + 50, yPos + 83);

        CubicCurveTo cubicCurveTo = new CubicCurveTo(xPos + 50, yPos + 83,
                xPos - 25, yPos + 166,
                xPos - 50, yPos + 249);

        path.getElements().add(moveTo);
        path.getElements().add(cubicCurveTo);

        pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(2000));
        pathTransition.setNode(newBlock);
        pathTransition.setPath(path);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);
        pathTransition.setDelay(Duration.millis(1000));
        pathTransition.play();

        rt = new RotateTransition(Duration.millis(2000), newBlock);
        rt.setByAngle(-400);
        rt.setCycleCount(1);
        rt.setAutoReverse(false);
        rt.setDelay(Duration.millis(1000));
        rt.play();

        ft = new FadeTransition(Duration.millis(2000), newBlock);
        ft.setFromValue(1.0);
        ft.setToValue(-1.0);
        ft.setDelay(Duration.millis(1000));
        ft.play();
    }

    public void collapseRight() 
    {        
        int xPos = newBlock.blockX;
        int yPos = newBlock.blockY;

        path = new Path();
        moveTo = new MoveTo(xPos + 50, yPos + 83);

        CubicCurveTo cubicCurveTo = new CubicCurveTo(xPos + 50, yPos + 83,
                xPos + 25, yPos + 166,
                xPos + 50, yPos + 249);

        path.getElements().add(moveTo);
        path.getElements().add(cubicCurveTo);

        pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(2000));
        pathTransition.setNode(newBlock);
        pathTransition.setPath(path);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);
        pathTransition.setDelay(Duration.millis(1000));
        pathTransition.play();

        rt = new RotateTransition(Duration.millis(3000), newBlock);
        rt.setByAngle(400);
        rt.setCycleCount(1);
        rt.setAutoReverse(false);
        rt.setDelay(Duration.millis(1000));
        rt.play();

        ft = new FadeTransition(Duration.millis(3000), newBlock);
        ft.setFromValue(1.0);
        ft.setToValue(-1.0);
        ft.setDelay(Duration.millis(1000));
        ft.play();
    }

    public void falling() 
    {   
        translateTransition = new TranslateTransition();
        translateTransition.setNode(newBlock);
        translateTransition.setDuration(Duration.millis(1000));
        translateTransition.setToY(700);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
        translateTransition.play();

        rt = new RotateTransition(Duration.millis(1000), newBlock);
        rt.setByAngle(500);
        rt.setCycleCount(1);
        rt.setAutoReverse(false);
        rt.play();

        ft = new FadeTransition(Duration.millis(2000), newBlock);
        ft.setFromValue(1.0);
        ft.setToValue(-1.0);
        ft.play();
    }

    public void scrolling() 
    {
        translateTransition = new TranslateTransition();
        translateTransition.setNode(imgBlock);
        translateTransition.setDuration(Duration.millis(1000));
        translateTransition.setToY(860);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
        translateTransition.play();

        getChildren().remove(imgBlock);
        blockList.remove(imgBlock);

        for (int i = 0; i < newBlockList.size(); i++) {
            translateTransition = new TranslateTransition();
            translateTransition.setNode(newBlockList.get(i));
            translateTransition.setDuration(Duration.millis(1000));
            newBlockList.get(i).blockY += countnum;
            translateTransition.setToY(newBlockList.get(i).blockY);
            translateTransition.setCycleCount(1);
            translateTransition.setAutoReverse(false);
            translateTransition.play();
        }

        for (int i = 0; i < newBlockList.size(); i++) {
            if (newBlockList.get(i).blockY > 900) {
                getChildren().remove(newBlockList.get(i));
                newBlockList.remove(i);
                i--;
            }
        }

        detachPosY += 150;
    }
}
