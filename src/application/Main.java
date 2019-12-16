package application;



import java.util.Random;
import java.util.Timer;

import controll.GameManager;
import controll.GameManagerLogic;
import controll.UpdateTime;
import controll.UpdateTimeLogic;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import models.Card;

public class Main extends Application implements UpdateTime, GameManager {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int OFFSETX = 50;
    public static final int OFFSETY = 50;
    public static final int OFFSET = 2;

    private Card mCards[][] = null;
    private Label mLabel = null;
    private Label mLabelTime = null;
    private UpdateTimeLogic mTimeLogic;
    private GameManagerLogic mGameManagerLogic;
    private int[] iconID;
    private int mTime = 2000;
    private int mScore = 0;

    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();
            mGameManagerLogic = new GameManagerLogic(this);

            iconID = new int[20];
            for(int i=0; i<20; i++) {
                iconID[i] = i%10;
            }

            for(int i=0; i<10; i++) {
                Random ran = new Random();
                int a = ran.nextInt(20);
                int b = ran.nextInt(20);
                int temp = iconID[a];
                iconID[a] = iconID[b];
                iconID[b] = temp;
            }

            mCards = new Card[5][4];
            for(int i=0; i<5; i++) {
                for(int j=0; j<4; j++) {
                    int id = iconID[i*4 + j];
                    mCards[i][j] = new Card(id, mTime);
                    mCards[i][j].setLayoutX(OFFSETX + i*(Card.WIDTH + OFFSET));
                    mCards[i][j].setLayoutY(OFFSETY + j*(Card.HEIGHT + OFFSET));
                    final int x = i;
                    final int y = j;
                    mCards[i][j].setOnAction(new EventHandler<ActionEvent>() {

                        public void handle(ActionEvent event) {
                            if(mCards[x][y].getActive() == false) {
                                mGameManagerLogic.selectCard(mCards[x][y], x, y);
                            }
                        }
                    });
                    root.getChildren().add(mCards[i][j]);
                }
            }

            // Add score, button play again
            VBox right = new VBox();
            right.setPadding(new Insets(0, 50, 0, 0));
            int width = 150;
            int height = 40;

            mLabelTime = new Label();
            mLabelTime.setText("Time 01:00");
            mLabelTime.setMinSize(width, height);
            right.getChildren().add(mLabelTime);

            mLabel = new Label();
            mLabel.setText("Your score: " + mScore);
            mLabel.setMinSize(width, height);
            right.getChildren().add(mLabel);

            Button btnAgain = new Button("Play Again");
            btnAgain.setMinSize(width, height);
            btnAgain.setWrapText(true);
            right.getChildren().add(btnAgain);
            root.setRight(right);

            // Add controll
            mTimeLogic = new UpdateTimeLogic(this);
            mTimeLogic.start();


            // Show scene
            Scene scene = new Scene(root, WIDTH, HEIGHT);
            primaryStage.setTitle("Pokemon");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void onEndTime() {

    }

    public void onUpdateTime(final String time) {

        Platform.runLater(new Runnable() {
            public void run() {
                mLabelTime.setText("Time " + time);
            }
        });
    }
}
