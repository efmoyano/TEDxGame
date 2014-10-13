package com.convey.GUI;

import com.convey.hardware.video.DeviceEventAdapter;
import com.convey.utils.ImageUtils;
import com.convey.utils.Utils;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.highgui.Highgui;

/**
 * @projectName TEDxGame
 * @package com.convey.GUI
 * @filename VideoPanel.java
 * @encoding UTF-8
 * @author Ernesto Moyano
 * @date 09/10/2014 19:16:28
 */
public final class VideoPanel extends javax.swing.JPanel {

    private MainFrame mainFrame;
    private int visorHeight;
    private int visorWidth;
    private Graphics2D graphics;
    private boolean screenShotCalled = false;
    private BufferedImage imageDraw;

    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public static final String PROP_VISORWIDTH = "visorWidth";
    public static final String PROP_VISORHEIGHT = "visorHeight";
    public static final String PROP_MAINFRAME = "mainFrame";

    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
    /**
     * Get the value of visorWidth
     *
     * @return the value of visorWidth
     */
    public int getVisorWidth() {
        return visorWidth;
    }

    /**
     * Set the value of visorWidth
     *
     * @param visorWidth new value of visorWidth
     */
    public void setVisorWidth(int visorWidth) {
        int oldVisorWidth = this.visorWidth;
        this.visorWidth = visorWidth;
        propertyChangeSupport.firePropertyChange(PROP_VISORWIDTH, oldVisorWidth, visorWidth);
    }

    /**
     * Get the value of visorHeight
     *
     * @return the value of visorHeight
     */
    public int getVisorHeight() {
        return visorHeight;
    }

    /**
     * Set the value of visorHeight
     *
     * @param visorHeight new value of visorHeight
     */
    public void setVisorHeight(int visorHeight) {
        int oldVisorHeight = this.visorHeight;
        this.visorHeight = visorHeight;
        propertyChangeSupport.firePropertyChange(PROP_VISORHEIGHT, oldVisorHeight, visorHeight);
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

    public VideoPanel(MainFrame p_mainFrame) {

        setMainFrame(p_mainFrame);

        initComponents();

        getMainFrame().getVideoDevice().addDeviceEventListener(new DeviceEventAdapter() {

            Mat originalImage;

            @Override
            public void imageCaptured(Mat p_image) {

                originalImage = p_image.clone();

                Rect l_detected = ImageUtils.detectFace(p_image);
                if (l_detected != null) {

                    if (!screenShotCalled) {

                        Highgui.imwrite("detected.jpg", new Mat(originalImage, l_detected));
                        paintComponent(graphics);
                        new Utils().playSound("face_detected.wav");
                        
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(VideoPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        getMainFrame().getVideoDevice().stop();
                        getMainFrame().getGameMainPanel().startGame();

                    } else {
                        imageDraw = ImageUtils.matToBufferedImage(p_image);
                        paintComponent(graphics);
                    }
                }
            }

            @Override
            public void onDeviceStarted() {
                eventScreenShoot(3000);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(ImageUtils.resizeImage(
                imageDraw, visorWidth, visorHeight), 0, 0, null);
    }

    public void eventScreenShoot(int p_delay) {
        screenShotCalled = true;
        new Thread(() -> {
            try {
                Thread.sleep(p_delay);
                screenShotCalled = false;
            } catch (InterruptedException ex) {
                Logger.getLogger(VideoPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        setLayout(new java.awt.BorderLayout());

        mainPanel.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 711, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 514, Short.MAX_VALUE)
        );

        add(mainPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        graphics = (Graphics2D) mainPanel.getGraphics();
        visorHeight = mainPanel.getHeight();
        visorWidth = mainPanel.getWidth();
    }//GEN-LAST:event_formComponentResized

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables
}
