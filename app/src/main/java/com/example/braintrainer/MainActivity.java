package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button goButton;
    Button tryAgainButton;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    TextView answerTextView;
    TextView timeTextView;
    TextView taskTextView;
    TextView scoreTextView;
    TextView finalScoreTextView;

    ConstraintLayout gameLayout;
    ConstraintLayout endGameLayout;

    CountDownTimer countDownTimer;
    long totalGameTime = 30;

    int randomPlaceForCorrectAnswer;
    int correctCount = 0;
    int countOfTasks = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton = findViewById(R.id.goButton);
        timeTextView = findViewById(R.id.timeTextView);
        taskTextView = findViewById(R.id.taskTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        answerTextView = findViewById(R.id.answerTextView);
        tryAgainButton = findViewById(R.id.tryAgainButton);
        finalScoreTextView = findViewById(R.id.finalScoreTextView);
        gameLayout = findViewById(R.id.gameLayout);
        endGameLayout = findViewById(R.id.endGameLayout);

        goButton.setVisibility(View.VISIBLE);
        gameLayout.setVisibility(View.INVISIBLE);
        endGameLayout.setVisibility(View.INVISIBLE);
    }

    public void tryAgain(View view){
        endGameLayout.setVisibility(View.INVISIBLE);
        countOfTasks = 0;
        correctCount = 0;
        timeTextView.setText(String.valueOf(totalGameTime));
        scoreTextView.setText("0/0");
        startTheGame(view);
    }

    public void startTheGame(View view){
        goButton.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);
        answerTextView.setText("");
        loadNewTask();
        countDownTimer = new CountDownTimer(totalGameTime* 1000 + 100, 1000) {
            @Override
            public void onTick(long l) {
                timeTextView.setText(String.valueOf(l/1000) + "s");
            }

            @Override
            public void onFinish() {
                gameLayout.setVisibility(View.INVISIBLE);
                endGameLayout.setVisibility(View.VISIBLE);
                finalScoreTextView.setText("Score: " + correctCount + "/" + countOfTasks);
            }
        }.start();

    }

    public void loadNewTask(){
        ArrayList<Integer> answers = new ArrayList<Integer>();
        final int min = 1;
        final int max = 50;
        final int randomX = new Random().nextInt((max - min) + 1) + min;
        final int randomY = new Random().nextInt((max - min) + 1) + min;
        int correctAnswer = randomX + randomY;
        taskTextView.setText(randomX + " + " + randomY);

        // generate answer options
        randomPlaceForCorrectAnswer = new Random().nextInt(3);
        Log.i("Place", Integer.toString(randomPlaceForCorrectAnswer));
        for(int i=0; i<4; i++){
            if(i == randomPlaceForCorrectAnswer){
                answers.add(correctAnswer);
            } else{
                int randomAnswer = new Random().nextInt(100);
                while(correctAnswer == randomAnswer){
                    randomAnswer = new Random().nextInt(100);
                }
                answers.add(randomAnswer);
            }
        }
        button1.setText(answers.get(0).toString());
        button2.setText(answers.get(1).toString());
        button3.setText(answers.get(2).toString());
        button4.setText(answers.get(3).toString());
    }

    public void buttonClicked(View view){
        countOfTasks++;
        String tag = view.getTag().toString();
        if(Integer.toString(randomPlaceForCorrectAnswer).equals(tag)){
            correctCount++;
            answerTextView.setText("CORRECT!");
            scoreTextView.setText(correctCount + "/" + countOfTasks);
            loadNewTask();
        } else{
            answerTextView.setText("WRONG!");
            scoreTextView.setText(correctCount + "/" + countOfTasks);
            loadNewTask();
        }
    }

    public void displayConstraints(boolean isActive){
        int visibility;
        if(isActive) {
            visibility = View.VISIBLE;
        }else {
            visibility = View.INVISIBLE;
        }
        gameLayout.setVisibility(visibility);

    }
}