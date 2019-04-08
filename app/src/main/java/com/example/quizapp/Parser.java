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

    Boolean JSON_Failure = false;

    Parser(String data) {

        populate_char_map();

        make_json_object(data);

    }

    Parser() {
        populate_char_map();
    }

    private void populate_char_map() {
        map.put("quot", '\"');
        map.put("rsquo", '\'');
        map.put("shy", ' ');
        map.put("amp", '&');
        map.put("ldquo", '\"');
        map.put("hellip", '…');
        map.put("rdquo", '\"');
        map.put("ocirc", 'ô');
        map.put("iacute", 'í');
        map.put("oacute", 'ó');
        map.put("Eacute", 'É');
        map.put("aring", 'å');
        map.put("auml", 'ä');
        map.put("Auml", 'Ä');
        map.put("ouml", 'ö');
        map.put("Ouml", 'Ö');
        map.put("uuml", 'ü');
        map.put("Uuml", 'Ü');
    }

    public void make_json_object(String data) {

        this.data = data;

        Log.d(getClass().getName(), data);

        try {
            Log.d(getClass().getName(), "JSON String: " + this.data);
            this.obj = new JSONObject(this.data);

            JSON_Failure = (this.obj.getInt("response_code") != 0);
        }
        catch (JSONException e) {
            Log.e(null, "PARSER CREATION: JSON ERROR. ", e);
        }

        if(JSON_Failure)
            Log.d(getClass().getName(), "WARNING: JSON Object not created correctly!");
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
                    incorrect_questions.add(remove_html_special_chars(json_inquestions.getString(j)));
                }

                current_question = new Question(remove_html_special_chars(current_obj.getString("question")),
                        remove_html_special_chars(current_obj.getString("category")),
                        remove_html_special_chars(current_obj.getString("type")),
                        remove_html_special_chars(current_obj.getString("difficulty")),
                        remove_html_special_chars(current_obj.getString("correct_answer")),
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

    public Boolean json_failure() {
        return JSON_Failure;
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
                Log.d(getClass().getName(), "Unknown character code encountered: " + code);
                return '?';
            }
        }
    }
}








