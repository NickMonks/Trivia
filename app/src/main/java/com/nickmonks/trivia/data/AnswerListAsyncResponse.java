package com.nickmonks.trivia.data;

import com.nickmonks.trivia.model.Question;

import java.util.ArrayList;

// This interface is created to allow async communication in our app.
// by default, Android prevents methods that fetch to the back-end to be used as synchronous
public interface AnswerListAsyncResponse {
    void processFinished(ArrayList<Question> questionArrayList);
}
