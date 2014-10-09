package com.convey.hardware.video;

import com.googlecode.javacv.cpp.videoInputLib.videoInput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

/**
 * @projectName TEDxGame
 * @package com.convey.hardware.video
 * @filename VideoDevice.java
 * @encoding UTF-8
 * @author Ernesto Moyano
 * @date 28/08/2014 22:26:26
 */
public final class VideoDevice {

    private VideoCapture capture;
    private Mat originalImage;
    private List<DeviceEventListener> deviceListeners;
    private int m_index;
    private int framesPerSecond = 0;
    private boolean running = false;
    private Timer fpsTimer;
    private final List<String> resolutions = Arrays.asList(
            "1920x1080",
            "1280x720",
            "1024x576",
            "800x600",
            "640x480",
            "320x240");

    /**
     *
     * @return true the Video Device is running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Set the running state of the device
     *
     * @param p_running state
     */
    public void setRunning(boolean p_running) {
        this.running = p_running;
    }

    /**
     *
     * @return List of Standar resolutions
     */
    public List<String> getResolutions() {
        return resolutions;
    }

    /**
     * Sets the device event listener how trigger the events
     *
     * @param p_deviceListener
     */
    public void addDeviceEventListener(DeviceEventListener p_deviceListener) {
        if (deviceListeners == null) {
            deviceListeners = new LinkedList<>();
        }
        deviceListeners.add(p_deviceListener);
    }

    /**
     * Get a list of valid resolutions
     *
     * @return
     */
    public Collection<String> getValidResolutions() {

        Collection<String> l_validResolutions = new LinkedList<>();
        for (String l_res : resolutions) {
            String[] l_tokens = l_res.split("x");
            setResolution(Integer.valueOf(l_tokens[0]), Integer.valueOf(l_tokens[1]));
            String l_acepted = getCurrentResolution();
            if (!l_validResolutions.contains(l_acepted)) {
                l_validResolutions.add(l_acepted);
                System.out.println(l_acepted);
            } // end if
        } // end for
        return l_validResolutions;
    }

    /**
     *
     * @return An ArrayList containing the video devices
     */
    public ArrayList getDevices() {
        ArrayList l_devices = new ArrayList();

        for (int i = 0; i < videoInput.listDevices(); i++) {
            l_devices.add(i + ") " + videoInput.getDeviceName(i));
        }

        return l_devices;
    }

    /**
     * Initialize the video device
     *
     * @param p_index
     * @return true if the device start with no problems
     */
    public boolean init(int p_index) {

        capture = new VideoCapture(p_index);
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
        }
        if (capture.isOpened()) {
            running = true;
            m_index = p_index;
            initFpsCounter();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Stop the device
     */
    public void stop() {
        synchronized (this) {
            if (capture != null) {
                running = false;
                if (deviceListeners != null) {
                    deviceListeners.stream().forEach((listener) -> {
                        listener.deviceStopped();
                    });
                }
                fpsTimer.cancel();
                capture.release();

            }
        }

    }

    /**
     * This method starts the video device, you should call init() method after
     * call this
     *
     * @return
     */
    public boolean start() {

        try {
            originalImage = new Mat();

            Thread t = new Thread(() -> {
                if (deviceListeners != null) {
                    deviceListeners.stream().map((listener) -> {
                        listener.onResolutionChanged(getCurrentWidth(), getCurrentHeight());
                        return listener;
                    }).forEach((listener) -> {
                        listener.onDeviceStarted();
                    });
                }
                while (running) {
                    try {
                        capture.retrieve(originalImage);
                        if (deviceListeners != null) {
                            deviceListeners.stream().forEach((listener) -> {
                                listener.imageCaptured(originalImage);
                            });
                        }
                        framesPerSecond++;
                    } catch (Exception ex) {
                    }
                }
            });
            t.setName("Thread device " + m_index);
            t.start();
            return true;
        } catch (java.lang.UnsatisfiedLinkError ex) {
            System.out.println(VideoDevice.class.getName() + " Error 0x001: " + ex.getMessage());
            return false;
        } catch (java.lang.NoClassDefFoundError ex) {
            System.out.println(VideoDevice.class.getName() + " Error 0x002: " + ex.getMessage());
            return false;
        }
    }

    private void initFpsCounter() {

        fpsTimer = new Timer("Video device fps counter");
        fpsTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (deviceListeners != null) {
                    deviceListeners.stream().forEach((listener) -> {
                        listener.onCalculateFPS(framesPerSecond);
                    });
                }
                framesPerSecond = 0;

            }
        }, 0, 1000);
    }

    /**
     * Sets the resolution of the device
     *
     * @param p_width
     * @param p_height
     */
    public void setResolution(int p_width, int p_height) {

        try {
            capture.set(Highgui.CV_CAP_PROP_FRAME_HEIGHT, p_height);
            capture.set(Highgui.CV_CAP_PROP_FRAME_WIDTH, p_width);

            if (deviceListeners != null) {
                deviceListeners.stream().forEach((listener) -> {
                    listener.onResolutionChanged(getCurrentWidth(), getCurrentHeight());
                });
            }
        } catch (NoClassDefFoundError ex) {
            System.out.println(VideoDevice.class.getName() + " Error 0x003: " + ex.getMessage());
        }
    }

    /**
     *
     * @return The current device resolution
     */
    public String getCurrentResolution() {
        int width = getCurrentWidth();
        int height = getCurrentHeight();
        return width + "x" + height;
    }

    /**
     *
     * @return
     */
    public int getCurrentWidth() {
        return (int) capture.get(Highgui.CV_CAP_PROP_FRAME_WIDTH);
    }

    /**
     *
     * @return
     */
    public int getCurrentHeight() {
        return (int) capture.get(Highgui.CV_CAP_PROP_FRAME_HEIGHT);
    }

    /**
     * Adjust the focus
     *
     * @param p_focus
     */
    public void changeFocus(double p_focus) {

        capture.set(Highgui.CV_CAP_PROP_FOCUS, p_focus);
    }

    /**
     * Changes the zoom of the device
     *
     * @param p_zoom
     */
    public void changeZoom(double p_zoom) {
        capture.set(Highgui.CV_CAP_PROP_ZOOM, p_zoom);

    }
}
