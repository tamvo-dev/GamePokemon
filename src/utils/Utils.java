package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Utils {

    public static final int DEFAULT = -1;

    public static Image getBackGround(){

        FileInputStream input = null;
        try {

            input = new FileInputStream("src\\images\\background.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(input);
        return image;
    }

    public static ImageView getImage(int position) throws IOException {
        if(position >= 0 && position < 10) {

            FileInputStream input = new FileInputStream("src\\images\\" + position + ".png");
            Image image = new Image(input);
            ImageView imageView = new ImageView(image);
            return imageView;
        }

        FileInputStream input = new FileInputStream( "src\\images\\default.png");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        return imageView;
    }
}

