package com.example.quizapp;

import java.util.*;
import java.lang.String;
import org.json.*;
import android.util.Log;

public class Parser {

    String data;
    JSONObject obj;

    Parser(String data) {

        try {
            this.data = data;
            this.obj = new JSONObject(data);
        }
        catch (JSONException e) {
            Log.e(null, "JSON ERROR.", e);
        }

    }

    public ArrayList<Question> generate_questions() {

        try {
            ArrayList<Question> questions = new ArrayList<Question>();
            Question current_question = null;

            JSONArray json_questions = this.obj.getJSONArray("results");

            Log.d(getClass().getName(), "JSON Array Size = " + json_questions.length());

            for (int i = 0; i < json_questions.length(); i++) {

                JSONObject current_obj = json_questions.getJSONObject(i);

                ArrayList<String> incorrect_questions = new ArrayList<String>();
                JSONArray json_inquestions = current_obj.getJSONArray("incorrect_answers");

                for (int j = 0; j < json_inquestions.length(); j++) {
                    incorrect_questions.add(json_inquestions.getString(j));
                }

                current_question = new Question(current_obj.getString("question"),
                        current_obj.getString("category"),
                        current_obj.getString("type"),
                        current_obj.getString("difficulty"),
                        current_obj.getString("correct_answer"),
                        incorrect_questions);

                questions.add(current_question);
            }

            return(questions);
        }
        catch (JSONException e) {
            Log.e(null, "JSON ERROR.", e);
            return null;
        }

        return null;
    }
}