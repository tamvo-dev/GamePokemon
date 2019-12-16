package controll;

import models.Point;

public class Rules {

    private int[][] mArrLable;

    public Rules() {
        mArrLable = new int[5][4];

        for(int i=0; i<5;i++) {
            for(int j=0;j<4;j++) {
                mArrLable[i][j] = 1;
            }
        }
    }

    public boolean isWin(){
        for(int i=0; i<5;i++) {
            for(int j=0;j<4;j++) {
                if (mArrLable[i][j] == 1)
                    return false;
            }
        }
        return true;
    }

    public boolean checkRules(Point p1, Point p2) {

        mArrLable[p1.getX()][p1.getY()] = 0;
        mArrLable[p2.getX()][p2.getY()] = 0;

        if(checkTwoPoint(p1, p2)) {
            return true;
        } else {
            mArrLable[p1.getX()][p1.getY()] = 1;
            mArrLable[p2.getX()][p2.getY()] = 1;
        }

        return false;
    }

    private boolean checkTwoPoint(Point p1, Point p2) {

        if(checkLineX(p1, p2)) {
            return true;
        } else if(checkLineY(p1, p2)) {
            return true;
        } else if(checkRectangleX(p1, p2)) {
            return true;
        } else if(checkRectangleY(p1, p2)) {
            return true;
        } else if(checkMoreLineX(p1, p2)) {
            return true;
        } else if(checkMoreLineY(p1, p2)) {
            return true;
        }

        return false;

    }

    private boolean checkLineX(Point a, Point b) {
        // Check line from a to b

        if(a.getY() == b.getY()) {

            int min = Math.min(a.getX(), b.getX());
            int max = Math.max(a.getX(), b.getX());

            for(int i=min; i<=max; i++) {
                if(mArrLable[i][a.getY()] == 1) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    private boolean checkLineY(Point a, Point b) {

        if(a.getX() == b.getX()) {

            int min = Math.min(a.getY(), b.getY());
            int max = Math.max(a.getY(), b.getY());

            for(int i=min; i<=max; i++) {
                if(mArrLable[a.getX()][i] == 1) {
                    return false;
                }
            }

            return true;
        }

        return false;

    }

    private boolean checkRectangleX(Point p1, Point p2) {

        int minX = Math.min(p1.getX(), p2.getX());
        int maxX = Math.max(p1.getX(), p2.getX());

        for(int i=minX; i<=maxX;i++){

            if(checkLineX(p1, new Point(i, p1.getY()))
                    && checkLineY(new Point(i, p1.getY()), new Point(i, p2.getY()))
                    && checkLineX(new Point(i, p2.getY()), p2)) {
                return true;
            }

            if(checkLineX(p2, new Point(i, p2.getY()))
                    && checkLineY(new Point(i, p1.getY()), new Point(i, p2.getY()))
                    && checkLineX(new Point(i, p1.getY()), p1)) {
                return true;
            }
        }

        return false;
    }

    private boolean checkRectangleY(Point p1, Point p2) {

        int minY = Math.min(p1.getY(), p2.getY());
        int maxY = Math.max(p1.getY(), p2.getY());

        for(int i=minY; i<=maxY;i++){

            if(checkLineY(p1, new Point(p1.getX(), i))
                    && checkLineX(new Point(p1.getX(), i), new Point(p2.getX(), i))
                    && checkLineY(new Point(p2.getX(), i), p2)) {
                return true;
            }

            if(checkLineY(p2, new Point(p2.getX(), i))
                    && checkLineX(new Point(p2.getX(), i), new Point(p1.getX(), i))
                    && checkLineY(new Point(p1.getX(), i), p1)) {
                return true;
            }
        }

        return false;
    }

    private boolean checkMoreLineX(Point p1, Point p2) {
        // Check right
        boolean isMore1 = true;
        boolean isMore2 = true;
        for(int i=p1.getX(); i<5;i++) {
            if(mArrLable[i][p1.getY()] == 1) {
                isMore1 = false;
                break;
            }
        }
        for(int i=p2.getX();i<5;i++) {
            if(mArrLable[i][p2.getY()] == 1) {
                isMore2 = false;
                break;
            }
        }
        if(isMore1 && isMore2) {
            return true;
        }

        // Check left
        isMore1 = true;
        isMore2 = true;
        for(int i=p1.getX(); i>=0;i--) {
            if(mArrLable[i][p1.getY()] == 1) {
                isMore1 = false;
                break;
            }
        }
        for(int i=p2.getX();i>=0;i--) {
            if(mArrLable[i][p2.getY()] == 1) {
                isMore2 = false;
                break;
            }
        }
        if(isMore1 && isMore2) {
            return true;
        }

        return false;
    }

    private boolean checkMoreLineY(Point p1, Point p2) {
        // Check bottom
        boolean isMore1 = true;
        boolean isMore2 = true;
        for(int i=p1.getY(); i<4;i++) {
            if(mArrLable[p1.getX()][i] == 1) {
                isMore1 = false;
                break;
            }
        }
        for(int i=p2.getY();i<4;i++) {
            if(mArrLable[p2.getX()][i] == 1) {
                isMore2 = false;
                break;
            }
        }
        if(isMore1 && isMore2) {
            return true;
        }

        // Check top
        isMore1 = true;
        isMore2 = true;
        for(int i=p1.getY(); i>=0;i--) {
            if(mArrLable[p1.getX()][i] == 1) {
                isMore1 = false;
                break;
            }
        }
        for(int i=p2.getY();i>=0;i--) {
            if(mArrLable[p2.getX()][i] == 1) {
                isMore2 = false;
                break;
            }
        }
        if(isMore1 && isMore2) {
            return true;
        }

        return false;
    }
}
