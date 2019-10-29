package com.example.tictactoe_v4;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /*
        - initialize the Used Views (by Id)
        - set on clickListener for each ImageButton(From which the board is composed)
        - set on clickListener for the two[2] reset Buttons(From which the board is composed)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        configurePlayButton();
    }


    private void configurePlayButton(){
        Button buttonPlayGame = (Button) findViewById(R.id.buttonPlayGame);
        buttonPlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,BoardGameActivity.class);
                startActivity(i);
            }
        });
    }


    @Override
    public void onClick(View v) {

    }
}



