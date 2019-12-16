package application;

import java.util.Random;

import controll.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import models.Card;
import utils.Utils;

public class Main extends Application implements UpdateTime {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int OFFSETX = 50;
    public static final int OFFSETY = 50;
    public static final int OFFSET = 2;

    private int mTime = 2000;
    private int mScore = 120;

    private UpdateTimeLogic mTimeLogic;
    private GameManagerLogic mGameManagerLogic;
    private PlaySound mPlaySound;
    private int[] iconID;

    private Card mCards[][] = null;
    private Label mLabelScore = null;
    private Label mLabelTime = null;

    private Stage windows;
    private Scene startScene;
    private Scene playScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        this.windows = primaryStage;
        try {
            // Start scene
            BorderPane start = new BorderPane();
            VBox center = new VBox();
            center.setMaxWidth(WIDTH);
            center.setAlignment(Pos.CENTER);
            center.setSpacing(20);

            Label label = new Label("Please choose the game mode");
            label.setFont(Font.font("verdana", 20));
            label.setMaxWidth(WIDTH);
            label.setMinHeight(50);
            label.setAlignment(Pos.CENTER);
            start.setTop(label);

            Button btnAdvance = new Button("Advance");
            btnAdvance.setMinSize(200, 50);
            btnAdvance.setWrapText(true);
            center.getChildren().add(btnAdvance);
            btnAdvance.setOnAction(actionEvent -> {
                mTime = 1000;
                onStartGame();
            });

            Button btnMedium = new Button("Medium");
            btnMedium.setMinSize(200, 50);
            btnMedium.setWrapText(true);
            center.getChildren().add(btnMedium);
            btnMedium.setOnAction(actionEvent -> {
                mTime = 2000;
                onStartGame();
            });

            Button btnEazy = new Button("Eazy");
            btnEazy.setMinSize(200, 50);
            btnEazy.setWrapText(true);
            center.getChildren().add(btnEazy);
            btnEazy.setOnAction(actionEvent -> {
                mTime = 3000;
                onStartGame();
            });

            start.setCenter(center);
            startScene = new Scene(start, WIDTH, HEIGHT);

            // Show scene;
            primaryStage.setTitle("Pokemon");
            primaryStage.setScene(startScene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onStartGame() {

        // Add play sound
        mPlaySound = new PlaySound();

        mScore = 120;
        BorderPane root = new BorderPane();
        BackgroundImage backgroundImage =  new BackgroundImage(Utils.getBackGround(),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        root.setBackground(background);
        mGameManagerLogic = new GameManagerLogic();

        iconID = new int[20];
        for (int i = 0; i < 20; i++) {
            iconID[i] = i % 10;
        }

        for (int i = 0; i < 10; i++) {
            Random ran = new Random();
            int a = ran.nextInt(20);
            int b = ran.nextInt(20);
            int temp = iconID[a];
            iconID[a] = iconID[b];
            iconID[b] = temp;
        }

        mCards = new Card[5][4];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                int id = iconID[i * 4 + j];
                mCards[i][j] = new Card(id, mTime);
                mCards[i][j].setLayoutX(OFFSETX + i * (Card.WIDTH + OFFSET));
                mCards[i][j].setLayoutY(OFFSETY + j * (Card.HEIGHT + OFFSET));
                final int x = i;
                final int y = j;
                mCards[i][j].setOnAction(new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent event) {
                        if (mCards[x][y].getActive() == false) {
                            mGameManagerLogic.selectCard(mCards[x][y], x, y);
                            // Ckeck win
                            checkWin();

                        }
                    }
                });
                root.getChildren().add(mCards[i][j]);
            }
        }

        // Add score, button play again
        VBox right = new VBox();
        right.setSpacing(10);
        right.setPadding(new Insets(0, 50, 0, 0));
        int width = 150;
        int height = 40;

        mLabelTime = new Label();
        mLabelTime.setText("Time 02 : 00 : 00");
        mLabelTime.setMinSize(width, height);
        right.getChildren().add(mLabelTime);

        mLabelScore = new Label();
        mLabelScore.setText("Your score: " + mScore);
        mLabelScore.setMinSize(width, height);
        right.getChildren().add(mLabelScore);

        Button btnSound = new Button("Sound off");
        btnSound.setMinSize(width, height);
        btnSound.setWrapText(true);
//        btnSound.setOnAction(actionEvent -> {
//           String status = mPlaySound.changeStatus();
//           btnSound.setText(status);
//        });
        right.getChildren().add(btnSound);

        Button btnAgain = new Button("Play Again");
        btnAgain.setMinSize(width, height);
        btnAgain.setWrapText(true);
        btnAgain.setOnAction(actionEvent -> {
            mTimeLogic.stop();
            onStartGame();
        });
        right.getChildren().add(btnAgain);

        Button btnQuit = new Button("Quit");
        btnQuit.setMinSize(width, height);
        btnQuit.setWrapText(true);
        btnQuit.setOnAction(actionEvent -> {
            Platform.exit();
        });
        right.getChildren().add(btnQuit);

        root.setRight(right);

        // Set scence
        playScene = new Scene(root, WIDTH, HEIGHT);
        windows.setScene(playScene);

        // Add controll
        mTimeLogic = new UpdateTimeLogic(this);
        mTimeLogic.start();

    }

    @Override
    public void stop() throws Exception {
        mTimeLogic.stop();
        super.stop();
    }

    public void onStopGame(){

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                mCards[i][j].setDisable(true);
            }
        }

        if(mTimeLogic != null){
            mTimeLogic.stop();
        }
    }

    public void checkWin(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isWin()){
                    onWinGame();
                }
            }
        });

        thread.start();
    }

    public boolean isWin(){

        for (int i=0;i<5;i++){
            for(int j=0;j<4;j++){
                if (mCards[i][j].isDisable() == false){
                    return false;
                }
            }
        }

        return true;
    }

    public void onWinGame(){
        Platform.runLater(new Runnable() {
            public void run() {
                mTimeLogic.stop();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Messenger");
                String level;
                if (mTime == 1000){
                    level = "Advance";
                } else if(mTime == 2000){
                    level = "Medium";
                } else {
                    level= "Easy";
                }
                String content = "Your score: " + mScore + "\nLevel: " + level;
                alert.setContentText(content);
                alert.showAndWait();
            }
        });

    }

    public void onEndTime() {
        Platform.runLater(new Runnable() {
            public void run() {
                onStopGame();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Messenger");
                alert.setContentText("You lose!");
                alert.showAndWait();
            }
        });
    }

    public void onUpdateScore(){
        mScore--;
        Platform.runLater(new Runnable() {
            public void run() {
                mLabelScore.setText("Your score: " + mScore);
            }
        });

    }

    public void onUpdateTime(final String time) {

        Platform.runLater(new Runnable() {
            public void run() {
                mLabelTime.setText("Time " + time);
            }
        });
    }
}
