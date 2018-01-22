package com.game.spshe.smoothieclubgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

/**
 * Created by spshe on 1/20/2017.
 */

public class FallingObject extends View {

    protected double heightRatioObj = 0;
    protected double widthRatioObj = 0;

    //Board size
    private int xMin = 0;
    private int xMax = 1000;
    private int yMin = 0;
    private int yMax = 1300;

    //Ball size
    protected float ballRadius = 30;

    //Ball initial position
    protected float ballX = (float)(Math.random() * 900 * widthRatioObj);
    protected float ballY = 0;

    //Ball speed
    protected float ballSpeedY = (float)(20*heightRatioObj);
    protected static double speedIncrement = 0;

    //Random initializing stuff
    protected RectF ballBounds; // canvas oval
    protected Paint paint;

    //Time
    protected int timeRemaining = 5000;
    protected int delayFactor = 1; /* Delay factor is based on the number of active balls in the program. Since the program repaints
     itself for each object, the delay factor would need to be higher as there are more objects running.
     Ex: two objects will take twice as long to run compared to only one object if the delay factor is not changed*/

    //Selecting balls
    protected static boolean canBeSelected = false;
    private static boolean isSelected = false;

    protected Bitmap bmp;

    private float basketPosition = 0;

    protected double baseDelay = 10;

    protected int sampleCount = 0;
    protected int currentPoints = 0;
    protected int fallingValue = 1; // catching fruit gives one point

    public FallingObject(Context context, int numberOfObjects, double widthRatio, double heightRatio)
    {
        super(context);
        ballBounds = new RectF();
        paint = new Paint();
        delayFactor = numberOfObjects;
        heightRatioObj = heightRatio;
        widthRatioObj = widthRatio;
        reset();

    }


    protected void onDraw (Canvas canvas)
    {
        ballBounds.set(ballX-ballRadius, ballY-ballRadius, ballX+ballRadius, ballY+ballRadius); //figure this out

        canvas.drawBitmap(bmp, ballX, ballY, paint); //ballX is left
        update(); // moves the ball

        Bitmap basket = BitmapFactory.decodeResource(getResources(), com.game.spshe.smoothieclubgame.R.drawable.apple);

        if(ballY > 1800*heightRatioObj) //ball hits the end of the board
        {
            reset();
        }

        if(ballY + 60 * heightRatioObj >= 1400 * heightRatioObj && ballY + 60 * heightRatioObj <= 1500 * heightRatioObj) //ball at height of the basket
        {
            //if ball touches basket
            if(ballX > basketPosition + 500*widthRatioObj - 20*widthRatioObj && ballX + 50*widthRatioObj < basketPosition + 500*widthRatioObj + basket.getWidth() + 20*widthRatioObj) //500 is the starting pixel location of the basket
            {
                reset();
                currentPoints += fallingValue; //adds points to score
            }
            //Log.d("Value", String.valueOf(bas))
            //reset();
        }
        try{
            Thread.sleep((long)(baseDelay/delayFactor));
        } catch(InterruptedException e){ }

        invalidate();
    }

    public float getBallX()
    {
        return ballX;
    }
    public float getBallY()
    {
        return ballY;
    }

    protected void update() //moves object down
    {
        ballY += ballSpeedY;
    }

    public void setBasketPosition(float position)
    {
        basketPosition = position;
    }

    public void reset()
    {
        fallingValue = 1;
        ballY = 0; //puts object back on top
        ballX = (float)(Math.random() * 900 * widthRatioObj); //sets object in random spot
        ballSpeedY = (float)((18) + speedIncrement); //sets random speed from 30 to 40
        int randomFruitValue = (int)(Math.random()*2); //decides on random fruit (currently apple or banana)
        if(randomFruitValue == 0)
            bmp = BitmapFactory.decodeResource(getResources(), com.game.spshe.smoothieclubgame.R.drawable.apple);
        else if(randomFruitValue == 1)
            bmp = BitmapFactory.decodeResource(getResources(), com.game.spshe.smoothieclubgame.R.drawable.banana);
    }

    public int addPoints() //adds points to user score
    {
        int pointsAdded = currentPoints;
        currentPoints = 0;
        return pointsAdded;
    }

    public void increaseSpeed() // changes delay factor
    {
        speedIncrement += 1;
        //double pixPerSec = 40*1000/30;
        //baseDelay = 20000/pixPerSec;
    }

    public void resetSpeed()
    {
        speedIncrement = 0;
    }


}
