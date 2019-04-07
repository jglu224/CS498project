package com.example.quizapp;

import java.util.*;
import java.lang.String;
import org.json.*;
import android.util.Log;

import static java.lang.Character.getNumericValue;
import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

public class Parser {

    String data;
    JSONObject obj;

    final int FS_REG = 0;
    final int FS_CODE = 1;

    Map<String, Character> map = new HashMap<>();

    Parser(String data) {

        map.put("quot", '\"');

        Log.d(getClass().getName(), data);

        try {
            this.data = remove_html_special_chars(data);
            this.obj = new JSONObject(this.data);
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
    }

    private String remove_html_special_chars(String data) {

        String code = "";
        int state = FS_REG;

        char current;

        String stripped = "";

        for(int i = 0; i < data.length(); i++) {

            current = data.charAt(i);

            if(current == '&' && state == FS_REG) {
                state = FS_CODE;
            }
            else if(current == ';' && state == FS_CODE) {
                state = FS_REG;
                stripped += decipher_code(code);
                code = "";
            }
            else if(state == FS_REG) {
                stripped += current;
            }
            else if(state == FS_CODE && (isDigit(current) || isLetter(current) || (current == '#'))) {
                code += current;
            }
        }

        return stripped;
    }

    private char decipher_code(String code) {

        if(code.charAt(0) == '#') {
            code = code.substring(1, code.length());
            int integer_code = Integer.parseInt(code);
            return (char)integer_code;
        }
        else {
            if(map.containsKey(code)) {
                return map.get(code);
            }
            else {
                return ' ';
            }
        }
    }
}








