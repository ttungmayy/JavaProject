package towerbloxx;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class Main extends Application{
    final static int width = 1024;
    final static int height = 860;
    public static Pane root;
    static Stage primaryStage;
    static Scene menu , tutorial , game , endlessScene , gameover;
    ImageView img;
    
    private Parent CreateContent()
    {
        root = new Pane();
        root.setPrefSize(width,height);
        
        try{
            InputStream mainBg = Files.newInputStream(Paths.get("C:/Users/PM/Documents/NetBeansProjects/TowerBloxx/src/towerbloxx/main.png"));
            img = new ImageView(new Image(mainBg));
            img.setFitWidth(width);
            img.setFitHeight(height);  
            root.getChildren().add(img);
        }
        catch (IOException e)
        {
            System.out.println("Cannot load main.png");
        }
        
        img.setOnMouseClicked(event -> {
                                        Pane menuPane = new Menu().runMenu();
                                        menu = new Scene(menuPane);
                                        primaryStage.setScene(menu);
                                        primaryStage.show();
                                });
        
        Text next = new Text("Click anywhere to continue . . .");
        next.setFont(Font.font("Tw Cen MT Condensed" , FontWeight.SEMI_BOLD, 65));
        next.setTranslateX(220);
        next.setTranslateY(830);
        
        root.getChildren().add(next);
        return root;
    }
    
    public void start(Stage stage)
    {
        primaryStage = stage;
        Scene scene = new Scene(CreateContent());
        primaryStage.setTitle("Tower Bloxx");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }
}
