package com.nickmonks.trivia.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.nickmonks.trivia.controller.AppController;
import com.nickmonks.trivia.model.Question;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    ArrayList<Question> questionArrayList = new ArrayList<>();
    String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    public List<Question> getQuestions(final AnswerListAsyncResponse callBack) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            Question question = new Question(response.getJSONArray(i).get(0).toString(),
                                                            response.getJSONArray(i).getBoolean(1));

                            // add questions to arrayList
                            questionArrayList.add(question);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    // invoke async method:
                    if (null != callBack){
                        callBack.processFinished(questionArrayList);
                    }


                }, error -> {
            Log.d("Error","Failed ");
        });

       AppController.getInstance().addRequestQueue(jsonArrayRequest);

        return questionArrayList;
    }
}
