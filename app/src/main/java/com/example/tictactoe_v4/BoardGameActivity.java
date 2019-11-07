package com.example.tictactoe_v4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;

public class BoardGameActivity extends AppCompatActivity implements View.OnClickListener {

    private int boardLength = 3;
    private int moveCount, roundCountPlayerOne, roundCountPlayerTwo,playerOnePoints, playerTwoPoints;
    private boolean playerOneTurn = true;
    private TextView textViewPlayerOne;
    private TextView textViewPlayerTwo;
    private ImageButton[][] imageButtons =new ImageButton[boardLength][boardLength];
    boolean popUpVisible = false;

    /*
        - initialize the Used Views (by Id)
        - set on clickListener for each ImageButton(From which the board is composed)
        - set on clickListener for the two[2] reset Buttons(From which the board is composed)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_board_game);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        //View decorView = getWindow().getDecorView();
        // Hide the status bar.
        //int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        //decorView.setSystemUiVisibility(uiOptions);


        textViewPlayerOne = findViewById(R.id.textViewPlayerOne);
        textViewPlayerTwo = findViewById(R.id.textViewPlayerTwo);

        for(int i = 0; i < 3;i++){
            for(int j = 0; j < 3;j++){
                String imageButtonID = "imageButton" + i + j;
                int resourceID = getResources().getIdentifier(imageButtonID,"id",getPackageName());
                imageButtons[i][j] = findViewById(resourceID);
                imageButtons[i][j].setOnClickListener(this);
            }
        }

        buttonsConfiguration();
    }


    /*
        - change the tag and background of the given View v (detected by the onClickListener) with
        the according players turn
        - count the number of rounds
        - call the check for win method
     */
    public void onClick(View v) {
        if(!popUpVisible) {
            if (!((ImageButton) v).getTag().toString().equals("")) {
                return;
            }
            if (playerOneTurn) {
                ((ImageButton) v).setBackgroundResource(R.drawable.roman_helmet_512px_white);
                ((ImageButton) v).setTag("x");
            } else {
                ((ImageButton) v).setBackgroundResource(R.drawable.viking_helmet_512px_grey);
                ((ImageButton) v).setTag("o");
            }

            moveCount++;

            winConditionsConfiguration();
        }
    }


    /*
        - check all win conditions and call the appropriate functions
     */
    private void winConditionsConfiguration(){
        if (checkForWin()) {
            if (playerOneTurn) {
                playerOneWins();
                if(!gameEnd()) {
                    resetBoardGame();
                }
            } else {
                playerTwoWins();
                if(!gameEnd()) {
                    resetBoardGame();
                }
            }
        } else if (moveCount == 9) {
            draw();
        } else {
            playerOneTurn = !playerOneTurn;
        }
        gameEnd();
    }


    /*
        - for "Best of ..." games, when the limit is reached a pop up screen will appear
        - turn the pop up layout to visible
        - update the message for the winning player (use Math.ceil() for approximation)
        - get the type of match from the previous activity with getIntent().getExtras();
     */
    private boolean gameEnd(){
        Double gameLength;
        Bundle extras = getIntent().getExtras();
        RelativeLayout linearLayoutEndGamePopUp = (RelativeLayout)findViewById(R.id.linearLayoutEndGamePopUp);
        if (extras != null) {
            gameLength = Double.parseDouble(extras.getString("Extra_Game_Type"));
            //The key argument here must match that used in the other activity
            if(gameLength != 0) {
                if (roundCountPlayerOne >= Math.ceil(gameLength / 2.0)) {
                    popUpVisible = true;
                    linearLayoutEndGamePopUp.setVisibility(View.VISIBLE);
                    playerEndGameWin(1);
                    return true;
                } else if (roundCountPlayerTwo >= Math.ceil(gameLength / 2.0)) {
                    popUpVisible = true;
                    linearLayoutEndGamePopUp.setVisibility(View.VISIBLE);
                    playerEndGameWin(2);
                    return true;
                }
            }
        }
        return false;

    }


    /*
        - update the end game pop up screen for the winning player
     */
    private void playerEndGameWin(int player){
        TextView textViewEndGame = (TextView) findViewById(R.id.textViewEndGame);
        textViewEndGame.setText("Player " + player + " won!");
    }


    /*
        - transfer the image View matrix data to a new String matrix
        - return true if at least one victory condition is met
     */
    public boolean checkForWin(){
        String[][] field = new String[boardLength][boardLength];

        for (int i = 0; i < boardLength;i++){
            for(int j = 0 ; j < boardLength;j++){
                field[i][j] = imageButtons[i][j].getTag().toString();
            }
        }

        if ( checkForLineWin(field) || checkForColumnWin(field) || checkForPrimaryDiagonalWin(field) || checkForSecondaryDiagonalWin(field)){
            return true;
        }
        return false;
    }


    /*
        Victory conditions on every line for a 3 by 3 matrix
            - given the matrix as a string
            - return true if the three[3] values of the line are the same
            - checks if the string is different from the initial one("")
     */
    private boolean checkForLineWin(String[][] field){
        for (int i = 0; i < boardLength;i++){
            if(field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")){
                return true;
            }
        }
        return false;
    }


    /*
        Victory conditions on every column for a 3 by 3 matrix
            - given the matrix as a string
            - return true if the three[3] values of the column are the same
            - checks if the string is different from the initial one("")
     */
    private boolean checkForColumnWin(String[][] field){
        for (int i = 0; i < 3;i++){
            if(field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")){
                return true;
            }
        }
        return false;
    }


    /*
        Victory conditions on the primary diagonal for a 3 by 3 matrix
            - given the matrix as a string
            - return true if the three[3] values are the same
            - checks if the string is different from the initial one("")
     */
    private boolean checkForPrimaryDiagonalWin(String[][] field) {
        if(field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")){
            return true;
        }
        return false;
    }


    /*
        Victory conditions on the secondary diagonal for a 3 by 3 matrix
            - given the matrix as a string
            - return true if the three[3] values are the same
            - checks if the string is different from the initial one("")
     */
    private boolean checkForSecondaryDiagonalWin(String[][] field) {
        if(field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")){
            return true;
        }
        return false;
    }


    // Toast for when the match is won by player two[1]
    private void playerOneWins(){
        playerOnePoints++;
        Toast.makeText(this,"Player 1 wins!",Toast.LENGTH_SHORT).show();
        updatePointsText();
        //resetBoardGameEnd();
        roundCountPlayerOne++;
    }


    // Toast for when the match is won by player two[2]
    private void playerTwoWins(){
        playerTwoPoints++;
        Toast.makeText(this,"Player 2 wins!",Toast.LENGTH_SHORT).show();
        updatePointsText();
        //resetBoardGameEnd();
        roundCountPlayerTwo++;
    }


    // Toast for when the match is a draw
    private void draw(){
        Toast.makeText(this,"Draw!",Toast.LENGTH_SHORT).show();
        resetBoardGame();
    }


    /*
        - Update the text views with the current score for each player
     */
    private void updatePointsText(){
        textViewPlayerOne.setText("Player 1\n" + playerOnePoints);
        textViewPlayerTwo.setText("Player 2\n" + playerTwoPoints);
    }


    /*
        - add an onClickListener for all the available buttons on this activity
        - call the check pop up method for each button with its specific id
     */
    private void buttonsConfiguration(){
        Button buttonMainMenu = findViewById(R.id.buttonMainMenu);
        buttonMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForPopUp("buttonMainMenu");
            }
        });

        Button buttonGoMainMenuEnd = findViewById(R.id.buttonGoMainMenuEnd);
        buttonGoMainMenuEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainMenu();
            }
        });

        Button buttonResetGame = findViewById(R.id.buttonResetGame);
        buttonResetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForPopUp("buttonResetGame");
            }
        });

        Button buttonResetBoard = findViewById(R.id.buttonResetBoard);
        buttonResetBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForPopUp("buttonResetBoard");
            }
        });

        Button buttonPlayAgainEnd = findViewById(R.id.buttonPlayAgainEnd);
        buttonPlayAgainEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgainMatch();
            }
        });
    }


    /*
        - check if the end game pop up is VISIBLE on the screen,
        if it is block all the other buttons, except the pop up ones
        - use switch case for simplicity
     */
    private void checkForPopUp(String command){
        if(!popUpVisible){
            switch (command){
                case "buttonResetGame":
                    resetGameButtonPress();
                    break;
                case "buttonResetBoard":
                    resetBoardGame();
                    break;
                case "buttonMainMenu":
                    goToMainMenu();
                    break;
            }
        }
    }


    /*
        - call the reset game function
        - It also makes the end game layout invisible
     */
    private void playAgainMatch(){
        resetGameButtonPress();
        RelativeLayout linearLayoutEndGamePopUp = (RelativeLayout)findViewById(R.id.linearLayoutEndGamePopUp);
        linearLayoutEndGamePopUp.setVisibility(View.INVISIBLE);
    }


    /*
        - end the instance of this activity
     */
    private void goToMainMenu(){
        finish();
    }


    /*
        - Reset the game board, return tags and colour to initial values
        - the round counter and player turn are also reset
     */
    private void resetBoardGame(){
        if(!popUpVisible) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    imageButtons[i][j].setTag("");
                    imageButtons[i][j].setBackgroundResource(R.drawable.fort_512px_white);
                }
            }
            moveCount = 0;
            playerOneTurn = true;
        }
    }


    /*
        - Reset the entire app, by returning all variables at the initial value
     */
    private void resetGameButtonPress(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                imageButtons[i][j].setTag("");
                imageButtons[i][j].setBackgroundResource(R.drawable.fort_512px_white);
            }
        }
        popUpVisible = false;
        moveCount = 0;
        roundCountPlayerOne = 0;
        roundCountPlayerTwo = 0;
        playerOneTurn = true;
        playerOnePoints = 0;
        playerTwoPoints = 0;
        updatePointsText();
    }


    /*
        - save the values of the given instance[Ex. in case of screen rotating the values are not lost]
        - works with onRestoreInstanceState
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("moveCount", moveCount);
        outState.putInt("playerOnePoints",playerOnePoints);
        outState.putInt("playerTwoPoints",playerTwoPoints);
        outState.putBoolean("playerOneTurn",playerOneTurn);
    }


    /*
        - transfer the saved values in the new instance[Ex. in case of screen rotating the values are not lost]
        - works with onSaveInstanceState
     */
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        moveCount = savedInstanceState.getInt("moveCount");
        playerOnePoints = savedInstanceState.getInt("playerOnePoints");
        playerTwoPoints = savedInstanceState.getInt("playerTwoPoints");
        playerOneTurn = savedInstanceState.getBoolean("playerOneTurn");
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
