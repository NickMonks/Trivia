package com.nickmonks.trivia.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    public static final String HIGHEST_SCORE = "highest_score";
    public static final String TRIVIA_STATE = "trivia_state";
    private SharedPreferences preferences;

    // for our case, we will pass an activity (since shared prefs needs to be bind to an activity or app context!)
    public Prefs(Activity context) {
        this.preferences = context.getPreferences(Context.MODE_PRIVATE);
    }

    public void saveHighestScore(int score) {
        int currentScore = score;
        int lastScore = preferences.getInt(HIGHEST_SCORE, 0);

        if (currentScore > lastScore){
            preferences.edit().putInt(HIGHEST_SCORE,currentScore).apply();
        }
    }

    public int getHighestScore(){
        return preferences.getInt(HIGHEST_SCORE, 0);
    }

    public void seState(int index) {
        // save question index for next state
        preferences.edit().putInt(TRIVIA_STATE, index).apply();
    }

    public int getState() {
        return preferences.getInt(TRIVIA_STATE,0);

    }
}
