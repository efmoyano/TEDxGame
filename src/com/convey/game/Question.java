package com.convey.game;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

/**
 *
 * @author jgonzalez
 */
public class Question {

    private int dificulty;
    private String textQuestion;
    private ArrayList<Option> options;

    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public static final String PROP_DIFICULTY = "dificulty";
    public static final String PROP_TEXTQUESTION = "textQuestion";
    public static final String PROP_OPTIONS = "options";

    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
    /**
     * Get the value of options
     *
     * @return the value of options
     */
    public ArrayList<Option> getOptions() {
        return options;
    }

    /**
     * Set the value of options
     *
     * @param options new value of options
     */
    public void setOptions(ArrayList<Option> options) {
        ArrayList<Option> oldOptions = this.options;
        this.options = options;
        propertyChangeSupport.firePropertyChange(PROP_OPTIONS, oldOptions, options);
    }

    /**
     * Get the value of textQuestion
     *
     * @return the value of textQuestion
     */
    public String getTextQuestion() {
        return textQuestion;
    }

    /**
     * Set the value of textQuestion
     *
     * @param textQuestion new value of textQuestion
     */
    public void setTextQuestion(String textQuestion) {
        String oldTextQuestion = this.textQuestion;
        this.textQuestion = textQuestion;
        propertyChangeSupport.firePropertyChange(PROP_TEXTQUESTION, oldTextQuestion, textQuestion);
    }

    /**
     * Get the value of dificulty
     *
     * @return the value of dificulty
     */
    public int getDificulty() {
        return dificulty;
    }

    /**
     * Set the value of dificulty
     *
     * @param dificulty new value of dificulty
     */
    public void setDificulty(int dificulty) {
        int oldDificulty = this.dificulty;
        this.dificulty = dificulty;
        propertyChangeSupport.firePropertyChange(PROP_DIFICULTY, oldDificulty, dificulty);
    }

    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
    // </editor-fold>

    public Question(String p_questionText, Option p_option1, Option p_option2, Option p_option3, Option p_option4, int p_difficulty) {
        textQuestion = p_questionText;
        options = new ArrayList<>();
        options.add(p_option1);
        options.add(p_option2);
        options.add(p_option3);
        options.add(p_option4);
        dificulty = p_difficulty;
    }

    public boolean evalResponse(String response) {
        return this.options.stream().anyMatch((actual) -> (actual.text.trim().equalsIgnoreCase(response.trim()) && actual.isAnswer == true));
    }

    public String getOptionRed() {
        return options.get(0).text;
    }

    public String getOptionYellow() {
        return options.get(1).text;
    }

    public String getOptionOrange() {
        return options.get(2).text;
    }

    public String getOptionGreen() {
        return this.options.get(3).text;
    }

}
