package com.convey.game;

import java.util.ArrayList;

/**
 *
 * @author jgonzalez
 */
public class Question {

    private String textQuestion;
    private ArrayList<Option> f_options;
    private int f_dificulty;

    public Question(String p_questionText, Option p_option1, Option p_option2, Option p_option3, Option p_option4, int p_difficulty) {
        textQuestion = p_questionText;
        f_options = new ArrayList<>();
        f_options.add(p_option1);
        f_options.add(p_option2);
        f_options.add(p_option3);
        f_options.add(p_option4);
        f_dificulty = p_difficulty;
    }

    public boolean evalResponse(String response) {
        return this.f_options.stream().anyMatch((actual) -> (actual.text.trim().equalsIgnoreCase(response.trim()) && actual.isAnswer == true));
    }

    public String getQuestionText() {
        return textQuestion;
    }

    public String getOptionRed() {
        return f_options.get(0).text;
    }

    public String getOptionYellow() {
        return f_options.get(1).text;
    }

    public String getOptionOrange() {
        return f_options.get(2).text;
    }

    public String getOptionGreen() {
        return this.f_options.get(3).text;
    }

}
