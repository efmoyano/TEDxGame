package com.convey.GUI;

import com.convey.hardware.video.DeviceEventAdapter;
import com.convey.utils.ImageUtils;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.opencv.core.Mat;

/**
 * @projectName TEDxGame
 * @package com.convey.GUI
 * @filename VideoPanel.java
 * @encoding UTF-8
 * @author Ernesto Moyano
 * @date 09/10/2014 19:16:28
 */
public final class VideoPanel extends javax.swing.JPanel {

    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private MainFrame mainFrame;
    public static final String PROP_MAINFRAME = "mainFrame";
    private int visorHeight;
    public static final String PROP_VISORHEIGHT = "visorHeight";
    private int visorWidth;
    public static final String PROP_VISORWIDTH = "visorWidth";
    private Graphics2D graphics;

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

    public VideoPanel(MainFrame p_mainFrame) {

        setMainFrame(p_mainFrame);

        initComponents();

        getMainFrame().getVideoDevice().addDeviceEventListener(new DeviceEventAdapter() {

            @Override
            public void imageCaptured(Mat p_image) {

                final BufferedImage image = ImageUtils.matToBufferedImage(p_image);

                SwingUtilities.invokeLater(() -> {

                    graphics.drawImage(ImageUtils.resizeImage(
                            image, visorWidth, visorHeight), 0, 0, null);
                });
            }

            @Override
            public void deviceStopped() {
                revalidate();
                repaint();
            }

            @Override
            public void onDeviceStarted() {
                graphics = (Graphics2D) l_visor0.getGraphics();
            }
        });

        boolean init0 = getMainFrame().getVideoDevice().init(0);
        if (init0) {
            getMainFrame().getVideoDevice().setResolution(640, 480);
            getMainFrame().getVideoDevice().start();
        } else {
            JOptionPane.showMessageDialog(null, "Can not start the device");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        l_visor0 = new javax.swing.JLabel();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        setLayout(new java.awt.BorderLayout());

        l_visor0.setMaximumSize(new java.awt.Dimension(3840, 2160));
        l_visor0.setMinimumSize(new java.awt.Dimension(320, 240));
        l_visor0.setPreferredSize(new java.awt.Dimension(320, 240));
        add(l_visor0, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        graphics = (Graphics2D) l_visor0.getGraphics();
        visorHeight = l_visor0.getHeight();
        visorWidth = l_visor0.getWidth();

    }//GEN-LAST:event_formComponentResized

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel l_visor0;
    // End of variables declaration//GEN-END:variables
}
