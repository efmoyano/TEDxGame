/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.convey.game;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author jgonzalez
 */
public class GameEngine {
    int MAX_QUESTIONS = 9;
    ArrayList<Question> questions;
    ArrayList<Question> alreadyUsedQuestions;
    public GameEngine(){
        loadQuestions();
    }
    public void loadQuestions(){
        questions = new ArrayList<Question>();
        Question q1 = new Question("Cual es la version anterior a Windows XP ?", new Option("Windows Milenium", true), new Option("Windows Vista", false), new Option("Ubuntu", false), new Option("Windows Mint", false));
        Question q2 = new Question("Cual es la version anterior a Windows Vista ?", new Option("Windows 98", true), new Option("Windows Vista", false), new Option("Ubuntu", false), new Option("Windows Mint", false));
        Question q3 = new Question("Cual es la version anterior a Windows Milenium ?", new Option("Windows 98", true), new Option("Windows Vista", false), new Option("Ubuntu", false), new Option("Windows Mint", false));
        Question q4 = new Question("Cual es la version anterior a Windows 8 ?", new Option("Windows 98", true), new Option("Windows Vista", false), new Option("Ubuntu", false), new Option("Windows Mint", false));
        Question q5 = new Question("Cual es la version anterior a Windows 9 ?", new Option("Windows 98", true), new Option("Windows Vista", false), new Option("Ubuntu", false), new Option("Windows Mint", false));
        Question q6 = new Question("Cual es la version anterior a Ubuntu 7 ?", new Option("Windows 98", true), new Option("Windows Vista", false), new Option("Ubuntu", false), new Option("Windows Mint", false));
        Question q7 = new Question("Cual es la version anterior a Debian 9 ?", new Option("Windows 98", true), new Option("Windows Vista", false), new Option("Ubuntu", false), new Option("Windows Mint", false));
        Question q8 = new Question("Cual es la version anterior a Windows 12 ?", new Option("Windows 98", true), new Option("Windows Vista", false), new Option("Ubuntu", false), new Option("Windows Mint", false));
        Question q9 = new Question("Cual es la version anterior a OSX Lion ?", new Option("Windows 98", true), new Option("Windows Vista", false), new Option("Ubuntu", false), new Option("Windows Mint", false));
        Question q10 = new Question("Cual es la version anterior a IOS4 ?", new Option("Windows 98", true), new Option("Windows Vista", false), new Option("Ubuntu", false), new Option("Windows Mint", false));
        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
        questions.add(q4);
        questions.add(q5);
        questions.add(q6);
        questions.add(q7);
        questions.add(q8);
        questions.add(q9);
        questions.add(q10);
    }
    
    public Question getNextQuestion(){
        return this.questions.get(this.getRandomQuestionIndex());
    }
    
    public int getRandomQuestionIndex() {
        Random rand = new Random();
        int randomNum = rand.nextInt(questions.size());
        return randomNum;
    }
}
