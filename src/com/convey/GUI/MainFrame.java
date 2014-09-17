package com.convey.GUI;

import com.convey.hardware.arduino.ArduinoDevice;
import com.convey.hardware.arduino.ArduinoEventListener;
import com.convey.services.MySqlConnection;
import com.convey.utils.ProcessPaths;
import com.convey.utils.ProcessRunner;
import com.convey.utils.Utils;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

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

        mySqlConnection = new MySqlConnection();

        arduinoDevice.addArduinoEventListener(new ArduinoEventListener() {

            @Override
            public void onArduinoStateChanged(String p_status) {
                System.out.println("State changed : " + p_status);
            }

            @Override
            public void onCalculatePacketsPerSecond(int p_pps) {

            }

            @Override
            public void onArduinoConnected() {
                System.out.println("Arduino Connected");
            }

            @Override
            public void onMessageReceived(String p_message) {
                System.out.println(p_message);
                setProximityDistance(Integer.parseInt(p_message));
                setColor(Utils.getRGB(0, 50, Integer.parseInt(p_message)));

            }
        });

        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();

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
            .addGap(0, 496, Short.MAX_VALUE)
        );

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Exit");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("ScreenSaver");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Tools");

        jMenuItem2.setText("Load Questions");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Arduino");

        jMenuItem1.setText("Settings");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Window");

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setText("Full Screen");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem5);

        jMenuItem6.setText("Run Screensaver");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem6);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        try {
            ArduinoPanel arduinoPanel = new ArduinoPanel(this);
            installNewPanel(arduinoPanel);
        } catch (Exception ex) {
            error(ex);
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed

        try {
            DataBasePanel dataBasePanel = new DataBasePanel(this);
            installNewPanel(dataBasePanel);
        } catch (Exception ex) {
            error(ex);
        }

    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        ScreenSaver();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        this.dispose();
        this.setUndecorated(true);
        this.setResizable(false);
        this.validate();
        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        new ProcessRunner().run(ProcessPaths.SCREENSAVER_PATH);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Robot robot = new Robot();
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

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

    public void ScreenSaver() {

        try {

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            final JFrame screenSaverFrame = new JFrame();
            screenSaverFrame.setDefaultCloseOperation(
                    WindowConstants.EXIT_ON_CLOSE);
            screenSaverFrame.setUndecorated(true);
            screenSaverFrame.setResizable(false);
            screenSaverFrame.add(new JLabel("This is a Java Screensaver!",
                    SwingConstants.CENTER), BorderLayout.CENTER);
            screenSaverFrame.validate();
            GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice()
                    .setFullScreenWindow(screenSaverFrame);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables
}
