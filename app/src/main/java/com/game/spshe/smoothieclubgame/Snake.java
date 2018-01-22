package com.game.spshe.smoothieclubgame;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;

/**
 * Created by spshe on 1/21/2017.
 */

public class Snake extends FallingObject {



    public Snake(Context context, int numberOfObjects, double widthRatio, double heightRatio)
    {
        super(context, numberOfObjects, widthRatio, heightRatio);
    }

    public void reset()
    {
        fallingValue = -99; //catching snake loses points
        ballY = 0;
        ballX = (float)(Math.random() * 900 * widthRatioObj);

        ballSpeedY = 0;
        bmp = BitmapFactory.decodeResource(getResources(), com.game.spshe.smoothieclubgame.R.drawable.invisible);
        new CountDownTimer(7000, 1000) { // snake appears approximately every 7 seconds

            public void onTick(long millisUntilFinished) {
                //mTextField.setText("Seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() { //snake appears after 7 seconds
                ballSpeedY = (float)((23 + speedIncrement));
                bmp = BitmapFactory.decodeResource(getResources(), com.game.spshe.smoothieclubgame.R.drawable.bomb);
            }

        }.start();
        //bmp = BitmapFactory.decodeResource(getResources(), R.drawable.quanho);
    }
}
