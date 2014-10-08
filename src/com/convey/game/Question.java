package com.convey.game;

import java.util.ArrayList;

/**
 *
 * @author jgonzalez
 */
public class Question {

    private final String textQuestion;
    private final ArrayList<Option> options;

    public Question(String text, Option a, Option b, Option c, Option d) {
        this.textQuestion = text;
        this.options = new ArrayList<>();
        this.options.add(a);
        this.options.add(b);
        this.options.add(c);
        this.options.add(d);
    }

    public boolean evalResponse(String response) {
        return this.options.stream().anyMatch((actual) -> (actual.text.trim().equalsIgnoreCase(response.trim()) && actual.isAnswer == true));
    }

    public String getQuestionText() {
        return this.textQuestion;
    }

    public String getOptionRed() {
        return this.options.get(0).text;
    }

    public String getOptionYellow() {
        return this.options.get(1).text;
    }

    public String getOptionOrange() {
        return this.options.get(2).text;
    }

    public String getOptionGreen() {
        return this.options.get(3).text;
    }

}
