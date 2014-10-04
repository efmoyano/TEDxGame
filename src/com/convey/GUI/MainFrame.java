package com.convey.GUI;

import com.convey.hardware.arduino.ArduinoDevice;
import com.convey.hardware.arduino.ArduinoEventListener;
import com.convey.services.MySqlConnection;
import com.convey.utils.ProcessPaths;
import com.convey.utils.ProcessRunner;
import java.awt.Color;
import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
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
    public static final String PROP_MYSQLCONNECTION = "mySqlConnection";
    public static final String PROP_ARDUINODEVICE = "arduinoDevice";
    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private int proximityDistance = 0;
    public static final String PROP_PROXIMITYDISTANCE = "proximityDistance";
    private Color color;
    public static final String PROP_COLOR = "color";

    private GameMainPanel gameMainPanel;

    public static final String PROP_GAMEMAINPANEL = "gameMainPanel";

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
     * Get the value of color
     *
     * @return the value of color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Set the value of color
     *
     * @param color new value of color
     */
    public void setColor(Color color) {
        Color oldColor = this.color;
        this.color = color;
        propertyChangeSupport.firePropertyChange(PROP_COLOR, oldColor, color);
    }

    /**
     * Get the value of proximityDistance
     *
     * @return the value of proximityDistance
     */
    public int getProximityDistance() {
        return proximityDistance;
    }

    /**
     * Set the value of proximityDistance
     *
     * @param proximityDistance new value of proximityDistance
     */
    public void setProximityDistance(int proximityDistance) {
        int oldProximityDistance = this.proximityDistance;
        this.proximityDistance = proximityDistance;
        propertyChangeSupport.firePropertyChange(PROP_PROXIMITYDISTANCE, oldProximityDistance, proximityDistance);
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

    public MainFrame() {

        arduinoDevice = new ArduinoDevice();
        if (!arduinoDevice.isConnected()) {
            arduinoDevice.initialize();
        }

        mySqlConnection = new MySqlConnection();

        arduinoDevice.addArduinoEventListener(new ArduinoEventListener() {
            private String[] l_components;

            @Override
            public void onArduinoStateChanged(String p_status) {
            }

            @Override
            public void onCalculatePacketsPerSecond(int p_pps) {

            }

            @Override
            public void onArduinoConnected() {
            }

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

                        setProximityDistance(Integer.parseInt(l_components[1]));
                        if (proximityDistance < 50) {
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

        initComponents();

//        runFullScreenMenuActionPerformed(null);
//        runScreenSaverMenuActionPerformed(null);
//        setExtendedState(getExtendedState());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        l_mainMenu = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        newGameItem = new javax.swing.JMenuItem();
        exitItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        loadQuestionsItem = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        arduinoSettingsItem = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        runFullScreenMenu = new javax.swing.JMenuItem();
        runScreenSaverMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 738, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 498, Short.MAX_VALUE)
        );

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

    private void loadQuestionsItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadQuestionsItemActionPerformed
        try {
            DataBasePanel dataBasePanel = new DataBasePanel(this);
            installNewPanel(dataBasePanel);
        } catch (Exception ex) {
            error(ex);
        }
    }//GEN-LAST:event_loadQuestionsItemActionPerformed

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

        try {
            gameMainPanel = new GameMainPanel(this);
            installNewPanel(gameMainPanel);
        } catch (Exception ex) {
            error(ex);
        }

    }//GEN-LAST:event_newGameItemActionPerformed

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
        super.setExtendedState(super.getExtendedState());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem arduinoSettingsItem;
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    public javax.swing.JMenuBar l_mainMenu;
    private javax.swing.JMenuItem loadQuestionsItem;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuItem newGameItem;
    private javax.swing.JMenuItem runFullScreenMenu;
    private javax.swing.JMenuItem runScreenSaverMenu;
    // End of variables declaration//GEN-END:variables
}
