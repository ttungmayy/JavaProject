package towerbloxx;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class Main extends Application{
    final static int width = 1024;
    final static int height = 860;
    public static Pane root;
    static Stage primaryStage;
    static Scene tutorial , game;
    
    private Parent CreateContent()
    {
        root = new Pane();
        root.setPrefSize(width,height);
        
        try{
            InputStream is = Files.newInputStream(Paths.get("C:/Users/PM/Documents/NetBeansProjects/TowerBloxx/src/towerbloxx/main.jpg"));
            ImageView img = new ImageView(new Image(is));
            img.setFitWidth(width);
            img.setFitHeight(height);
                
            root.getChildren().add(img);
        }
        catch (IOException e)
        {
            System.out.println("Cannot load image");
        }
        
        MenuItem itemPlay = new MenuItem("START");
        itemPlay.setOnMouseClicked(event -> {
                                        Pane gameApp = new GameApp().runGameApp();
                                        game = new Scene(gameApp);
                                        primaryStage.setScene(game);
                                        primaryStage.show();
        });
        
        MenuItem itemTutorial = new MenuItem("TUTORIAL");
        itemTutorial.setOnMouseClicked(event -> {
                                        Pane intruc = new Tutorial().runTutorial();
                                        tutorial = new Scene(intruc);
                                        primaryStage.setScene(tutorial);
                                        primaryStage.show();
        });
        
        MenuItem itemQuit = new MenuItem("QUIT");
        itemQuit.setOnMouseClicked(event -> System.exit(0));
        
        MenuBox menu = new MenuBox(itemPlay , itemTutorial , itemQuit);
        menu.setTranslateX(width/2 - 150);
        menu.setTranslateY(height/2 - 170);

        root.getChildren().add(menu);
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
    
    public static class MenuBox extends VBox
    {
        public MenuBox(MenuItem... items)
        {
            getChildren().add(createSeparator());
            
            for (MenuItem item : items)
            {
                getChildren().addAll(item , createSeparator());
            }
        }
        
        private Line createSeparator()
        {
            Line sep = new Line();
            sep.setEndX(300);
            sep.setStroke(Color.DARKGREY);
            return sep;
        }
    }
    
    public static class MenuItem extends StackPane
    {
        public MenuItem(String name)
        {
            LinearGradient gradient = new LinearGradient(0,0,1,0,true,CycleMethod.NO_CYCLE,new Stop[]{
                new Stop(0,Color.ORANGE),
                new Stop(0.1,Color.YELLOW),
                new Stop(0.9, Color.YELLOW),
                new Stop(1,Color.ORANGE)
            });
            
            Rectangle bg = new Rectangle(300,50);
            bg.setOpacity(0.4);
            bg.setFill(Color.AQUAMARINE);
            
            Text text = new Text(name);
            text.setFill(Color.DARKGREY);
            text.setFont(Font.font("Tw Cen MT Condensed" , FontWeight.SEMI_BOLD, 42));
            
            setAlignment(Pos.CENTER);
            getChildren().addAll(bg , text);
            
            setOnMouseEntered(event -> {
                bg.setFill(gradient);
                text.setFill(Color.WHITE);
            });
            
            setOnMouseExited(event -> {
                bg.setFill(Color.AQUAMARINE);
                text.setFill(Color.DARKGREY);
            });
            
            setOnMousePressed(event -> {
                bg.setFill(Color.ORANGE);
            });
            
            setOnMouseReleased(event -> {
                bg.setFill(gradient);
            });
        }
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }
}
