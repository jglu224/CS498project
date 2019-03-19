package com.example.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    String json_data = "{\"response_code\":0,\"results\":[{\"category\":\"Science & Nature\",\"type\":\"multiple\",\"difficulty\":\"medium\",\"question\":\"The moons, Miranda, Ariel, Umbriel, Titania and Oberon orbit which planet?\",\"correct_answer\":\"Uranus\",\"incorrect_answers\":[\"Jupiter\",\"Venus\",\"Neptune\"]},{\"category\":\"Entertainment: Books\",\"type\":\"multiple\",\"difficulty\":\"hard\",\"question\":\"In the Harry Potter universe, what is Cornelius Fudge&#039;s middle name?\",\"correct_answer\":\"Oswald\",\"incorrect_answers\":[\"James\",\"Harold\",\"Christopher\"]},{\"category\":\"Entertainment: Video Games\",\"type\":\"boolean\",\"difficulty\":\"easy\",\"question\":\"In &quot;Mario Kart 64&quot;, Waluigi is a playable character.\",\"correct_answer\":\"False\",\"incorrect_answers\":[\"True\"]},{\"category\":\"Entertainment: Music\",\"type\":\"multiple\",\"difficulty\":\"easy\",\"question\":\"Which artist released the 2012 single &quot;Harlem Shake&quot;, which was used in numerous YouTube videos in 2013?\",\"correct_answer\":\"Baauer\",\"incorrect_answers\":[\"RL Grime\",\"NGHTMRE\",\"Flosstradamus\"]},{\"category\":\"History\",\"type\":\"multiple\",\"difficulty\":\"medium\",\"question\":\"How many times was Albert Einstein married in his lifetime?\",\"correct_answer\":\"Twice\",\"incorrect_answers\":[\"Five\",\"Thrice\",\"Once\"]},{\"category\":\"Celebrities\",\"type\":\"multiple\",\"difficulty\":\"easy\",\"question\":\"What does film maker Dan Bell typically focus his films on?\",\"correct_answer\":\"Abandoned Buildings and Dead Malls\",\"incorrect_answers\":[\"Historic Landmarks\",\"Action Films\",\"Documentaries \"]},{\"category\":\"History\",\"type\":\"multiple\",\"difficulty\":\"hard\",\"question\":\"When did Lithuania declare independence from the Soviet Union?\",\"correct_answer\":\"March 11th, 1990\",\"incorrect_answers\":[\"December 25th, 1991\",\"December 5th, 1991\",\"April 20th, 1989\"]},{\"category\":\"Entertainment: Video Games\",\"type\":\"multiple\",\"difficulty\":\"medium\",\"question\":\"What was the first Call of Duty game to include the Zombies gamemode?\",\"correct_answer\":\"Call of Duty: World at War\",\"incorrect_answers\":[\"Call of Duty: Black Ops\",\"Call of Duty: Modern Warfare 2\",\"Call of Duty: Black Ops III\"]},{\"category\":\"Entertainment: Television\",\"type\":\"boolean\",\"difficulty\":\"medium\",\"question\":\"In &quot;Star Trek&quot;, Klingons respect William Shakespeare, they even suspect him having a Klingon lineage.\",\"correct_answer\":\"True\",\"incorrect_answers\":[\"False\"]},{\"category\":\"Geography\",\"type\":\"multiple\",\"difficulty\":\"easy\",\"question\":\"What is the capital of Scotland?\",\"correct_answer\":\"Edinburgh\",\"incorrect_answers\":[\"Glasgow\",\"Dundee\",\"London\"]}]}";

    Parser parser = new Parser(json_data);

    ArrayList<Question> questions = parser.generate_questions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

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
