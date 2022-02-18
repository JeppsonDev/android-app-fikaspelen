package cs.umu.se.fikaspelen;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * A class which updates a UI element depending on the clock's value
 *
 * @since 2021-03-22
 * @author Jesper Byström
 */
public class FikaTimer extends CountDownTimer {

    private long ms;
    private TextView timerText;
    private FikaTimerFinishedListener fikaTimerFinishedListener;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public FikaTimer(TextView timerText, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.ms = millisInFuture;
        this.timerText = timerText;
    }

    /**
     * Updates the timer's visual text
     *
     * @param ms
     */
    private void update(long ms) {
        timerText.setText(FikaTimerString.generate(ms));
    }

    @Override
    public void onTick(long millisUntilFinished) {
        ms = millisUntilFinished;
        update(millisUntilFinished);
    }

    @Override
    public void onFinish() {
        fikaTimerFinishedListener.finished();
    }

    /**
     * Sets the fika timer finished listener
     * @param l
     */
    public void setFikaTimerFinishedListener(FikaTimerFinishedListener l) {
        fikaTimerFinishedListener = l;
    }

    /**
     * Returns the time left
     * @return long
     */
    public long getTimeLeft() {
        return ms;
    }

    /**
     * Sets the time left
     * @param ms
     */
    public void setTimeLeft(long ms) {
        this.ms = ms;
    }
}
