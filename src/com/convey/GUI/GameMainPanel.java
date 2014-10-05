package com.convey.GUI;

import com.convey.game.GameEngine;
import com.convey.game.Question;
import com.convey.utils.Animation;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.LineBorder;

/**
 *
 * @author jgonzalez
 */
public final class GameMainPanel extends javax.swing.JPanel {

    private GameEngine gameEngine;
    public static final String PROP_GAMEENGINE = "gameEngine";
    private Question currentQuestion;
    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private MainFrame mainFrame;
    public static final String PROP_MAINFRAME = "mainFrame";
    private Dimension l_panelSize;

    private Color answerResult = Color.WHITE;
    public static final String PROP_ANSWERRESULT = "answerResult";

    private LineBorder answerBorder;

    public static final String PROP_ANSWERBORDER = "answerBorder";

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

    public GameMainPanel() {
        initComponents();
        this.gameEngine = new GameEngine();
        this.loadNextQuestion();
    }

    public GameMainPanel(MainFrame p_mainFrame) {
        setMainFrame(p_mainFrame);
        initComponents();
        l_panelSize = getMainFrame().getPreferredSize();
        this.gameEngine = new GameEngine();
        this.loadNextQuestion();
    }

    public void loadNextQuestion() {

        hideAnswers(false, false, false, false);

        this.lblMensajeRespuesta.setText("");
        this.currentQuestion = this.gameEngine.getNextQuestion();

        this.lblQuestion.setText(this.currentQuestion.getQuestionText());

        this.lblGreenOption.setText(this.currentQuestion.getOptionGreen());
        this.lblOrangeOption.setText(this.currentQuestion.getOptionOrange());
        this.lblYellowOption.setText(this.currentQuestion.getOptionYellow());
        this.lblRedOption.setText(this.currentQuestion.getOptionRed());

        Rectangle l_redFrom = new Rectangle(l_panelSize.width / 4, l_panelSize.height / 2, 0, 0);

        Rectangle l_orangeFrom = new Rectangle(l_panelSize.width / 4, l_panelSize.height / 2, 0, 0);

        Rectangle l_greenFrom = new Rectangle(l_panelSize.width / 2, l_panelSize.height, 0, 0);

        Rectangle l_yellowFrom = new Rectangle(l_panelSize.width / 2, l_panelSize.height, 0, 0);

        Rectangle to = new Rectangle(0, 0, l_panelSize.width / 2, l_panelSize.height / 4);

        new Thread(() -> {
            try {
                hideAnswers(true, false, false, false);
                new Animation(redAnswer, l_redFrom, to).start();

                Thread.sleep(250);

                hideAnswers(true, true, false, false);
                new Animation(orangeAnswer, l_orangeFrom, to).start();

                Thread.sleep(250);

                hideAnswers(true, true, true, false);
                new Animation(yellowAnswer, l_yellowFrom, to).start();

                Thread.sleep(250);

                hideAnswers(true, true, true, true);
                new Animation(greenAnswer, l_greenFrom, to).start();
            } catch (InterruptedException ex) {
                Logger.getLogger(GameMainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }

    public void hideAnswers(boolean p_flag1, boolean p_flag2, boolean p_flag3, boolean p_flag4) {
        redAnswer.setVisible(p_flag1);
        orangeAnswer.setVisible(p_flag2);
        yellowAnswer.setVisible(p_flag3);
        greenAnswer.setVisible(p_flag4);
    }

    public void checkResponse(String p_resopnse) {

        new Thread(() -> {
            if (currentQuestion.evalResponse(p_resopnse)) {
                lblMensajeRespuesta.setText("Respuesta Correcta!!!");
                setAnswerResult(Color.GREEN);
                setAnswerBorder(new javax.swing.border.LineBorder(Color.GREEN, 10, true));
            } else {
                lblMensajeRespuesta.setText("Respuesta Incorrecta!!!");
                setAnswerResult(Color.RED);
                setAnswerBorder(new javax.swing.border.LineBorder(Color.RED, 10, true));
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameMainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            hideAnswers(false, false, false, false);
            setAnswerResult(Color.WHITE);
            setAnswerBorder(null);
            loadNextQuestion();
        }).start();
    }

    public void buttonListener(String p_option) {
        switch (p_option) {
            case "1":
                checkResponse(lblRedOption.getText());
                break;
            case "2":
                checkResponse(lblOrangeOption.getText());
                break;
            case "3":
                checkResponse(lblGreenOption.getText());
                break;
            case "4":
                checkResponse(lblYellowOption.getText());
                break;
        }
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
        conveyBrand = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 255, 255));
        setForeground(new java.awt.Color(204, 0, 153));
        setMinimumSize(new java.awt.Dimension(640, 480));
        setPreferredSize(new java.awt.Dimension(640, 480));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${answerBorder}"), this, org.jdesktop.beansbinding.BeanProperty.create("border"));
        bindingGroup.addBinding(binding);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        setLayout(new java.awt.BorderLayout());

        customPanel1.setImageBackground("wallpaper.png");
        customPanel1.setLayout(new java.awt.BorderLayout(10, 10));

        questionPanel.setOpaque(false);
        java.awt.GridBagLayout questionPanelLayout = new java.awt.GridBagLayout();
        questionPanelLayout.columnWidths = new int[] {0};
        questionPanelLayout.rowHeights = new int[] {0};
        questionPanel.setLayout(questionPanelLayout);

        lblQuestion.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lblQuestion.setForeground(new java.awt.Color(255, 255, 255));
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
        jPanel2.setLayout(new java.awt.GridBagLayout());

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

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        jPanel2.add(informationPanel, gridBagConstraints);

        conveyBrand.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        conveyBrand.setMaximumSize(new java.awt.Dimension(250, 100));
        conveyBrand.setMinimumSize(new java.awt.Dimension(250, 100));
        conveyBrand.setPreferredSize(new java.awt.Dimension(250, 100));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Convey");

        jLabel2.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Asking Game     ");

        javax.swing.GroupLayout conveyBrandLayout = new javax.swing.GroupLayout(conveyBrand);
        conveyBrand.setLayout(conveyBrandLayout);
        conveyBrandLayout.setHorizontalGroup(
            conveyBrandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(conveyBrandLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(conveyBrandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(conveyBrandLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 36, Short.MAX_VALUE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        conveyBrandLayout.setVerticalGroup(
            conveyBrandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(conveyBrandLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 20);
        jPanel2.add(conveyBrand, gridBagConstraints);

        customPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        add(customPanel1, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized

    }//GEN-LAST:event_formComponentResized

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel answersPanel;
    private javax.swing.JPanel conveyBrand;
    private com.convey.component.CustomPanel customPanel1;
    private javax.swing.JPanel greenAnswer;
    private javax.swing.JPanel informationPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
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
