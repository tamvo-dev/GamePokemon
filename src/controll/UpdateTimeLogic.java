package controll;

public class UpdateTimeLogic extends Thread  {

    private UpdateTime mUpdateTime;
    private int mHundredth = 99;
    private int mCurrentSecond = 59;
    private int mCurrentMinute = 1;

    public UpdateTimeLogic(UpdateTime mUpdateTime) {
        this.mUpdateTime = mUpdateTime;
    }

    @Override
    public void run() {

        while(true) {
            String time = mCurrentMinute + " : " + mCurrentSecond + " : " + mHundredth;
            mUpdateTime.onUpdateTime(time);

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            mHundredth--;
            if(mHundredth < 0) {
                mHundredth = 99;
                mCurrentSecond--;
                mUpdateTime.onUpdateScore();
            }
            if(mCurrentSecond < 0) {
                mCurrentSecond = 59;
                mCurrentMinute--;
            }
            if(mCurrentMinute == 0 && mCurrentSecond == 0 && mHundredth == 0) {
                mUpdateTime.onEndTime();
                break;
            }
        }

    }

}
