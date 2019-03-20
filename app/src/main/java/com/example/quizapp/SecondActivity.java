package com.example.quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    //MyApplication application = (MyApplication)getApplication();
    //String json_data = application.getJSON();

    int current_question = 0;

    Bundle extras;

    String json_data;

    Parser parser;

    ArrayList<Question> questions;

    Boolean has_answered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        extras = getIntent().getExtras();

        if(extras != null) {
            json_data = extras.getString("json_data");
            parser = new Parser(json_data);
        }
        else
        {
            finish();
        }

        questions = parser.generate_questions();
        Log.d(getClass().getName(), "Number of Questions = " + questions.size());

        update_question(current_question);
    }

    public void onRadioButtonClicked(View view) {


        if(!has_answered) {

            //has_answered = true;

            RadioGroup radio_group = (RadioGroup) findViewById(R.id.radio_group);

            RadioButton button = (RadioButton) view;

            button.setChecked(false);

            String answer = button.getText().toString();

            if (questions.get(current_question).is_correct(answer)) {
                button.setTextColor(Color.parseColor("#00BB00"));
            } else {

                button.setTextColor(Color.parseColor("#BB0000"));

                String correct_answer = questions.get(current_question).get_correct_answer();

                for (int i = 0; i < radio_group.getChildCount(); i++) {
                    if (((RadioButton) radio_group.getChildAt(i)).getText() == questions.get(current_question).get_correct_answer()) {
                        ((RadioButton) radio_group.getChildAt(i)).setTextColor(Color.parseColor("#00BB00"));
                    }
                }


            }


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {

                    RadioGroup radio_group = (RadioGroup) findViewById(R.id.radio_group);

                    current_question += 1;

                    for (int i = 0; i < radio_group.getChildCount(); i++) {
                        ((RadioButton) radio_group.getChildAt(i)).setTextColor(Color.parseColor("#000000"));
                    }

                    update_question(current_question);

                    has_answered = false;
                }
            }, 1000);

                    }
    }

    private void update_question(int number) {

        if(number < questions.size()) {

            TextView question_displayed = (TextView)findViewById(R.id.question);
            RadioGroup radio_group = (RadioGroup) findViewById(R.id.radio_group);

            question_displayed.setText(this.questions.get(number).get_question_text());

            ArrayList<String> answers = questions.get(number).get_scrambled_answers();

            for (int i = 0; i < radio_group.getChildCount(); i++) {


                ((RadioButton) radio_group.getChildAt(i)).setText(answers.get(i));
            }
        }
    }
}
