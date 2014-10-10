package com.convey.game;

import com.convey.services.MySqlConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author jgonzalez
 */
public final class GameEngine {

    private ArrayList<Question> questions;
    private ArrayList<Question> alreadyUsedQuestions;
    private MySqlConnection mySqlConnection;
    private boolean q1;
    private boolean q2;
    private boolean q3;
    private boolean q4;

    public GameEngine(MySqlConnection p_mySqlConnection) {
        mySqlConnection = p_mySqlConnection;
        loadQuestions();
    }

    public void loadQuestions() {

        ResultSet resultset = mySqlConnection.getQuestion();
        questions = new ArrayList<>();

        try {
            while (resultset.next()) {

                parseCorrectAnswer(resultset.getInt(8));

                Question l_question = new Question(
                        resultset.getString(2),
                        new Option(resultset.getString(3), q1),
                        new Option(resultset.getString(4), q2),
                        new Option(resultset.getString(5), q3),
                        new Option(resultset.getString(6), q4),
                        resultset.getInt(7));

                questions.add(l_question);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void parseCorrectAnswer(int p_correctAnswer) {
        switch (p_correctAnswer) {
            case 1:
                q1 = true;
                q2 = false;
                q3 = false;
                q4 = false;
                break;
            case 2:
                q1 = false;
                q2 = true;
                q3 = false;
                q4 = false;
                break;
            case 3:
                q1 = false;
                q2 = false;
                q3 = true;
                q4 = false;
                break;
            case 4:
                q1 = false;
                q2 = false;
                q3 = false;
                q4 = true;
                break;

        }
    }

    public Question getNextQuestion() {
        return this.questions.get(this.getRandomQuestionIndex());
    }

    public int getRandomQuestionIndex() {
        if (questions.size() > 0) {
            return new Random().nextInt(questions.size());
        }
        return 0;
    }
}
