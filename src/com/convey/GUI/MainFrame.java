package com.convey.GUI;

import com.convey.hardware.arduino.ArduinoDevice;
import com.convey.hardware.arduino.ArduinoEventAdapter;
import com.convey.hardware.video.VideoDevice;
import com.convey.services.MySqlConnection;
import com.convey.utils.ProcessPaths;
import com.convey.utils.ProcessRunner;
import com.convey.utils.XLSProcessor;
import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 * @projectName TEDxGame
 * @package com.convey.GUI
 * @filename MainFrame.java
 * @encoding UTF-8
 * @author Ernesto Moyano
 * @date 28/08/2014 21:27:27
 */
public class MainFrame extends javax.swing.JFrame {

    private ArduinoDevice arduinoDevice;
    private MySqlConnection mySqlConnection;
    private GameMainPanel gameMainPanel;
    private VideoDevice videoDevice;
    private boolean gameStarted = false;
    private VideoPanel videoPanel;

    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public static final String PROP_VIDEODEVICE = "videoDevice";
    public static final String PROP_GAMEMAINPANEL = "gameMainPanel";
    public static final String PROP_COLOR = "color";
    public static final String PROP_MYSQLCONNECTION = "mySqlConnection";
    public static final String PROP_ARDUINODEVICE = "arduinoDevice";
    public static final String PROP_GAMESTARTED = "gameStarted";

    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
    /**
     * Get the value of gameStarted
     *
     * @return the value of gameStarted
     */
    public boolean isGameStarted() {
        return gameStarted;
    }

    /**
     * Set the value of gameStarted
     *
     * @param gameStarted new value of gameStarted
     */
    public void setGameStarted(boolean gameStarted) {
        boolean oldGameStarted = this.gameStarted;
        this.gameStarted = gameStarted;
        propertyChangeSupport.firePropertyChange(PROP_GAMESTARTED, oldGameStarted, gameStarted);
    }

    /**
     * Get the value of videoDevice
     *
     * @return the value of videoDevice
     */
    public VideoDevice getVideoDevice() {
        return videoDevice;
    }

    /**
     * Set the value of videoDevice
     *
     * @param videoDevice new value of videoDevice
     */
    public void setVideoDevice(VideoDevice videoDevice) {
        VideoDevice oldVideoDevice = this.videoDevice;
        this.videoDevice = videoDevice;
        propertyChangeSupport.firePropertyChange(PROP_VIDEODEVICE, oldVideoDevice, videoDevice);
    }

    /**
     * Get the value of gameMainPanel
     *
     * @return the value of gameMainPanel
     */
    public GameMainPanel getGameMainPanel() {
        return gameMainPanel;
    }

    /**
     * Set the value of gameMainPanel
     *
     * @param gameMainPanel new value of gameMainPanel
     */
    public void setGameMainPanel(GameMainPanel gameMainPanel) {
        GameMainPanel oldGameMainPanel = this.gameMainPanel;
        this.gameMainPanel = gameMainPanel;
        propertyChangeSupport.firePropertyChange(PROP_GAMEMAINPANEL, oldGameMainPanel, gameMainPanel);
    }

    /**
     * Get the value of mySqlConnection
     *
     * @return the value of mySqlConnection
     */
    public MySqlConnection getMySqlConnection() {
        return mySqlConnection;
    }

    /**
     * Set the value of mySqlConnection
     *
     * @param mySqlConnection new value of mySqlConnection
     */
    public void setMySqlConnection(MySqlConnection mySqlConnection) {
        MySqlConnection oldMySqlConnection = this.mySqlConnection;
        this.mySqlConnection = mySqlConnection;
        propertyChangeSupport.firePropertyChange(PROP_MYSQLCONNECTION, oldMySqlConnection, mySqlConnection);
    }

    /**
     * Get the value of arduinoDevice
     *
     * @return the value of arduinoDevice
     */
    public ArduinoDevice getArduinoDevice() {
        return arduinoDevice;
    }

    /**
     * Set the value of arduinoDevice
     *
     * @param arduinoDevice new value of arduinoDevice
     */
    public void setArduinoDevice(ArduinoDevice arduinoDevice) {
        ArduinoDevice oldArduinoDevice = this.arduinoDevice;
        this.arduinoDevice = arduinoDevice;
        propertyChangeSupport.firePropertyChange(PROP_ARDUINODEVICE, oldArduinoDevice, arduinoDevice);
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

    public MainFrame() {

        arduinoDevice = new ArduinoDevice();
        if (!arduinoDevice.isConnected()) {
            arduinoDevice.initialize();
        }

        videoDevice = new VideoDevice();

        this.addWindowListener(
                new WindowAdapter() {

                    @Override
                    public void windowClosing(WindowEvent e) {
                        if (videoDevice != null) {
                            videoDevice.stop();
                        }
                    }
                });

        arduinoDevice.addArduinoEventListener(new ArduinoEventAdapter() {
            private String[] l_components;

            @Override
            public void onMessageReceived(String p_message) {

                l_components = p_message.split("x");

                switch (l_components[0]) {
                    // Information messages
                    case "0":
                        JOptionPane.showMessageDialog(null, l_components[1]);
                        break;

                    // Proximity sensor messages   
                    case "1":
                        if (Integer.parseInt(l_components[1]) < 50) {
                            event();
                        }
                        break;

                    // Button messages
                    case "2":
                        gameMainPanel.buttonListener(l_components[1]);
                        break;
                }
            }
        });

        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke("ESCAPE");
        Action escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(null);
            }
        };
        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        this.getRootPane().getActionMap().put("ESCAPE", escapeAction);

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                boolean l_hasSelectedMenu = false;
                for (int i = 0; i < l_mainMenu.getMenuCount(); ++i) {
                    if (l_mainMenu.getMenu(i).isSelected()) {
                        l_hasSelectedMenu = true;
                        break;
                    }
                }
                if (!l_hasSelectedMenu) {
                    l_mainMenu.setVisible(e.getY() < 50);
                }
            }
        });

        mySqlConnection = new MySqlConnection();

        initComponents();

        if (mySqlConnection.connect()) {
            dataBaseLed.setLedColor(eu.hansolo.steelseries.tools.LedColor.GREEN_LED);
        } else {
            dataBaseLed.setLedColor(eu.hansolo.steelseries.tools.LedColor.RED_LED);
        }

        runFullScreenMenuActionPerformed(null);
        runScreenSaverMenuActionPerformed(null);
        setExtendedState(getExtendedState());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        mainPanel = new javax.swing.JPanel();
        customPanel1 = new com.convey.component.CustomPanel();
        jPanel1 = new javax.swing.JPanel();
        dataBaseLed = new eu.hansolo.steelseries.extras.Led();
        dataBaseLabel = new javax.swing.JLabel();
        conveyBrand = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        l_mainMenu = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        newGameItem = new javax.swing.JMenuItem();
        exitItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        loadQuestionsItem = new javax.swing.JMenuItem();
        dbConfigItem = new javax.swing.JMenuItem();
        webcamMenuItem = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        arduinoSettingsItem = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        runFullScreenMenu = new javax.swing.JMenuItem();
        runScreenSaverMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(640, 480));

        mainPanel.setBackground(new java.awt.Color(204, 204, 255));
        mainPanel.setMinimumSize(new java.awt.Dimension(640, 480));
        mainPanel.setPreferredSize(new java.awt.Dimension(640, 480));
        mainPanel.setLayout(new java.awt.BorderLayout());

        customPanel1.setImageBackground("wallpaper.png");
        customPanel1.setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "Data Base", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        dataBaseLed.setLedColor(eu.hansolo.steelseries.tools.LedColor.BLUE_LED);
        dataBaseLed.setLedOn(true);
        dataBaseLed.setMinimumSize(new java.awt.Dimension(50, 50));
        jPanel1.add(dataBaseLed);

        dataBaseLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dataBaseLabel.setForeground(new java.awt.Color(255, 255, 255));
        dataBaseLabel.setMinimumSize(new java.awt.Dimension(100, 100));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${mySqlConnection.status}"), dataBaseLabel, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jPanel1.add(dataBaseLabel);

        customPanel1.add(jPanel1, java.awt.BorderLayout.SOUTH);

        conveyBrand.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        conveyBrand.setMaximumSize(new java.awt.Dimension(250, 100));
        conveyBrand.setMinimumSize(new java.awt.Dimension(250, 100));
        conveyBrand.setOpaque(false);

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
                        .addGap(0, 0, Short.MAX_VALUE))
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

        customPanel1.add(conveyBrand, java.awt.BorderLayout.PAGE_START);

        mainPanel.add(customPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        l_mainMenu.setBackground(new java.awt.Color(153, 255, 153));
        l_mainMenu.setBorder(null);
        l_mainMenu.setBorderPainted(false);
        l_mainMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        l_mainMenu.setOpaque(false);

        jMenu1.setText("File");

        newGameItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newGameItem.setText("New Game");
        newGameItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameItemActionPerformed(evt);
            }
        });
        jMenu1.add(newGameItem);

        exitItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        exitItem.setText("Exit");
        exitItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitItemActionPerformed(evt);
            }
        });
        jMenu1.add(exitItem);

        l_mainMenu.add(jMenu1);

        jMenu2.setText("Tools");

        loadQuestionsItem.setText("Load Questions");
        loadQuestionsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadQuestionsItemActionPerformed(evt);
            }
        });
        jMenu2.add(loadQuestionsItem);

        dbConfigItem.setText("DB Config");
        dbConfigItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dbConfigItemActionPerformed(evt);
            }
        });
        jMenu2.add(dbConfigItem);

        webcamMenuItem.setText("Webcam");
        webcamMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                webcamMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(webcamMenuItem);

        l_mainMenu.add(jMenu2);

        jMenu3.setText("Arduino");

        arduinoSettingsItem.setText("Settings");
        arduinoSettingsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                arduinoSettingsItemActionPerformed(evt);
            }
        });
        jMenu3.add(arduinoSettingsItem);

        l_mainMenu.add(jMenu3);

        jMenu4.setText("Window");

        runFullScreenMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        runFullScreenMenu.setText("Full Screen");
        runFullScreenMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runFullScreenMenuActionPerformed(evt);
            }
        });
        jMenu4.add(runFullScreenMenu);

        runScreenSaverMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        runScreenSaverMenu.setText("Run Screensaver");
        runScreenSaverMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runScreenSaverMenuActionPerformed(evt);
            }
        });
        jMenu4.add(runScreenSaverMenu);

        l_mainMenu.add(jMenu4);

        setJMenuBar(l_mainMenu);

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void arduinoSettingsItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_arduinoSettingsItemActionPerformed
        try {
            ArduinoPanel arduinoPanel = new ArduinoPanel(this);
            installNewPanel(arduinoPanel);
        } catch (Exception ex) {
            error(ex);
        }
    }//GEN-LAST:event_arduinoSettingsItemActionPerformed

    private void exitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitItemActionPerformed

    private void dbConfigItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dbConfigItemActionPerformed
        try {
            DataBasePanel dataBasePanel = new DataBasePanel(this);
            installNewPanel(dataBasePanel);

        } catch (Exception ex) {
            error(ex);
        }
    }//GEN-LAST:event_dbConfigItemActionPerformed

    private void runFullScreenMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runFullScreenMenuActionPerformed
        this.dispose();
        this.setUndecorated(true);
        this.setResizable(false);
        this.validate();
        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this);
    }//GEN-LAST:event_runFullScreenMenuActionPerformed

    private void runScreenSaverMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runScreenSaverMenuActionPerformed
        new ProcessRunner().run(ProcessPaths.SCREENSAVER_PATH);
    }//GEN-LAST:event_runScreenSaverMenuActionPerformed

    private void newGameItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameItemActionPerformed
        startNewGame();
    }//GEN-LAST:event_newGameItemActionPerformed

    public void startNewGame() {
        try {
            gameMainPanel = new GameMainPanel(this);
            installNewPanel(gameMainPanel);
            setGameStarted(true);
        } catch (Exception ex) {
            error(ex);
        }
    }

    private void loadQuestionsItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadQuestionsItemActionPerformed
        XLSProcessor xlsProcessor = new XLSProcessor();
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(fc);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            xlsProcessor.setPath(file.getAbsolutePath());
            xlsProcessor.setMySqlConnection(mySqlConnection);
            xlsProcessor.setSheetIndex(0);
            xlsProcessor.read();
        }
    }//GEN-LAST:event_loadQuestionsItemActionPerformed

    private void webcamMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_webcamMenuItemActionPerformed
        try {
            if (videoPanel == null) {
                videoPanel = new VideoPanel(this);
            }
            installNewPanel(videoPanel);
        } catch (Exception ex) {
            error(ex);
        }
    }//GEN-LAST:event_webcamMenuItemActionPerformed

    public void installNewPanel(JComponent p_component) {
        Component l_last = getContentPane().getComponent(0);
        if (l_last != null) {
            getContentPane().remove(0);
        }
        getContentPane().add(p_component, java.awt.BorderLayout.CENTER);
        validate();
    }

    public void error(Exception ex) {
        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
    }

    public void event() {
        if (!gameStarted) {
            setGameStarted(true);
            super.setExtendedState(super.getExtendedState());
            webcamMenuItemActionPerformed(null);
        }
    }

    public void startDevice() {
        boolean init0 = getVideoDevice().init(0);
        if (init0) {
            getVideoDevice().setResolution(640, 480);
            getVideoDevice().start();
        } else {
            JOptionPane.showMessageDialog(null, "Can not start the device");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem arduinoSettingsItem;
    private javax.swing.JPanel conveyBrand;
    private com.convey.component.CustomPanel customPanel1;
    private javax.swing.JLabel dataBaseLabel;
    private eu.hansolo.steelseries.extras.Led dataBaseLed;
    private javax.swing.JMenuItem dbConfigItem;
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    public javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JPanel jPanel1;
    public javax.swing.JMenuBar l_mainMenu;
    private javax.swing.JMenuItem loadQuestionsItem;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuItem newGameItem;
    private javax.swing.JMenuItem runFullScreenMenu;
    private javax.swing.JMenuItem runScreenSaverMenu;
    private javax.swing.JMenuItem webcamMenuItem;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
