
package com.example.quizapp;
import android.app.Application;

public class MyApplication extends Application{
    private String selectedCategoryNum = "9";
    private String selectedDifficulty = "easy";
    private String apiURL;
    private String jsonData = "";

    public String getCategory() {
        return selectedCategoryNum;
    }

    public String setCategory(String selectedCategoryNum) {
        this.selectedCategoryNum = selectedCategoryNum;
        return selectedCategoryNum;
    }

    public String getDifficulty() {
        return selectedDifficulty;
    }

    public String setDifficulty(String selectedDifficulty) {
        this.selectedDifficulty = selectedDifficulty;
        return selectedDifficulty;
    }

    public String getURL() {
        return apiURL;
    }

    public String setURL(String apiURL) {
        this.apiURL = apiURL;
        return apiURL;
    }

    public String getJSON() {
        return jsonData;
    }

    public String setJSON(String jsonData) {
        this.jsonData = jsonData;
        return jsonData;
    }

}
