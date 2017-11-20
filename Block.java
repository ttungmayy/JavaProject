package towerbloxx;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Block extends Pane {

    int blockX = 0;
    int blockY = 0;
    int blockWidth = 100;
    int blockHeight = 100;
    int blockSpeed = 5;
    int totalBlock = 0;
    int score;
    Label Showscore = new Label("0");

    Label Text = new Label("Tower");
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
    int scrollY = 860;
    int countnum = 250;
    int countScroll = 0;
    int bgPos = 50;
    //ImageView bg;
    TranslateTransition background;
    boolean check = false;
    InputStream human;
    ImageView imgHuman;

    public Block() {

        try {
            block = Files.newInputStream(Paths.get("/Users/namedojimo/NetBeansProjects/Project/src/towerbloxx/block.png"));
            imgBlock = new ImageView(new Image(block));
            imgBlock.setFitWidth(blockWidth);
            imgBlock.setFitHeight(blockHeight);

        } catch (IOException e) {
            System.out.println("Cannot load block.png");
        }
        getChildren().add(imgBlock);
        // getChildren().add(Showscore);

    }

    public void swing() {
        path = new Path();
        moveTo = new MoveTo(200, 50);
        CubicCurveTo cubicCurveTo = new CubicCurveTo(200, 50, 250, 300, 824, 50);

        path.getElements().add(moveTo);
        path.getElements().add(cubicCurveTo);

        pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(1000));

        if (count == 0) {
            pathTransition.setNode(imgBlock);
            getChildren().add(Showscore);
            getChildren().add(Text);
            Showscore.setText(totalBlock + "");
            Text.setTranslateY(0);
            Text.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 50));
            Showscore.setTranslateY(bgPos);
            totalBlock += 1;
            Showscore.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 100));

            count++;
        } else {
            newBlock = new Block();
            getChildren().add(newBlock);
            pathTransition.setNode(newBlock);
        }

        pathTransition.setPath(path);
        pathTransition.setCycleCount(20);
        pathTransition.setAutoReverse(true);
        pathTransition.play();
    }

    public void detach() {
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
            Showscore.setText(totalBlock + "");

        } else {
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
        if (blockList.size() > 1) {
            if (blockList.get(blockPrev).blockY - blockList.get(blockCurrent).blockY == 100) {
                if ((blockList.get(blockCurrent).blockX + blockWidth) - blockList.get(blockPrev).blockX > 0
                        && ((blockList.get(blockCurrent).blockX + blockWidth) - blockList.get(blockPrev).blockX <= 50)) {
                    System.out.println("Collapse Left");
                    blockPrev--;
                    detachPosY += 100;
                    blockList.remove(newBlock);
                    collapseLeft();
                } else if ((blockList.get(blockPrev).blockX + blockWidth) - blockList.get(blockCurrent).blockX > 0
                        && ((blockList.get(blockPrev).blockX + blockWidth) - blockList.get(blockCurrent).blockX <= 50)) {
                    System.out.println("Collapse Right");
                    blockPrev--;
                    detachPosY += 100;
                    blockList.remove(newBlock);
                    collapseRight();
                } else if ((blockList.get(blockCurrent).blockX + blockWidth) - blockList.get(blockPrev).blockX < 0
                        || ((blockList.get(blockPrev).blockX + blockWidth) - blockList.get(blockCurrent).blockX < 0)) {
                    blockPrev--;
                    detachPosY += 100;
                    blockList.remove(newBlock);
                    falling();
                } else {
                    newBlockList.add(newBlock);
                    totalBlock += 1;
                    Showscore.setText(totalBlock + "");

                    if (newBlockList.size() % 3 == 0) {

                        scrolling();
                        detachPosY += 100;
                        //check = true;
                        if (countScroll == 0) {
                            countScroll++;
                            System.out.println("HEHE");
                            background = new TranslateTransition();
                            background.setDuration(Duration.millis(1000));
                            background.setNode(GameApp.bg);
                            background.setToY(scrollY);
                            background.setCycleCount(1);
                            background.setAutoReverse(true);
                            background.play();

                            //bg.setTranslateY(scrollY);
                        }
                    }
                    check = false;
                    if (newBlockList.size() > 3) {
                        Random rand = new Random();
                        int direction;
                        direction = rand.nextInt(2) + 1;

                        if (direction == 1) {
                            moveTo = new MoveTo(100, 50);
                            CubicCurveTo cubicCurveTo = new CubicCurveTo(250, 100, 350, 370, 450, 600);

                            int character = rand.nextInt(3) + 1;

                            if (character == 1) {

                                try {
                                    human = Files.newInputStream(Paths.get("/Users/namedojimo/NetBeansProjects/Project/src/towerbloxx/oldJetRight.png"));
                                    imgHuman = new ImageView(new Image(human));
                                    imgHuman.setFitWidth(50);
                                    imgHuman.setFitHeight(50);
                                } catch (IOException e) {
                                    System.out.println("Cannot load Human.png");
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
                                    human = Files.newInputStream(Paths.get("/Users/namedojimo/NetBeansProjects/Project/src/towerbloxx/PenguinFartRight.png"));
                                    imgHuman = new ImageView(new Image(human));
                                    imgHuman.setFitWidth(50);
                                    imgHuman.setFitHeight(50);
                                } catch (IOException e) {
                                    System.out.println("Cannot load Human.png");
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
                                    human = Files.newInputStream(Paths.get("/Users/namedojimo/NetBeansProjects/Project/src/towerbloxx/parachute.png"));
                                    imgHuman = new ImageView(new Image(human));
                                    imgHuman.setFitWidth(100);
                                    imgHuman.setFitHeight(100);
                                } catch (IOException e) {
                                    System.out.println("Cannot load Human.png");
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
                            moveTo = new MoveTo(600, 50);
                            CubicCurveTo cubicCurveTo = new CubicCurveTo(550, 100, 400, 370, 350, 350);

                            int character = rand.nextInt(3) + 1;

                            if (character == 1) {

                                try {
                                    human = Files.newInputStream(Paths.get("/Users/namedojimo/NetBeansProjects/Project/src/towerbloxx/parachute.png"));
                                    imgHuman = new ImageView(new Image(human));
                                    imgHuman.setFitWidth(50);
                                    imgHuman.setFitHeight(50);
                                } catch (IOException e) {
                                    System.out.println("Cannot load Human.png");
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
                                    human = Files.newInputStream(Paths.get("/Users/namedojimo/NetBeansProjects/Project/src/towerbloxx/PenguinFartLeft.png"));
                                    imgHuman = new ImageView(new Image(human));
                                    imgHuman.setFitWidth(50);
                                    imgHuman.setFitHeight(50);
                                } catch (IOException e) {
                                    System.out.println("Cannot load Human.png");
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
                                    human = Files.newInputStream(Paths.get("/Users/namedojimo/NetBeansProjects/Project/src/towerbloxx/parachute.png"));
                                    imgHuman = new ImageView(new Image(human));
                                    imgHuman.setFitWidth(100);
                                    imgHuman.setFitHeight(100);
                                } catch (IOException e) {
                                    System.out.println("Cannot load Human.png");
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
                            moveTo = new MoveTo(200, 200);
                            CubicCurveTo cubicCurveTo = new CubicCurveTo(250, 300, 300, 200, 400, 200);

                            int character = rand.nextInt(3) + 1;

                            if (character == 1) {

                                try {
                                    human = Files.newInputStream(Paths.get("/Users/namedojimo/NetBeansProjects/Project/src/towerbloxx/oldJetLeft.png"));
                                    imgHuman = new ImageView(new Image(human));
                                    imgHuman.setFitWidth(50);
                                    imgHuman.setFitHeight(50);
                                } catch (IOException e) {
                                    System.out.println("Cannot load Human.png");
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
                                    human = Files.newInputStream(Paths.get("/Users/namedojimo/NetBeansProjects/Project/src/towerbloxx/PenguinFartLeft.png"));
                                    imgHuman = new ImageView(new Image(human));
                                    imgHuman.setFitWidth(50);
                                    imgHuman.setFitHeight(50);
                                } catch (IOException e) {
                                    System.out.println("Cannot load Human.png");
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
                                    human = Files.newInputStream(Paths.get("/Users/namedojimo/NetBeansProjects/Project/src/towerbloxx/parachute.png"));
                                    imgHuman = new ImageView(new Image(human));
                                    imgHuman.setFitWidth(100);
                                    imgHuman.setFitHeight(100);
                                } catch (IOException e) {
                                    System.out.println("Cannot load Human.png");
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
        swing();
        //return check;
    }
    
    public void collapseLeft() {
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

    public void collapseRight() {
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

    public void falling() {
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

    public void scrolling() {
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

        check = true;

    }

}
