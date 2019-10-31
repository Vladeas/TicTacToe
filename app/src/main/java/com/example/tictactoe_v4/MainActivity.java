package com.example.tictactoe_v4;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /*
        - hide action bar
        - set screen orientation to Portrait
        - call the necessary functions
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        configurePlayButton();
    }


    /*
        - use setOnClickListener for the play button
        - if pressed call the appropriate functions
     */
    public void configurePlayButton(){
        Button buttonPlayGame = (Button) findViewById(R.id.buttonPlayGame);
        buttonPlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configureTwoOutThreeButton();
                configureThreeOutFiveButton();
                configureJustPlayButton();
            }
        });
    }

    private void configureTwoOutThreeButton(){
        final Button buttonPlayBest3 = (Button) findViewById(R.id.buttonPlayBest3);
        buttonVisibility(buttonPlayBest3);

        buttonPlayBest3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,BoardGameActivity.class);
                i.putExtra("Extra_Game_Type",buttonPlayBest3.getTag().toString());
                startActivity(i);
            }
        });
    }

    private void configureThreeOutFiveButton(){
        final Button buttonPlayBest5 = (Button) findViewById(R.id.buttonPlayBest5);
        buttonVisibility(buttonPlayBest5);


        buttonPlayBest5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,BoardGameActivity.class);
                i.putExtra("Extra_Game_Type",buttonPlayBest5.getTag().toString());
                startActivity(i);
            }
        });
    }

    private void configureJustPlayButton(){
        final Button buttonJustPlay = (Button) findViewById(R.id.buttonJustPlay);
        buttonVisibility(buttonJustPlay);


        buttonJustPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,BoardGameActivity.class);
                i.putExtra("Extra_Game_Type",buttonJustPlay.getTag().toString());
                startActivity(i);
            }
        });
    }

    private void buttonVisibility(View v){
        if(v.getVisibility() == View.VISIBLE) {
            v.setVisibility(View.INVISIBLE);
        }else{
            v.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {

    }
}



