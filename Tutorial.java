package towerbloxx;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        
        Main.root.getChildren().add(this);

        return instruc;
    }
}
