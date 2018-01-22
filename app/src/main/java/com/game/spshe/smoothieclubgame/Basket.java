package com.game.spshe.smoothieclubgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by spshe on 1/20/2017.
 */

public class Basket extends View {

    protected Bitmap bmp;
    protected Paint paint;
    protected int delayFactor = 1;

    protected double widthRatioObj = 0;
    protected double heightRatioObj = 0;

    protected float ballX = 500;
    protected float ballY = 1400;

    public Basket (Context context, int numberOfObjects, double widthRatio, double heightRatio)
    {
        super(context);
        bmp = BitmapFactory.decodeResource(getResources(), com.game.spshe.smoothieclubgame.R.drawable.basket);
        delayFactor = numberOfObjects;
        paint = new Paint();
        widthRatioObj = widthRatio;
        heightRatioObj = heightRatio;
    }

    protected void onDraw(Canvas canvas)
    {
        canvas.drawBitmap(bmp, (float)(ballX * widthRatioObj), (float)(ballY * heightRatioObj), paint);

    }


}
