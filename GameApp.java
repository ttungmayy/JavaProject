package towerbloxx;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class GameApp extends Pane{
    Block block;
    
    public Pane runGameApp()
    {
        Pane game = new Pane();
        game.setPrefSize(Main.width , Main.height);
        
        // background
        try{
        InputStream bg = Files.newInputStream(Paths.get("C:/Users/PM/Documents/NetBeansProjects/TowerBloxx/src/towerbloxx/town.jpg"));
        ImageView imgBG = new ImageView(new Image(bg));
        imgBG.setFitWidth(Main.width);
        imgBG.setFitHeight(Main.height);
        game.getChildren().add(imgBG);
        } catch (IOException e)
        {
            System.out.println("Cannot load town.jpg");
        }
        
        // block to play
        block = new Block();
        game.getChildren().add(block);
        
        // block swings back & forth
        block.move();
        
        // detach block by clicking 
        game.setOnMouseClicked(event -> block.detach());
        
        Main.root.getChildren().add(this);
        return game;
    }
}
