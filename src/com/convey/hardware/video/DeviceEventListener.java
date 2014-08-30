package com.convey.hardware.video;

import org.opencv.core.Mat;

/**
 * @projectName TEDxGame
 * @package com.convey.hardware.video
 * @filename DeviceEventListener.java
 * @encoding UTF-8
 * @author Ernesto Moyano
 * @date 28/08/2014 22:27:10
 */
public interface DeviceEventListener {

    /**
     * This method is triggered when new image is captured by the device
     *
     * @param p_imageOriginal an org.opencv.core.Mat structure containing the
     * RAW image captured in BGR format. Look ImageUtils class in the
     * ar.unsta.robotteam.imageproc from covnersion between Mat to BufferedImage
     */
    public void imageCaptured(Mat p_imageOriginal);

    /**
     * This method is triggered when the device is stopped
     */
    public void deviceStopped();

    /**
     *
     *
     * @param p_width
     * @param p_height
     */
    public void onResolutionChanged(int p_width, int p_height);

    /**
     * This method is triggered when the device is started
     */
    public void onDeviceStarted();

    /**
     * Checks how many frame per second were captured
     *
     * @param p_fps The calculated frames per second
     */
    public void onCalculateFPS(int p_fps);
}
