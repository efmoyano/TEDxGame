package com.convey.hardware.arduino;

/**
 * @projectName TEDxGame
 * @package com.convey.hardware.arduino
 * @filename ArduinoEventListener.java
 * @encoding UTF-8
 * @author Ernesto Moyano
 * @date 28/08/2014 22:25:05
 */
public interface ArduinoEventListener {

    /**
     * Método encargado de manejar los estados del Arduino
     *
     * @param p_status
     */
    public void onArduinoStateChanged(String p_status);

    /**
     *
     * @param p_pps
     */
    public void onCalculatePacketsPerSecond(int p_pps);

    /**
     *
     */
    public void onArduinoConnected();

}
