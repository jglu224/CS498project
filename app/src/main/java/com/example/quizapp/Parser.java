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

    final int NUM_STATES =  3;
    final int NUM_CC =      6;       // Number of character categories

    // Finite States
    final int FS_ERROR =    -1;
    final int FS_START =    0;
    final int FS_POTSCEC =  1;   // Potential Special Character
    final int FS_CODE =     2;

    // Character Categories
    final int CC_ALPHA =    0;
    final int CC_DIGIT =    1;
    final int CC_AMP =      2;       // Ampsersand
    final int CC_POUND =    3;
    final int CC_SECOL =    4;     // Semicolon
    final int CC_OTHER =    5;

    // Actions
    final int ACT_APPEND =  0;
    final int ACT_HOLD =    1;
    final int ACT_POP_APP = 2;      // Pop append
    final int ACT_POP_DIS = 3;      // Pop discard
    final int ACT_APP_NUM = 4;
    final int ACT_SPEC =    5;

    final int[][] FSM = {
            {FS_START,  FS_START,   FS_POTSCEC,   FS_START,   FS_START,   FS_START},
            {FS_START,  FS_START,   FS_START,     FS_CODE,    FS_START,   FS_START},
            {FS_START,  FS_CODE,    FS_START,     FS_START,   FS_START,   FS_START}
    };

    final int[][] ACTION = {
            {ACT_APPEND,    ACT_APPEND,     ACT_HOLD,       ACT_APPEND,     ACT_APPEND,     ACT_APPEND},
            {ACT_POP_APP,   ACT_POP_APP,    ACT_POP_APP,    ACT_HOLD,       ACT_POP_APP,    ACT_POP_APP},
            {ACT_POP_APP,   ACT_APP_NUM,    ACT_POP_APP,    ACT_POP_APP,    ACT_SPEC,       ACT_POP_APP}
    };

    Parser(String data) {

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

        int state = FS_START;
        int char_cat;
        int action;
        char current;

        String stripped = "";
        String buffer = "";
        int code = 0;

        for(int i = 0; i < data.length(); i++) {

            current = data.charAt(i);

            char_cat = get_cc(current);

            action = ACTION[state][char_cat];

            switch (action) {
                case ACT_APPEND:

                    stripped += current;

                    break;
                case ACT_HOLD:

                    buffer += current;

                    break;
                case ACT_POP_APP:

                    stripped.concat(buffer);
                    buffer = "";
                    code = 0;

                    break;
                case ACT_POP_DIS:

                    buffer = "";
                    code = 0;

                    break;
                case ACT_APP_NUM:

                    code = (code * 10) + getNumericValue(current);

                    break;
                case ACT_SPEC:

                    stripped += (char)code;

                    code = 0;

                    break;
            }

            state = FSM[state][char_cat];
        }

        return stripped;
    }

    private int get_cc(char c) {

        if(isLetter(c)) {
            return CC_ALPHA;
        }
        else if(isDigit(c)) {
            return CC_DIGIT;
        }
        else if(c == '&') {
            return CC_AMP;
        }
        else if(c == '#') {
            return CC_POUND;
        }
        else if(c == ';') {
            return CC_SECOL;
        }
        else {
            return CC_OTHER;
        }
    }
}








