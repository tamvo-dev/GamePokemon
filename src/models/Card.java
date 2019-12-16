package models;

import java.io.IOException;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.Utils;

public class Card extends Button {

    public static final int WIDTH = 72;
    public static final int HEIGHT = 72;

    private ImageView icon;
    private ImageView iconDefault;
    private int time;
    private int iconID;
    private boolean isActive = false;

    public Card(int iconID, int time) {
        this.time = time;
        this.iconID = iconID;

        try {
            icon = Utils.getImage(iconID);
            iconDefault = Utils.getImage(Utils.DEFAULT);
            this.setGraphic(iconDefault);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void changeStatus() {
        this.setGraphic(icon);

        if(isActive == false) {

            isActive = true;
            Thread thread = new Thread(new Runnable() {

                public void run() {
                    try {
                        Thread.sleep(time);
                        isActive = false;
                        Platform.runLater(new Runnable() {
                            public void run() {

                                if(!isDisable()) {
                                    setGraphic(iconDefault);
                                }

                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
        }

    }

    public int getIconID() {
        return iconID;
    }

    public void setDefaultStatus() {
        this.setGraphic(iconDefault);
        this.isActive = false;
    }

    public boolean getActive() {
        return isActive;
    }

    public void setDisableCard() {
        this.setGraphic(null);
        this.setDisable(true);
    }
}
