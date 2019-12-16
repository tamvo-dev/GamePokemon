package controll;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import models.Card;
import models.Point;

public class GameManagerLogic {

    private GameManager mGameManager;
    private List<Card> mCards;
    private List<Point> mPoints;
    private Rules mRules;

    public GameManagerLogic(GameManager mGameManager) {
        this.mGameManager = mGameManager;
        mCards = new ArrayList<Card>();
        mPoints = new ArrayList<Point>();
        mRules = new Rules();

    }

    public void selectCard(Card card, int x, int y) {
        card.changeStatus();

        mCards.add(card);
        mPoints.add(new Point(x, y));

        if(mCards.size() == 2) {
            checkPoints();
        }
    }

    private void checkPoints() {

        final Card a = mCards.get(0);
        final Card b = mCards.get(1);
        if(a.getActive() == false) {
            mCards.remove(0);
            mPoints.remove(0);
            return;
        }

        Point p1 = mPoints.get(0);
        Point p2 = mPoints.get(1);

        mCards.clear();
        mPoints.clear();

        if(a.getIconID() == b.getIconID() && mRules.checkRules(p1, p2)) {
            a.setDisableCard();
            b.setDisableCard();
        } else {
            Thread thread = new Thread(new Runnable() {

                public void run() {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(new Runnable() {
                        public void run() {
                            a.setDefaultStatus();
                            b.setDefaultStatus();
                        }
                    });
                }
            });

            thread.start();
        }
    }

}
