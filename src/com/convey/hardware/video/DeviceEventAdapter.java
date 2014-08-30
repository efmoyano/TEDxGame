package com.convey.hardware.video;

import org.opencv.core.Mat;

/**
 * @projectName TEDxGame
 * @package com.convey.hardware.video
 * @filename DeviceEventAdapter.java
 * @encoding UTF-8
 * @author Ernesto Moyano
 * @date 28/08/2014 22:27:42
 */
public class DeviceEventAdapter implements DeviceEventListener {

    @Override
    public void imageCaptured(Mat p_imageOriginal) {
    }

    @Override
    public void deviceStopped() {
    }

    @Override
    public void onResolutionChanged(int p_width, int p_height) {
    }

    @Override
    public void onDeviceStarted() {
    }

    @Override
    public void onCalculateFPS(int p_fps) {
    }

}
