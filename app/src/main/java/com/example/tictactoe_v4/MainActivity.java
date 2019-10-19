package com.example.tictactoe_v4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int boardLength = 3;
    private int roundCount, playerOnePoints, playerTwoPoints;
    private boolean playerOneTurn = true;
    private TextView textViewPlayerOne;
    private TextView textViewPlayerTwo;
    private ImageButton[][] imageButtons =new ImageButton[boardLength][boardLength];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayerOne = findViewById(R.id.textViewPlayerOne);
        textViewPlayerTwo = findViewById(R.id.textViewPlayerTwo);

        for(int i = 0; i < 3;i++){
            for(int j = 0; j < 3;j++){
                String imageButtonID = "imageButton" + i + j;
                int resID = getResources().getIdentifier(imageButtonID,"id",getPackageName());
                imageButtons[i][j] = findViewById(resID);
                imageButtons[i][j].setOnClickListener(this);
            }
        }

        Button buttonResetGame = findViewById(R.id.buttonResetGame);
        buttonResetGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                resetGameButtonPress();
            }
        });

        Button buttonResetBoard = findViewById(R.id.buttonResetBoard);
        buttonResetBoard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                resetBoardGameEnd();
            }
        });
    }

    public void onClick(View v) {
        if(!((ImageButton) v).getTag().toString().equals("")){
            return;
        }
        if(playerOneTurn){
            ((ImageButton) v).setBackgroundResource(R.drawable.red_x);
            ((ImageButton) v).setTag("x");
        } else{
            ((ImageButton) v).setBackgroundResource(R.drawable.blue_o);
            ((ImageButton) v).setTag("o");
        }

        roundCount++;

        if(checkForWin()){
            if(playerOneTurn){
                playerOneWins();
            } else {
                playerTwoWins();
            }
        }else if (roundCount == 9){
            draw();
        }else{
            playerOneTurn = !playerOneTurn;
        }
    }

    public boolean checkForWin(){
        String[][] field = new String[boardLength][boardLength];

        for (int i = 0; i < boardLength;i++){
            for(int j = 0 ; j < boardLength;j++){
                field[i][j] = imageButtons[i][j].getTag().toString();
            }
        }

        if ( checkForLineWin(field) && checkForColumnWin(field) && checkForPrimaryDiagonalWin(field) && checkForSecondaryDiagonalWin(field)){
            return true;
        }
        return false;
    }

    /*
    check for
     */
    private boolean checkForLineWin(String[][] field){
        for (int i = 0; i < boardLength;i++){
            if(field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")){
                return true;
            }
        }
        return false;
    }

    private boolean checkForColumnWin(String[][] field){
        for (int i = 0; i < 3;i++){
            if(field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")){
                return true;
            }
        }
        return false;
    }

    private boolean checkForPrimaryDiagonalWin(String[][] field) {
        if(field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")){
            return true;
        }
        return false;
    }

    private boolean checkForSecondaryDiagonalWin(String[][] field) {
        if(field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")){
            return true;
        }
        return false;
    }

    private void playerOneWins(){
        playerOnePoints++;
        Toast.makeText(this,"Player 1 wins!",Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoardGameEnd();
    }

    private void playerTwoWins(){
        playerTwoPoints++;
        Toast.makeText(this,"Player 2 wins!",Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoardGameEnd();
    }

    private void draw(){
        Toast.makeText(this,"Draw!",Toast.LENGTH_SHORT).show();
        resetBoardGameEnd();
    }

    private void updatePointsText(){
        textViewPlayerOne.setText("Player 1\n" + playerOnePoints);
        textViewPlayerTwo.setText("Player 2\n" + playerTwoPoints);
    }

    private void resetBoardGameEnd(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                imageButtons[i][j].setTag("");
                imageButtons[i][j].setBackgroundColor(Color.parseColor("#C0C0C0"));
            }
        }
        roundCount = 0;
        playerOneTurn = true;
    }

    private void resetGameButtonPress(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                imageButtons[i][j].setTag("");
                imageButtons[i][j].setBackgroundColor(Color.parseColor("#C0C0C0"));
            }
        }
        roundCount = 0;
        playerOneTurn = true;
        playerOnePoints = 0;
        playerTwoPoints = 0;
        updatePointsText();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount", roundCount);
        outState.putInt("playerOnePoints",playerOnePoints);
        outState.putInt("playerTwoPoints",playerTwoPoints);
        outState.putBoolean("playerOneTurn",playerOneTurn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        playerOnePoints = savedInstanceState.getInt("playerOnePoints");
        playerTwoPoints = savedInstanceState.getInt("playerTwoPoints");
        playerOneTurn = savedInstanceState.getBoolean("playerOneTurn");
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
