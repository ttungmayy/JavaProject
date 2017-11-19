package towerbloxx;

import java.io.File;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class GameApp extends Pane{
    Block block;
    ImageView bg;
    
    public Pane runGameApp()
    {
        Pane game = new Pane();
        game.setPrefSize(Main.width , Main.height);

        Media sound = new Media(new File("C:/Users/PM/Documents/NetBeansProjects/TowerBloxx/src/towerbloxx/happy.wav").toURI().toString());
        MediaPlayer soundplay = new MediaPlayer(sound);
        soundplay.setAutoPlay(true);
        MediaView mediaview = new MediaView(soundplay);
        game.getChildren().add(mediaview);
        
       
        // background
        bg = new Background().getBackground();
        game.getChildren().add(bg);
        
        // block to play
        block = new Block();
        game.getChildren().add(block);
        
        // block swings back & forth
        block.swing();
        
        // detach block by clicking 
        game.setOnMouseClicked(event -> block.detach());

        Main.root.getChildren().add(this);
        return game;
    }
}
