package com.nickmonks.trivia;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.snackbar.Snackbar;
import com.nickmonks.trivia.controller.AppController;
import com.nickmonks.trivia.data.AnswerListAsyncResponse;
import com.nickmonks.trivia.data.Repository;
import com.nickmonks.trivia.databinding.ActivityMainBinding;
import com.nickmonks.trivia.model.Question;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.material.snackbar.Snackbar.*;

public class MainActivity extends AppCompatActivity{

    // it concatenates the name of the class + Binding
    private ActivityMainBinding binding;
    private int currentQuestionIndex = 0; // keep track of n` of the queston
    private List<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

       questions = new Repository().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                // retrieve the question array from the Repository class
                // IMPORTANT: binding doesnt take the exact name we write
                binding.questionTextview.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
                updateCounter(questionArrayList);
            }
        });

        // we will force the mainactivitty to implemente onClick directly, so "this" is effectively a View
        // we will add a switch statement to choose if the clock was done by one or another button
        binding.trueButton.setOnClickListener(v -> {
            Log.d("DEBUG", "onCreate: TRUE CLICKED");
            checkAnswer(true);
            updateQuestion();
        });

        binding.falseButton.setOnClickListener(v -> {
            Log.d("DEBUG", "onCreate: FALSE CLICKED");
            checkAnswer(false);
            updateQuestion();

        });

        binding.nextButton.setOnClickListener(v -> {
            currentQuestionIndex = (currentQuestionIndex + 1) % questions.size();
            updateQuestion();
        });


    }

    private void checkAnswer(boolean userChose) {
        boolean answer = questions.get(currentQuestionIndex).isAnswerTrue();
        int snackMessageId = 0;

        if (userChose == answer) {
            snackMessageId = R.string.correct_answer;
            fadeAnimation();
            updateQuestion();
        } else {
            snackMessageId = R.string.incorrect;
            shakeAnimation();
        }
        Log.d("DEBUG", String.format("onCreate - checkAnswer: %d", snackMessageId));
        make(binding.cardView, snackMessageId, LENGTH_SHORT).show();
    }

    private void updateCounter(ArrayList<Question> questionArrayList) {
        binding.textViewOutOf.setText(String.format(getString(R.string.text_formated), currentQuestionIndex, questionArrayList.size()));
        Log.d("Main", "onCreate: " + questionArrayList);
    }

    private void updateQuestion() {
        String question = questions.get(currentQuestionIndex).getAnswer();
        binding.questionTextview.setText(question);
        updateCounter((ArrayList<Question>) questions);
    }
    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake_animation);
        binding.cardView.setAnimation(shake);

        // with the shake object, we can control what is happening surrounding that shake object!
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionTextview.setTextColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionTextview.setTextColor(Color.WHITE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void fadeAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(300);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        binding.cardView.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionTextview.setTextColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionTextview.setTextColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}