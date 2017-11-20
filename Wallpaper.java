/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package towerbloxx;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
/**
 *
 * @author namedojimo
 */
public class Wallpaper {
    ImageView imgBG;
    public ImageView getWallpaper(){

        try{
        InputStream bg = Files.newInputStream(Paths.get("/Users/namedojimo/NetBeansProjects/Project/src/towerbloxx/BGFinal.png"));
        imgBG = new ImageView(new Image(bg));
        imgBG.setX(0);
        imgBG.setY(-860);
        //imgBG.setFitWidth(1024);
        //imgBG.setFitHeight(0);
        } catch (IOException e)        {
            System.out.println("Cannot load town.jpg");
        }
        return imgBG;
    }
}
