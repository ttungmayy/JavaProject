package towerbloxx;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Menu extends Pane{
    public Pane runMenu()
    {
        Pane menu = new Pane();
        menu.setPrefSize(Main.width, Main.height);
        try{
            InputStream menuPic = Files.newInputStream(Paths.get("C:/Users/PM/Documents/NetBeansProjects/TowerBloxx/src/towerbloxx/menu.png"));
            ImageView imgMenu = new ImageView(new Image(menuPic));
            imgMenu.setFitWidth(Main.width);
            imgMenu.setFitHeight(Main.height);
                
            menu.getChildren().add(imgMenu);
        }
        catch (IOException e)
        {
            System.out.println("Cannot load menu.png");
        }
        
        Text story = new Text("Story");
        story.setFont(Font.font("Tw Cen MT Condensed" , FontWeight.SEMI_BOLD, 80));
        story.setTranslateX(470);
        story.setTranslateY(390);
        story.setOnMouseEntered(event-> story.setFill(Color.WHITE));
        story.setOnMouseExited(event-> story.setFill(Color.BLACK));
        story.setOnMouseClicked(event -> {
                                        Pane storyGame = new StoryMode().runStoryMode();
                                        Main.game = new Scene(storyGame);
                                        Main.primaryStage.setScene(Main.game);
                                        Main.primaryStage.show();
                                });
        
        Text endless = new Text("Endless");
        endless.setFont(Font.font("Tw Cen MT Condensed" , FontWeight.SEMI_BOLD, 80));
        endless.setTranslateX(430);
        endless.setTranslateY(520);
        endless.setOnMouseEntered(event-> endless.setFill(Color.WHITE));
        endless.setOnMouseExited(event-> endless.setFill(Color.BLACK));
        endless.setOnMouseClicked(event -> {
                                        Pane endlessPane = new GameApp().runGameApp();
                                        Main.endlessScene = new Scene(endlessPane);
                                        Main.primaryStage.setScene(Main.endlessScene);
                                        Main.primaryStage.show();
                                });
        
        Text instruc = new Text("Tutorial");
        instruc.setFont(Font.font("Tw Cen MT Condensed" , FontWeight.SEMI_BOLD, 80));
        instruc.setTranslateX(440);
        instruc.setTranslateY(650);
        instruc.setOnMouseEntered(event-> instruc.setFill(Color.WHITE));
        instruc.setOnMouseExited(event-> instruc.setFill(Color.BLACK));
        instruc.setOnMouseClicked(event -> {
                                        Pane tutorialPane = new Tutorial().runTutorial();
                                        Main.tutorial = new Scene(tutorialPane);
                                        Main.primaryStage.setScene(Main.tutorial);
                                        Main.primaryStage.show();
                                });
        
        Text quit = new Text("Quit");
        quit.setFont(Font.font("Tw Cen MT Condensed" , FontWeight.SEMI_BOLD, 80));
        quit.setTranslateX(480);
        quit.setTranslateY(780);
        quit.setOnMouseEntered(event-> quit.setFill(Color.WHITE));
        quit.setOnMouseExited(event-> quit.setFill(Color.BLACK));
        quit.setOnMouseClicked(event -> System.exit(0));

        menu.getChildren().addAll(story , endless , instruc , quit);
        Main.root.getChildren().add(this);
        
        return menu;
    }
}
