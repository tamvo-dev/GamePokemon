package utils;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Utils {

    public static final int DEFAULT = -1;
    private static final String URL = "C:\\Users\\LAptop\\IdeaProjects\\GamePokemon\\images\\";

    public static ImageView getImage(int position) throws IOException {
        if(position >= 0 && position < 10) {
            FileInputStream input = new FileInputStream(URL + position + ".png");
            Image image = new Image(input);
            ImageView imageView = new ImageView(image);
            //ImageView imageView = new ImageView(URL + position + ".png");
            return imageView;
        }

        FileInputStream input = new FileInputStream(URL + "default.png");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        return imageView;
    }
}

