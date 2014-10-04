package com.convey.game;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author jgonzalez
 */
public final class GameEngine {

    int MAX_QUESTIONS = 9;
    ArrayList<Question> questions;
    ArrayList<Question> alreadyUsedQuestions;

    public GameEngine() {
        loadQuestions();
    }

    public void loadQuestions() {
        questions = new ArrayList<>();
        Question q1 = new Question("Cuanto es 1 + 1 ?", new Option("2", true), new Option("3", false), new Option("4", false), new Option("5", false));
        Question q2 = new Question("Cual es la raiz cuadrada de 81 ?", new Option("22", false), new Option("9", true), new Option("33", false), new Option("8", false));
        Question q3 = new Question("Cual es el comando de linux para ser super usuario ?", new Option("sumo", false), new Option("sudo", true), new Option("suso", false), new Option("suro", false));
        Question q4 = new Question("Por que ivan es tan pecho frio ?", new Option("por cagon", false), new Option("por boton", false), new Option("por chillon", false), new Option("todas son correctas", true));
        Question q5 = new Question("Cual es la version anterior a Windows 8 ?", new Option("Windows 98", false), new Option("Windows Vista", false), new Option("Windows 7", true), new Option("Windows Mint", false));
        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
        questions.add(q4);
        questions.add(q5);
    }

    public Question getNextQuestion() {
        return this.questions.get(this.getRandomQuestionIndex());
    }

    public int getRandomQuestionIndex() {
        return new Random().nextInt(questions.size());
    }
}
