package com.convey.GUI;

import com.convey.game.GameEngine;
import com.convey.game.Question;
import com.convey.utils.Animation;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

/**
 *
 * @author jgonzalez
 */
public final class GameMainPanel extends javax.swing.JPanel {

    private GameEngine gameEngine;
    private Question currentQuestion;
    private MainFrame mainFrame;
    private Color answerResult = Color.WHITE;
    private LineBorder answerBorder;
    private final static int MAX_QUESTIONS = 3;
    private int questionCounter = 0;
    private Timer timer;
    private long startTime;
    private long duration;
    private double score = 0;
    private final double questionTime = 5000;

    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public static final String PROP_ANSWERBORDER = "answerBorder";
    public static final String PROP_ANSWERRESULT = "answerResult";
    public static final String PROP_MAINFRAME = "mainFrame";
    public static final String PROP_GAMEENGINE = "gameEngine";
    public static final String PROP_SCORE = "score";

    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
    /**
     * Get the value of score
     *
     * @return the value of score
     */
    public double getScore() {
        return score;
    }

    /**
     * Set the value of score
     *
     * @param score new value of score
     */
    public void setScore(double score) {
        double oldScore = this.score;
        this.score = score;
        propertyChangeSupport.firePropertyChange(PROP_SCORE, oldScore, score);
    }

    /**
     * Get the value of answerBorder
     *
     * @return the value of answerBorder
     */
    public LineBorder getAnswerBorder() {
        return answerBorder;
    }

    /**
     * Set the value of answerBorder
     *
     * @param answerBorder new value of answerBorder
     */
    public void setAnswerBorder(LineBorder answerBorder) {
        LineBorder oldAnswerBorder = this.answerBorder;
        this.answerBorder = answerBorder;
        propertyChangeSupport.firePropertyChange(PROP_ANSWERBORDER, oldAnswerBorder, answerBorder);
    }

    /**
     * Get the value of answerResult
     *
     * @return the value of answerResult
     */
    public Color getAnswerResult() {
        return answerResult;
    }

    /**
     * Set the value of answerResult
     *
     * @param answerResult new value of answerResult
     */
    public void setAnswerResult(Color answerResult) {
        Color oldAnswerResult = this.answerResult;
        this.answerResult = answerResult;
        propertyChangeSupport.firePropertyChange(PROP_ANSWERRESULT, oldAnswerResult, answerResult);
    }

    /**
     * Get the value of mainFrame
     *
     * @return the value of mainFrame
     */
    public MainFrame getMainFrame() {
        return mainFrame;
    }

    /**
     * Set the value of mainFrame
     *
     * @param mainFrame new value of mainFrame
     */
    public void setMainFrame(MainFrame mainFrame) {
        MainFrame oldMainFrame = this.mainFrame;
        this.mainFrame = mainFrame;
        propertyChangeSupport.firePropertyChange(PROP_MAINFRAME, oldMainFrame, mainFrame);
    }

    /**
     * Get the value of gameEngine
     *
     * @return the value of gameEngine
     */
    public GameEngine getGameEngine() {
        return gameEngine;
    }

    /**
     * Set the value of gameEngine
     *
     * @param gameEngine new value of gameEngine
     */
    public void setGameEngine(GameEngine gameEngine) {
        GameEngine oldGameEngine = this.gameEngine;
        this.gameEngine = gameEngine;
        propertyChangeSupport.firePropertyChange(PROP_GAMEENGINE, oldGameEngine, gameEngine);
    }

    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
    // </editor-fold>

    public GameMainPanel() {
        initComponents();
        this.gameEngine = new GameEngine(getMainFrame().getMySqlConnection());
        this.loadNextQuestion();
    }

    public GameMainPanel(MainFrame p_mainFrame) {
        setMainFrame(p_mainFrame);
        this.gameEngine = new GameEngine(getMainFrame().getMySqlConnection());
        initComponents();
        this.loadNextQuestion();
    }

    public void loadNextQuestion() {

        battery1.setValue(100);

        questionCounter++;

        if (questionCounter > MAX_QUESTIONS) {
            questionCounter = 0;

            try {
                ResultsPanel resultsPanel = new ResultsPanel();
                getMainFrame().installNewPanel(resultsPanel);
                getMainFrame().setGameStarted(false);

                System.out.println("Score: " + score);

            } catch (Exception ex) {
                getMainFrame().error(ex);
            }

        } else {
            hideAnswers(false);

            this.lblMensajeRespuesta.setText("");

            this.currentQuestion = this.gameEngine.getNextQuestion();

            this.lblQuestion.setText(this.currentQuestion.getTextQuestion());

            this.lblGreenOption.setText(this.currentQuestion.getOptionGreen());
            this.lblOrangeOption.setText(this.currentQuestion.getOptionOrange());
            this.lblYellowOption.setText(this.currentQuestion.getOptionYellow());
            this.lblRedOption.setText(this.currentQuestion.getOptionRed());

            Rectangle l_redFrom = new Rectangle(redAnswer.getBounds().x, redAnswer.getBounds().y, 0, 0);

            Rectangle l_orangeFrom = new Rectangle(orangeAnswer.getBounds().x, orangeAnswer.getBounds().y, 0, 0);

            Rectangle l_greenFrom = new Rectangle(greenAnswer.getBounds().x, greenAnswer.getBounds().y, 0, 0);

            Rectangle l_yellowFrom = new Rectangle(yellowAnswer.getBounds().x, yellowAnswer.getBounds().y, 0, 0);

            new Thread(() -> {
                try {

                    new Animation(redAnswer, l_redFrom, redAnswer.getBounds()).start();
                    Thread.sleep(150);

                    new Animation(orangeAnswer, l_orangeFrom, orangeAnswer.getBounds()).start();
                    Thread.sleep(150);

                    new Animation(yellowAnswer, l_yellowFrom, yellowAnswer.getBounds()).start();
                    Thread.sleep(150);

                    new Animation(greenAnswer, l_greenFrom, greenAnswer.getBounds()).start();
                    Thread.sleep(150);

                    startCountDown();

                } catch (InterruptedException ex) {
                    Logger.getLogger(GameMainPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }).start();
        }
    }

    public void hideAnswers(boolean p_flag1, boolean p_flag2, boolean p_flag3, boolean p_flag4) {
        redAnswer.setVisible(p_flag1);
        orangeAnswer.setVisible(p_flag2);
        yellowAnswer.setVisible(p_flag3);
        greenAnswer.setVisible(p_flag4);
    }

    public void hideAnswers(boolean p_flag) {
        redAnswer.setVisible(p_flag);
        orangeAnswer.setVisible(p_flag);
        yellowAnswer.setVisible(p_flag);
        greenAnswer.setVisible(p_flag);
    }

    public void checkResponse(String p_resopnse, boolean p_validTime) {

        new Thread(() -> {
            if (p_validTime) {
                if (currentQuestion.evalResponse(p_resopnse)) {
                    lblMensajeRespuesta.setText("Respuesta Correcta!!!");
                    setAnswerResult(Color.GREEN);
                    setAnswerBorder(new javax.swing.border.LineBorder(Color.GREEN, 10, true));
                    parseAnswerToButton(questionCounter, true);
                    sumScore(questionTime - duration, currentQuestion.getDificulty());

                } else {
                    lblMensajeRespuesta.setText("Respuesta Incorrecta!!!");
                    setAnswerResult(Color.RED);
                    setAnswerBorder(new javax.swing.border.LineBorder(Color.RED, 10, true));
                    parseAnswerToButton(questionCounter, false);
                }
            } else {
                lblMensajeRespuesta.setText("Tiempo Agotado!!!");
                setAnswerResult(Color.RED);
                setAnswerBorder(new javax.swing.border.LineBorder(Color.RED, 10, true));
                parseAnswerToButton(questionCounter, false);
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameMainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            setAnswerResult(Color.WHITE);
            setAnswerBorder(null);
            loadNextQuestion();
        }).start();
    }

    public void startCountDown() {
        timer = new Timer(40, (ActionEvent e) -> {
            duration = System.currentTimeMillis() - startTime;
            double l_progress = (double) duration / (double) questionTime;
            if (l_progress > 1f) {
                l_progress = 1f;
                ((Timer) e.getSource()).stop();
                checkResponse("", false);
            }
            battery1.setValue((int) Math.abs(Math.round(l_progress * 100) - 100));
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        startTime = System.currentTimeMillis();
        timer.start();
    }

    public void buttonListener(String p_option) {
        timer.stop();
        switch (p_option) {
            case "1":
                checkResponse(lblRedOption.getText(), true);
                break;
            case "2":
                checkResponse(lblOrangeOption.getText(), true);
                break;
            case "3":
                checkResponse(lblGreenOption.getText(), true);
                break;
            case "4":
                checkResponse(lblYellowOption.getText(), true);
                break;
        }
    }

    private void parseAnswerToButton(int p_seqButton, boolean p_answer) {
        switch (p_seqButton) {
            case 1:
                dataBaseLed1.setLedOn(true);
                if (p_answer) {
                    dataBaseLed1.setLedColor(eu.hansolo.steelseries.tools.LedColor.GREEN_LED);
                } else {
                    dataBaseLed1.setLedColor(eu.hansolo.steelseries.tools.LedColor.RED_LED);
                }
                break;
            case 2:
                dataBaseLed2.setLedOn(true);
                if (p_answer) {
                    dataBaseLed2.setLedColor(eu.hansolo.steelseries.tools.LedColor.GREEN_LED);
                } else {
                    dataBaseLed2.setLedColor(eu.hansolo.steelseries.tools.LedColor.RED_LED);
                }
                break;
            case 3:
                dataBaseLed3.setLedOn(true);
                if (p_answer) {
                    dataBaseLed3.setLedColor(eu.hansolo.steelseries.tools.LedColor.GREEN_LED);
                } else {
                    dataBaseLed3.setLedColor(eu.hansolo.steelseries.tools.LedColor.RED_LED);
                }
                break;
        }
    }

    private void sumScore(double p_duration, double p_questionDifficulty) {

        double l_calculated = (p_duration * p_questionDifficulty);

        score = score + l_calculated;

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        customPanel1 = new com.convey.component.CustomPanel();
        questionPanel = new javax.swing.JPanel();
        lblQuestion = new javax.swing.JLabel();
        answersPanel = new javax.swing.JPanel();
        redAnswer = new javax.swing.JPanel();
        lblRedOption = new javax.swing.JLabel();
        orangeAnswer = new javax.swing.JPanel();
        lblOrangeOption = new javax.swing.JLabel();
        yellowAnswer = new javax.swing.JPanel();
        lblYellowOption = new javax.swing.JLabel();
        greenAnswer = new javax.swing.JPanel();
        lblGreenOption = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        informationPanel = new javax.swing.JPanel();
        lblMensajeRespuesta = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        dataBaseLed1 = new eu.hansolo.steelseries.extras.Led();
        dataBaseLed2 = new eu.hansolo.steelseries.extras.Led();
        dataBaseLed3 = new eu.hansolo.steelseries.extras.Led();
        jPanel3 = new javax.swing.JPanel();
        battery1 = new eu.hansolo.steelseries.extras.Battery();

        setBackground(new java.awt.Color(204, 255, 255));
        setForeground(new java.awt.Color(204, 0, 153));
        setMinimumSize(new java.awt.Dimension(640, 480));
        setPreferredSize(new java.awt.Dimension(640, 480));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${answerBorder}"), this, org.jdesktop.beansbinding.BeanProperty.create("border"));
        bindingGroup.addBinding(binding);

        setLayout(new java.awt.BorderLayout());

        customPanel1.setImageBackground("wallpaper.png");
        customPanel1.setLayout(new java.awt.BorderLayout(10, 10));

        questionPanel.setOpaque(false);
        java.awt.GridBagLayout questionPanelLayout = new java.awt.GridBagLayout();
        questionPanelLayout.columnWidths = new int[] {0};
        questionPanelLayout.rowHeights = new int[] {0};
        questionPanel.setLayout(questionPanelLayout);

        lblQuestion.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lblQuestion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQuestion.setText("Como se llama la version anterior a windows XP ?");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${answerResult}"), lblQuestion, org.jdesktop.beansbinding.BeanProperty.create("foreground"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(30, 30, 30, 30);
        questionPanel.add(lblQuestion, gridBagConstraints);

        customPanel1.add(questionPanel, java.awt.BorderLayout.PAGE_START);

        answersPanel.setOpaque(false);
        answersPanel.setLayout(new java.awt.GridLayout(2, 2, 30, 30));

        redAnswer.setBackground(new java.awt.Color(255, 0, 51));
        redAnswer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                redAnswerMousePressed(evt);
            }
        });
        redAnswer.setLayout(new java.awt.GridBagLayout());

        lblRedOption.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblRedOption.setText("Windows 3.1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        redAnswer.add(lblRedOption, gridBagConstraints);

        answersPanel.add(redAnswer);

        orangeAnswer.setBackground(new java.awt.Color(255, 102, 0));
        orangeAnswer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                orangeAnswerMousePressed(evt);
            }
        });
        orangeAnswer.setLayout(new java.awt.GridBagLayout());

        lblOrangeOption.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblOrangeOption.setText("Windows 95");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        orangeAnswer.add(lblOrangeOption, gridBagConstraints);

        answersPanel.add(orangeAnswer);

        yellowAnswer.setBackground(new java.awt.Color(255, 255, 51));
        yellowAnswer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                yellowAnswerMousePressed(evt);
            }
        });
        yellowAnswer.setLayout(new java.awt.GridBagLayout());

        lblYellowOption.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblYellowOption.setText("Windows Vista");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        yellowAnswer.add(lblYellowOption, gridBagConstraints);

        answersPanel.add(yellowAnswer);

        greenAnswer.setBackground(new java.awt.Color(0, 153, 102));
        greenAnswer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                greenAnswerMousePressed(evt);
            }
        });
        greenAnswer.setLayout(new java.awt.GridBagLayout());

        lblGreenOption.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblGreenOption.setText("Windows 98");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        greenAnswer.add(lblGreenOption, gridBagConstraints);

        answersPanel.add(greenAnswer);

        customPanel1.add(answersPanel, java.awt.BorderLayout.CENTER);

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.PAGE_AXIS));

        informationPanel.setOpaque(false);
        informationPanel.setLayout(new java.awt.GridBagLayout());

        lblMensajeRespuesta.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblMensajeRespuesta.setText("Respuesta Correcta!!");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${answerResult}"), lblMensajeRespuesta, org.jdesktop.beansbinding.BeanProperty.create("foreground"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        informationPanel.add(lblMensajeRespuesta, gridBagConstraints);

        jPanel2.add(informationPanel);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        dataBaseLed1.setLedColor(eu.hansolo.steelseries.tools.LedColor.CYAN);
        dataBaseLed1.setMaximumSize(new java.awt.Dimension(100, 100));
        dataBaseLed1.setMinimumSize(new java.awt.Dimension(100, 100));
        jPanel1.add(dataBaseLed1);

        dataBaseLed2.setLedColor(eu.hansolo.steelseries.tools.LedColor.CYAN);
        dataBaseLed2.setMaximumSize(new java.awt.Dimension(100, 100));
        dataBaseLed2.setMinimumSize(new java.awt.Dimension(100, 100));
        jPanel1.add(dataBaseLed2);

        dataBaseLed3.setLedColor(eu.hansolo.steelseries.tools.LedColor.CYAN);
        dataBaseLed3.setMaximumSize(new java.awt.Dimension(100, 100));
        dataBaseLed3.setMinimumSize(new java.awt.Dimension(100, 100));
        jPanel1.add(dataBaseLed3);

        jPanel2.add(jPanel1);

        jPanel3.setMinimumSize(new java.awt.Dimension(500, 50));
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(500, 50));
        jPanel3.setLayout(new java.awt.BorderLayout());

        battery1.setMinimumSize(new java.awt.Dimension(500, 50));
        battery1.setPreferredSize(new java.awt.Dimension(500, 50));
        battery1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(battery1, java.awt.BorderLayout.PAGE_START);

        jPanel2.add(jPanel3);

        customPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        add(customPanel1, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void redAnswerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_redAnswerMousePressed
        buttonListener("1");
    }//GEN-LAST:event_redAnswerMousePressed

    private void orangeAnswerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orangeAnswerMousePressed
        buttonListener("2");
    }//GEN-LAST:event_orangeAnswerMousePressed

    private void yellowAnswerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_yellowAnswerMousePressed
        buttonListener("4");
    }//GEN-LAST:event_yellowAnswerMousePressed

    private void greenAnswerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_greenAnswerMousePressed
        buttonListener("3");
    }//GEN-LAST:event_greenAnswerMousePressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel answersPanel;
    private eu.hansolo.steelseries.extras.Battery battery1;
    private com.convey.component.CustomPanel customPanel1;
    private eu.hansolo.steelseries.extras.Led dataBaseLed1;
    private eu.hansolo.steelseries.extras.Led dataBaseLed2;
    private eu.hansolo.steelseries.extras.Led dataBaseLed3;
    private javax.swing.JPanel greenAnswer;
    private javax.swing.JPanel informationPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblGreenOption;
    private javax.swing.JLabel lblMensajeRespuesta;
    private javax.swing.JLabel lblOrangeOption;
    private javax.swing.JLabel lblQuestion;
    public javax.swing.JLabel lblRedOption;
    private javax.swing.JLabel lblYellowOption;
    private javax.swing.JPanel orangeAnswer;
    private javax.swing.JPanel questionPanel;
    private javax.swing.JPanel redAnswer;
    private javax.swing.JPanel yellowAnswer;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
