package com.game.spshe.smoothieclubgame;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.util.Log;

/**
 * Created by spshe on 1/21/2017.
 */

public class Vegetable extends FallingObject {

    private static int nextDelay;

    public Vegetable(Context context, int numberOfObjects, int waitTime, double widthRatio, double heightRatio)
    {
        super(context, numberOfObjects, widthRatio, heightRatio);
        nextDelay = waitTime;
        Log.d("waitTime", String.valueOf(waitTime));
        waitTime();
    }

    public void waitTime()
    {
        ballSpeedY = 0;
        bmp = BitmapFactory.decodeResource(getResources(), com.game.spshe.smoothieclubgame.R.drawable.invisible);
        Log.d("nextDelay", String.valueOf(nextDelay));

        if (nextDelay != 0) //checks to see if delay is necessary
        {

            new CountDownTimer(nextDelay, 1000) { // snake appears approximately every 7 seconds

                public void onTick(long millisUntilFinished) {
                    //mTextField.setText("Seconds remaining: " + millisUntilFinished / 1000);
                }

                public void onFinish() { //snake appears after 7 seconds

                    nextDelay = 0;
                    reset();
                }

            }.start();
        }
    }

    public void reset()
    {
        fallingValue = -1; //catching vegetable loses points
        ballSpeedY = (float)((18 + speedIncrement));
        ballY = 0;
        ballX = (float)(Math.random() * 900*widthRatioObj);
        int randomFruitValue = (int)(Math.random()*2);
        if(randomFruitValue == 0)
            bmp = BitmapFactory.decodeResource(getResources(), com.game.spshe.smoothieclubgame.R.drawable.broccoli);
        else if(randomFruitValue == 1)
            bmp = BitmapFactory.decodeResource(getResources(), com.game.spshe.smoothieclubgame.R.drawable.corn);



    }
}
