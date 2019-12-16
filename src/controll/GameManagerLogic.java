package controll;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import models.Card;

public class GameManagerLogic {

    private List<Card> mCards;

    public GameManagerLogic() { ;
        mCards = new ArrayList<Card>();

    }

    public void selectCard(Card card, int x, int y) {
        card.changeStatus();

        mCards.add(card);

        if(mCards.size() == 2) {
            checkPoints();
        }
    }

    private void checkPoints() {

        final Card a = mCards.get(0);
        final Card b = mCards.get(1);
        if(a.getActive() == false) {
            mCards.remove(0);
            return;
        }

        mCards.clear();

        if(a.getIconID() == b.getIconID()) {
            twoCardsTrue(a, b);
        } else {
            twoCardsFalse(a, b);
        }

    }

    private void twoCardsFalse(Card a, Card b){
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

    private void twoCardsTrue(Card a, Card b){
        Thread thread = new Thread(new Runnable() {

            public void run() {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(new Runnable() {
                    public void run() {
                        a.setDisableCard();
                        b.setDisableCard();
                    }
                });
            }
        });

        thread.start();
    }

}
