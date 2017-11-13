package towerbloxx;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Tutorial extends Pane{
    public Pane runTutorial()
    {
        Pane instruc = new Pane();
        instruc.setPrefSize(Main.width, Main.height);
        try{
            InputStream wall = Files.newInputStream(Paths.get("C:/Users/PM/Documents/NetBeansProjects/TowerBloxx/src/towerbloxx/wallpaper.jpg"));
            ImageView imgWall = new ImageView(new Image(wall));
            imgWall.setFitWidth(Main.width);
            imgWall.setFitHeight(Main.height);
                
            instruc.getChildren().add(imgWall);
        }
        catch (IOException e)
        {
            System.out.println("Cannot load wallpaper.jpg");
        }
        
        Main.MenuItem itemStart = new Main.MenuItem("I WANNA PLAY THIS !!");
        itemStart.setOnMouseClicked(event -> {
                                        Pane gameApp = new GameApp().runGameApp();
                                        Main.game = new Scene(gameApp);
                                        Main.primaryStage.setScene(Main.game);
                                        Main.primaryStage.show();});
        Main.MenuItem itemExit = new Main.MenuItem("LET ME OUT OF HERE");
        itemExit.setOnMouseClicked(event -> System.exit(0));
        
        Main.MenuBox tutorialBox = new Main.MenuBox(itemStart , itemExit);
        tutorialBox.setTranslateX(Main.width/2 - 150);
        tutorialBox.setTranslateY(Main.height - 450);
        
        instruc.getChildren().add(tutorialBox);
        Main.root.getChildren().add(this);

        return instruc;
    }
}
