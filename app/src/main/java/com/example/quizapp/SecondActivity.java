package com.example.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    //MyApplication application = (MyApplication)getApplication();
    //String json_data = application.getJSON();

    Bundle extras;

    String json_data;

    Parser parser;

    ArrayList<Question> questions;

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

        update_question(0);
    }

    public void onRadioButtonClicked(View view) {


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
