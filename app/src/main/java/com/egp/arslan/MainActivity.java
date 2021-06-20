package com.egp.arslan;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int score;
    int bestScore;

    int healthMy;
    int healthEnemy;
    int damageMy;
    int damageEnemy;

    Random rand;

    ImageView pictureMy;
    ImageView pictureEnemy;

    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;

    boolean blockEnemy = false;

    Button bite;
    Button block;

    TextView tScore;
    TextView tBestScore;
    TextView myHealth;
    TextView enemyHealth;

    TextView textView;
    TextView textView2;
    TextView textView3;
    TextView textView4;

    Button play;
    TextView textView0;

    void start(){
        visible();
        tScore.setText(String.valueOf(score));

        healthMy+=10;
        int num = rand.nextInt(10) + 1;
        if (num<6) {
            pictureEnemy.setImageResource(R.drawable.enemy);
            healthEnemy = 30;
            damageEnemy = 5;
        } else if (num<10) {
            pictureEnemy.setImageResource(R.drawable.enemybig);
            healthEnemy = 60;
            damageEnemy = 10;
        } else {
            pictureEnemy.setImageResource(R.drawable.enemybts);
            healthEnemy = 800;
            damageEnemy = 1;
        }
        myHealth.setText(String.valueOf(healthMy));
        enemyHealth.setText(String.valueOf(healthEnemy));

    }

    void visible(){
        pictureMy.setVisibility(View.VISIBLE);
        pictureEnemy.setVisibility(View.VISIBLE);
        bite.setVisibility(View.VISIBLE);
        block.setVisibility(View.VISIBLE);
        tScore.setVisibility(View.VISIBLE);
        myHealth.setVisibility(View.VISIBLE);
        enemyHealth.setVisibility(View.VISIBLE);
        textView2.setVisibility(View.VISIBLE);
        textView3.setVisibility(View.VISIBLE);
        textView4.setVisibility(View.VISIBLE);
    }
    void invisible(){
        pictureMy.setVisibility(View.INVISIBLE);
        pictureEnemy.setVisibility(View.INVISIBLE);
        bite.setVisibility(View.INVISIBLE);
        block.setVisibility(View.INVISIBLE);
        tScore.setVisibility(View.INVISIBLE);
        myHealth.setVisibility(View.INVISIBLE);
        enemyHealth.setVisibility(View.INVISIBLE);
        textView2.setVisibility(View.INVISIBLE);
        textView3.setVisibility(View.INVISIBLE);
        textView4.setVisibility(View.INVISIBLE);
    }

    void check(){
        // Ничья {} Победа {} Поражение {}
        if (healthMy<1&healthEnemy<1){
            invisible();
            textView0.setText("Вау, ничья. Вы оба в отключке.\nКонец игры");
            play.setVisibility(View.VISIBLE);
            textView0.setVisibility(View.VISIBLE);
        } else if (healthEnemy<1){
            invisible();
            score++;
            if (bestScore<score){
                bestScore = score;
                tBestScore.setText(String.valueOf(bestScore));
                dbHelper.updateValue(sqLiteDatabase,bestScore);
            }
            start();
        } else if (healthMy<1){
            invisible();
            textView0.setText("Ты повержен.\nКонец игры");
            play.setVisibility(View.VISIBLE);
            textView0.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();

        rand = new Random();

        bestScore = dbHelper.getValue(sqLiteDatabase);
            // Соединение переменных и активности
        {
            pictureMy = findViewById(R.id.imageView);
            pictureEnemy = findViewById(R.id.imageView2);

            bite = findViewById(R.id.bite);
            block = findViewById(R.id.block);
            tScore = findViewById(R.id.score);
            tBestScore = findViewById(R.id.bestScore);
            myHealth = findViewById(R.id.myHealth);
            enemyHealth = findViewById(R.id.enemyHealth);

            play = findViewById(R.id.play);
            textView0 = findViewById(R.id.textView3);

            textView = findViewById(R.id.textView);
            textView2 = findViewById(R.id.textView2);
            textView3 = findViewById(R.id.textView7);
            textView4 = findViewById(R.id.textView10);
        }

        tBestScore.setText(String.valueOf(bestScore));
        healthMy = 70;
        damageMy = 6;
        invisible();

        bite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blockEnemy = false;
                int en = rand.nextInt(3)+1;
                switch (en) {
                    case 1: blockEnemy=true;
                        break;
                    case 2: healthMy-=damageEnemy;
                        break;
                    case 3:
                        break;
                    default:
                        break;
                }
                if (!blockEnemy) {
                    healthEnemy-= damageMy;
                }
                myHealth.setText(String.valueOf(healthMy));
                enemyHealth.setText(String.valueOf(healthEnemy));
                check();


            }
        });

        block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blockEnemy = false;
                int en = rand.nextInt(3)+1;
                switch (en) {
                    case 1: blockEnemy=true;
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    default:
                        break;
                }
                myHealth.setText(String.valueOf(healthMy));
                enemyHealth.setText(String.valueOf(healthEnemy));
                check();

            }
        });





        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play.setVisibility(View.INVISIBLE);
                textView0.setVisibility(View.INVISIBLE);
                healthMy = 70;
                score = 0;
                start();
            }
        });

    }

}