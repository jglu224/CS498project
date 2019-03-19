package com.example.quizapp;

import java.lang.String;
import java.util.*;

public class Question {
    String text;
    String category;
    String type;
    String difficulty;
    List<String> incorrect_answers = new ArrayList<String>();
    String correct_answer;

    Question(String text,
             String category,
             String type,
             String difficulty,
             String correct_answer,
             List<String> incorrect_answers) {
        this.text = text;
        this.incorrect_answers = new ArrayList<String>(incorrect_answers);
        this.correct_answer = correct_answer;
    }

    public boolean is_correct(String answer) {
        return this.correct_answer == answer;
    }

    public String get_question_text() {

        return this.text;
    }

    public ArrayList<String> get_scrambled_answers() {

        ArrayList<String> questions = new ArrayList<>(incorrect_answers);

        questions.add(correct_answer);

        Collections.shuffle(questions);

        return questions;
    }
}