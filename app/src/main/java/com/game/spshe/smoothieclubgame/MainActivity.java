package com.game.spshe.smoothieclubgame;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by spshe on 1/19/2017.
 */

public class MainActivity extends AppCompatActivity {


    final List<FallingObject> fruit = new ArrayList<>(); //regular fruit
    final List<FallingObject> vegetable = new ArrayList<>(); //vegetable
    final List<FallingObject> snake = new ArrayList<>(); //snake
    final List<FallingObject> dragonfruit = new ArrayList<>(); //snake
    int totalPoints = 0;
    int totalObjects = 0;
    int totalVegetables = 0;
    static int entranceTime = 0;
    int pointIncrement = 0;
    int strikesLeft = 3;

    double widthRatio = 0;
    double heightRatio = 0;


    // SETTING UP DATABASE
    Connect myDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display d = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        double width = d.getWidth();
        double height = d.getHeight();
        widthRatio = width/1080;
        heightRatio = height/1920; //scaling to device size


        Log.d("height", String.valueOf(height));
        Log.d("width", String.valueOf(width));

        myDB = new Connect(this); //initialize database
        mainMenu();
    }

    public void mainMenu()
    {
        setContentView(com.game.spshe.smoothieclubgame.R.layout.menuscreen); //initializing starting board

        Button start = (Button) findViewById(com.game.spshe.smoothieclubgame.R.id.start);
        start.setY((int)(800 * heightRatio));

        Button howToPlay = (Button) findViewById(com.game.spshe.smoothieclubgame.R.id.howToPlay);
        howToPlay.setY((int)(900 * heightRatio));

        Button viewData = (Button) findViewById(com.game.spshe.smoothieclubgame.R.id.viewData);
        viewData.setY((int)(1000 * heightRatio));

        Button deleteData = (Button) findViewById(com.game.spshe.smoothieclubgame.R.id.deleteData);
        deleteData.setY((int)(1100 * heightRatio));

        start.setOnClickListener(new View.OnClickListener() { // start button click
            @Override
            public void onClick(View view) {
                startGame();
            }
        });

        howToPlay.setOnClickListener(new View.OnClickListener() { // start button click
            @Override
            public void onClick(View view) {
                howToPlay();
            }
        });

        viewData.setOnClickListener(new View.OnClickListener() { // views entire database
            @Override
            public void onClick(View view) {
                viewTopFive();
            }
        });

        deleteData.setOnClickListener(new View.OnClickListener() { // deletes entire database
            @Override
            public void onClick(View view) {
                deleteData();
            }
        });
    }

    public void deleteData() // clears entire database
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        //alert.setTitle("Delete");
        alert.setMessage("Are you sure you want to remove all scores?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                myDB.deleteData(); // clear database
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();

    }


    public void viewData() // views all data
    {
        Cursor res = myDB.getAllData();
        if(res.getCount() == 0)
        {
            showMessage("Error", "Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext())
        {
            buffer.append("Id: " + res.getString(0) + "\n");
            buffer.append("Score: " + res.getString(1) + "\n");
            buffer.append("Date: " + res.getString(2) + "\n" + "\n");

            //Format
            // Id: 1
            // Score: 4
            // Date: mm/dd/yyyy

        }

        showMessage("Data", buffer.toString());

    }

    public void viewTopFive() // views only top 5 scores
    {
        Cursor res = myDB.topFiveScores();
        if(res.getCount() == 0)
        {
            showMessage("Error", "Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        int i = 1;
        while (res.moveToNext())
        {
            buffer.append(i + ". ");
            buffer.append("Score: " + res.getString(1));
            buffer.append("    Date: " + res.getString(2) + "\n" + "\n");
            i++;
            // Format:
            // 1. Score: 4    Date: mm/dd/yyyy
        }

        showMessage("Data", buffer.toString());

    }

    public void showMessage(String title, String message) //message for scores / database values
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }

    public void howToPlay() //instructions page (only UI)
    {
        setContentView(com.game.spshe.smoothieclubgame.R.layout.how_to_play);

        TextView howToLabel = (TextView) findViewById(com.game.spshe.smoothieclubgame.R.id.howToLabel);
        TextView fruitLabel = (TextView) findViewById(com.game.spshe.smoothieclubgame.R.id.fruitLabel);
        TextView vegetableLabel = (TextView) findViewById(com.game.spshe.smoothieclubgame.R.id.vegetableLabel);
        TextView dragonFruitLabel = (TextView) findViewById(com.game.spshe.smoothieclubgame.R.id.dragonFruitLabel);
        TextView snakeLabel = (TextView) findViewById(com.game.spshe.smoothieclubgame.R.id.snakeLabel);

        ImageView fruitImage = (ImageView) findViewById(com.game.spshe.smoothieclubgame.R.id.fruitImage);
        ImageView vegetableImage = (ImageView) findViewById(com.game.spshe.smoothieclubgame.R.id.vegetableImage);
        ImageView dragonFruitImage = (ImageView) findViewById(com.game.spshe.smoothieclubgame.R.id.dragonFruitImage);
        ImageView snakeImage = (ImageView) findViewById(com.game.spshe.smoothieclubgame.R.id.snakeImage);
        Button returntoMenu = (Button) findViewById(com.game.spshe.smoothieclubgame.R.id.returnMenu);
        returntoMenu.setY(0);

        howToLabel.setText("How To Play");
        howToLabel.setTextSize(30);

        fruitImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), com.game.spshe.smoothieclubgame.R.drawable.apple));
        fruitImage.setX((int)(200*widthRatio));
        fruitImage.setY((int)(150*heightRatio));

        fruitLabel.setText("Your standard fruit, catching \n this gives you 1 point");
        fruitLabel.setX((int)(400*widthRatio));
        fruitLabel.setY(0);

        vegetableImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), com.game.spshe.smoothieclubgame.R.drawable.broccoli));
        vegetableImage.setX((int)(200*widthRatio));
        vegetableImage.setY((int)(150*heightRatio));

        vegetableLabel.setText("Your pesky vegetable, catch \nthis and you lose 1 point. Catch \n3 vegetables and the game ends");
        vegetableLabel.setX((int)(400*widthRatio));
        vegetableLabel.setY(0);

        dragonFruitImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), com.game.spshe.smoothieclubgame.R.drawable.dragonfruit));
        dragonFruitImage.setX((int)(200*widthRatio));
        dragonFruitImage.setY((int)(150*heightRatio));

        dragonFruitLabel.setText("The legendary dragonfruit, catch\nthis and you gain 15 points!!!");
        dragonFruitLabel.setX((int)(400*widthRatio));
        dragonFruitLabel.setY(0);

        snakeImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), com.game.spshe.smoothieclubgame.R.drawable.bomb));
        snakeImage.setX((int)(200*widthRatio));
        snakeImage.setY((int)(150*heightRatio));

        snakeLabel.setText("AVOID THE BOMB AT ALL COSTS!!! \nCatch this and the game ends");
        snakeLabel.setX((int)(400*widthRatio));
        snakeLabel.setY(0);

        returntoMenu.setOnClickListener(new View.OnClickListener() { // start button click
            @Override
            public void onClick(View view) {
                mainMenu();
            }
        });
    }

    public void startGame()
    {
        //////////////// SETS UP BEGINNING OF GAME /////////////////////////////

        fruit.clear();
        vegetable.clear();
        snake.clear();
        dragonfruit.clear();

        setContentView(com.game.spshe.smoothieclubgame.R.layout.gamescreen); // game board
        totalPoints = 0;
        strikesLeft = 3;

        final TextView pointLabel = (TextView) findViewById(com.game.spshe.smoothieclubgame.R.id.pointLabel); //formatting point label
        pointLabel.setText("Points: " + (totalPoints));
        pointLabel.setTextSize(20);

        final TextView vegetableLabel = (TextView) findViewById(com.game.spshe.smoothieclubgame.R.id.vegetableLabel); //formatting vegetable label
        vegetableLabel.setText("Vegetables Left: " + (strikesLeft));
        vegetableLabel.setTextSize(20);
        vegetableLabel.setX((int)(450*widthRatio));
        vegetableLabel.setY(0);

        final FallingObject sampleFruit = new FallingObject(this, 8, widthRatio, heightRatio); //initialize fruit (only one on board at one time)
        fruit.add(sampleFruit);
        addContentView(sampleFruit, new ViewGroup.LayoutParams((int)(1000*widthRatio),(int)(heightRatio*2000)));

        for(int i = 0; i < 5; i++)
        {
            final Vegetable sampleVegetable = new Vegetable(this, 8, i*10000, widthRatio, heightRatio); //initialize vegetables (scales with time)
            vegetable.add(sampleVegetable);
            addContentView(sampleVegetable, new ViewGroup.LayoutParams((int)(1000*widthRatio),(int)(heightRatio*2000)));
        }

        final DragonFruit sampleDragonFruit = new DragonFruit(this, 8, widthRatio, heightRatio); //initializes dragonfruit (appears periodically)
        dragonfruit.add(sampleDragonFruit);
        addContentView(sampleDragonFruit, new ViewGroup.LayoutParams((int)(1000*widthRatio),(int)(heightRatio*2000)));

        final Snake QuanHo = new Snake(this, 6, widthRatio, heightRatio); // initializes snake (appears periodically)
        snake.add(QuanHo);
        addContentView(QuanHo, new ViewGroup.LayoutParams((int)(1000*widthRatio),(int)(heightRatio*2000)));

        final Basket sampleBasket = new Basket(this, 1, widthRatio, heightRatio); //initializes basket
        addContentView(sampleBasket, new ViewGroup.LayoutParams((int)(1000*widthRatio),(int)(heightRatio*2000)));

        //Log.d("value", String.valueOf(sampleFruit.getBallY()));


        ///////////////////// PROCESSING BASKET MOVEMENT /////////////////////////////////

        sampleBasket.setOnTouchListener(new View.OnTouchListener()
        {
            PointF DownPT = new PointF(); // Record Mouse Position When Pressed Down
            PointF StartPT = new PointF(); // Record Start Position of 'img'


            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                int eid = event.getAction();

                switch (eid)
                {
                    case MotionEvent.ACTION_MOVE :
                        // moving basket
                        PointF mv = new PointF( event.getX() - DownPT.x, event.getY() - DownPT.y);
                        sampleBasket.setX((int)(StartPT.x+mv.x));
                        //sampleBasket.setY((int)(StartPT.y+mv.y));
                        StartPT = new PointF( sampleBasket.getX(), sampleBasket.getY() );

                        //detects whether fruit touches basket
                        sampleFruit.setBasketPosition(sampleBasket.getX());
                        for(int i = 0; i < vegetable.size(); i++)
                        {
                            vegetable.get(i).setBasketPosition(sampleBasket.getX());
                        }
                        QuanHo.setBasketPosition(sampleBasket.getX());
                        sampleDragonFruit.setBasketPosition(sampleBasket.getX());

                        addFruit();
                        pointLabel.setText("Points: " + (totalPoints));
                        vegetableLabel.setText("Vegetables Left: " + (strikesLeft));
                        break;
                    case MotionEvent.ACTION_DOWN :
                        //moving basket
                        DownPT.x = event.getX();
                        //DownPT.y = event.getY();
                        StartPT = new PointF( sampleBasket.getX(), sampleBasket.getY() );

                        //detects whether fruit touches basket
                        sampleFruit.setBasketPosition(sampleBasket.getX()); //basket position with fruit
                        for(int i = 0; i < vegetable.size(); i++)
                        {
                            vegetable.get(i).setBasketPosition(sampleBasket.getX());
                        }
                        QuanHo.setBasketPosition(sampleBasket.getX());
                        sampleDragonFruit.setBasketPosition(sampleBasket.getX());

                        addFruit();
                        pointLabel.setText("Points: " + (totalPoints));
                        vegetableLabel.setText("Vegetables Left: " + (strikesLeft));
                        break;
                    case MotionEvent.ACTION_UP :
                        // Nothing have to do

                    default :

                }
                return true;
            }
        });
    }

    public void addFruit()
    {
        int pointInstance = totalPoints; //pointInstance keeps track of points before added
        totalPoints += fruit.get(0).addPoints();
        totalPoints += snake.get(0).addPoints();
        for (int i = 0; i < vegetable.size(); i++)
        {
            totalPoints += vegetable.get(i).addPoints();
        }
        totalPoints += dragonfruit.get(0).addPoints(); // after all points are added

        if(pointInstance != totalPoints) //touches anything in general
            pointIncrement ++;

        if(pointInstance == totalPoints + 1) //touches vegetable
            strikesLeft--;

        if(pointInstance == totalPoints + 99) //touches snake
        {
            strikesLeft = 0;
            totalPoints += 99;
        }


        if(strikesLeft == 0)
        {
            //myDB.insertData("5", "5");
            endGame();
        }


        if (pointIncrement >= 10) //ten touches and speed increments
        {
            pointIncrement = 0;
            fruit.get(0).increaseSpeed();
//            snake.get(0).increaseSpeed();
//            for (int i = 0; i < vegetable.size(); i++)
//            {
//                vegetable.get(i).increaseSpeed();
//            }
//            dragonfruit.get(0).increaseSpeed();
        }
    }

    public void endGame() //loads when game ends
    {
        Calendar date = Calendar.getInstance();
        final String currentDate = (date.get(Calendar.MONTH) + 1) + "/" + date.get(Calendar.DAY_OF_MONTH) + "/" + date.get(Calendar.YEAR);


        setContentView(com.game.spshe.smoothieclubgame.R.layout.endscreeen);
        Button start = (Button) findViewById(com.game.spshe.smoothieclubgame.R.id.restart);
        start.setY((int)(200*heightRatio));

        Button menuButton = (Button) findViewById(com.game.spshe.smoothieclubgame.R.id.mainmenu); //formatting restart button
        menuButton.setY((int)(500*heightRatio));

        TextView totalLabel = (TextView) findViewById(com.game.spshe.smoothieclubgame.R.id.totalLabel);
        start.setY((int)(800*heightRatio));
        totalLabel.setText("Your points: " + totalPoints);

        Button topFive = (Button) findViewById(com.game.spshe.smoothieclubgame.R.id.viewtopfive);
        topFive.setY((int)(1100*heightRatio));

        fruit.get(0).resetSpeed(); //resets initial speed

        start.setOnClickListener(new View.OnClickListener() { // restart button click
            @Override
            public void onClick(View view) {
                myDB.insertData(Integer.toString(totalPoints), currentDate);
                startGame();
            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() { //goes to main menu
            @Override
            public void onClick(View view) {
                myDB.insertData(Integer.toString(totalPoints), currentDate);
                mainMenu();
            }
        });

        topFive.setOnClickListener(new View.OnClickListener() { //goes to main menu
            @Override
            public void onClick(View view) {

                viewTopFive();
            }
        });
    }


}
