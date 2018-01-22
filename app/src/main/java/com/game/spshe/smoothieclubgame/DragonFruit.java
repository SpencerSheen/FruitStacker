package com.game.spshe.smoothieclubgame;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;

/**
 * Created by spshe on 1/21/2017.
 */

public class DragonFruit extends FallingObject {

    public DragonFruit(Context context, int numberOfObjects, double widthRatio, double heightRatio)
    {
        super(context, numberOfObjects, widthRatio, heightRatio);
    }
    public void reset()
    {
        fallingValue = 15; //catching dragon fruit gains bonus points
        ballY = 0;
        ballX = (float)(Math.random() * 900 * widthRatioObj);

        ballSpeedY = 0;
        bmp = BitmapFactory.decodeResource(getResources(), com.game.spshe.smoothieclubgame.R.drawable.invisible);
        new CountDownTimer(10000, 1000) { // snake appears approximately every 10 seconds

            public void onTick(long millisUntilFinished) {
                //mTextField.setText("Seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() { //snake appears after 7 seconds
                ballSpeedY = (float)((23 + speedIncrement));
                bmp = BitmapFactory.decodeResource(getResources(), com.game.spshe.smoothieclubgame.R.drawable.dragonfruit);
            }

        }.start();
        //bmp = BitmapFactory.decodeResource(getResources(), R.drawable.quanho);
    }
}
