package com.example.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Spinner spinner_category = findViewById(R.id.spinner_category);
        ArrayAdapter<CharSequence> adapter_category = ArrayAdapter.createFromResource(this, R.array.Category, android.R.layout.simple_spinner_item);
        adapter_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_category.setAdapter(adapter_category);

        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Stores the user selected category into a string
                // Will be used later to build URL
                int categoryIndex = position + 9;
                MyApplication application = (MyApplication)getApplication();
                String selectedCategoryNum = application.setCategory(Integer.toString(categoryIndex));
                Log.d("Input", selectedCategoryNum);

            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Spinner spinner_difficulty = findViewById(R.id.spinner_difficulty);
        ArrayAdapter<CharSequence> adapter_difficulty = ArrayAdapter.createFromResource(this, R.array.Difficulty, android.R.layout.simple_spinner_item);
        adapter_difficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_difficulty.setAdapter(adapter_difficulty);
        spinner_difficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                MyApplication application = (MyApplication)getApplication();
                String selectedDifficulty = application.setDifficulty(parent.getItemAtPosition(position).toString());
                Log.d("Input", selectedDifficulty);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        

        setupLaunchButton();
    }

    private void setupLaunchButton()
    {

        Button start_quiz = (Button) findViewById(R.id.start_quiz);
        start_quiz.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {

                // Builds api URL
                String baseBeginURL = "https://opentdb.com/api.php?amount=10&category=";

                MyApplication application = (MyApplication)getApplication();
                String selectedCategoryNum = application.getCategory();

                String baseMiddleURL = "&difficulty=";

                String selectedDifficulty = application.getDifficulty();

                String baseEndURL = "&type=multiple";

                String apiURL = baseBeginURL + selectedCategoryNum + baseMiddleURL + selectedDifficulty + baseEndURL;

                Log.d("Input", apiURL);

                // Launch the second activity
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);

                startActivity(intent);
            }

        });
    }

    /*@Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }*/
}
