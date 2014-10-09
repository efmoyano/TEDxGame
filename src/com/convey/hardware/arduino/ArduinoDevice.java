package com.convey.hardware.arduino;

import com.convey.properties.PropertiesHandler;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TooManyListenersException;

/**
 * @projectName TEDxGame
 * @package com.convey.hardware.arduino
 * @filename ArduinoDevice.java
 * @encoding UTF-8
 * @author Ernesto Moyano
 * @date 28/08/2014 22:21:57
 */
public class ArduinoDevice implements SerialPortEventListener {

    private transient SerialPort serialPort;
    private transient BufferedReader f_input;
    private transient OutputStream f_output;
    private boolean f_connected = false;
    private transient int f_packetsPerSecond = 0;
    private List<ArduinoEventListener> f_listeners;
    private List<Integer> f_dataRates = Arrays.asList(300, 1200, 2400, 4800,
            9600, 14400, 19200, 28800, 38400, 57600, 115200);
    private Map<String, String> l_ArduioProperties;
    public static final String PROP_ARDUIOPROPERTIES = "ArduioProperties";
    private PropertiesHandler l_arduinoPropHandler;
    public static final String PROP_PROPERTIESHANDLER = "propertiesHandler";
    private String port;
    public static final String PROP_PORT = "port";
    private int dataRate;
    public static final String PROP_DATARATE = "dataRate";

    /**
     * Get the value of l_arduinoPropHandler
     *
     * @return the value of l_arduinoPropHandler
     */
    public PropertiesHandler getPropertiesHandler() {
        return l_arduinoPropHandler;
    }

    /**
     * Set the value of l_arduinoPropHandler
     *
     * @param propertiesHandler new value of l_arduinoPropHandler
     */
    public void setPropertiesHandler(PropertiesHandler propertiesHandler) {
        PropertiesHandler oldPropertiesHandler = this.l_arduinoPropHandler;
        this.l_arduinoPropHandler = propertiesHandler;
        propertyChangeSupport.firePropertyChange(PROP_PROPERTIESHANDLER, oldPropertiesHandler, propertiesHandler);
    }

    /**
     * Get the value of l_ArduioProperties
     *
     * @return the value of l_ArduioProperties
     */
    public Map<String, String> getArduioProperties() {
        return l_ArduioProperties;
    }

    /**
     * Set the value of l_ArduioProperties
     *
     * @param l_ArduioProperties new value of l_ArduioProperties
     */
    public void setArduioProperties(Map<String, String> l_ArduioProperties) {
        Map<String, String> oldL_ArduioProperties = this.l_ArduioProperties;
        this.l_ArduioProperties = l_ArduioProperties;
        propertyChangeSupport.firePropertyChange(PROP_ARDUIOPROPERTIES, oldL_ArduioProperties, l_ArduioProperties);
    }

    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * Constructor
     */
    public ArduinoDevice() {
        l_ArduioProperties = new HashMap<>();
        l_arduinoPropHandler = new PropertiesHandler();
        l_arduinoPropHandler.setFileName(this.getClass().getSimpleName());
        l_arduinoPropHandler.loadProperties();
        this.setPort(l_arduinoPropHandler.getProperty(PROP_PORT));
        this.setDataRate(Integer.parseInt(l_arduinoPropHandler.getProperty(PROP_DATARATE)));
    }

    /**
     *
     * Get data rates for Arduino devices
     *
     * @return
     */
    public List<Integer> getDataRates() {
        return f_dataRates;
    }

    /**
     *
     * Set the data rates for Arduino devices
     *
     * @param p_dataRates
     */
    public void setDataRates(final List<Integer> p_dataRates) {
        this.f_dataRates = p_dataRates;
    }

    /**
     * Return the state of the conection between the Arduino device
     *
     * @return
     */
    public boolean isConnected() {
        return f_connected;
    }

    /**
     *
     * Sets the state of Arduino Device
     *
     * @param connected
     */
    public void setConnected(final boolean connected) {
        this.f_connected = connected;
    }

    /**
     * Get f_port setted to connect to Arduino device
     *
     * @return
     */
    public String getPort() {
        return port;
    }

    /**
     * Set the f_port to connect to Arduino device
     *
     * @param port
     */
    public final void setPort(final String port) {
        this.port = port;
        l_ArduioProperties.put(PROP_PORT, this.port);
        String oldPort = this.port;
        this.port = port;
        propertyChangeSupport.firePropertyChange(PROP_PORT, oldPort, port);
    }

    /**
     * Get the data rate to be setted to Arduino device
     *
     * @return
     */
    public int getDataRate() {
        return dataRate;
    }

    /**
     * Set the data rate to the Arduino device
     *
     * @param dataRate
     */
    public final void setDataRate(final int dataRate) {
        this.dataRate = dataRate;
        l_ArduioProperties.put(PROP_DATARATE, this.dataRate + "");
        int oldDataRate = this.dataRate;
        this.dataRate = dataRate;
        propertyChangeSupport.firePropertyChange(PROP_DATARATE, oldDataRate, dataRate);
    }

    /**
     * Sets the Arduino Event Listener that triggerall events generated by
     * Arduino
     *
     * @param p_listsner
     */
    public void addArduinoEventListener(final ArduinoEventListener p_listsner) {
        if (f_listeners == null) {
            f_listeners = new LinkedList<>();
        }
        f_listeners.add(p_listsner);
    }

    /**
     * This method get a list of all ports avalaibles on the PC
     *
     * @return LinkedList containing all the ports
     */
    public LinkedList getAvailablesPorts() {
        final LinkedList l_ports = new LinkedList();
        try {
            final Enumeration l_puertoEnum = CommPortIdentifier.getPortIdentifiers();
            while (l_puertoEnum.hasMoreElements()) {
                final CommPortIdentifier actualPuertoID = (CommPortIdentifier) l_puertoEnum.nextElement();
                l_ports.add(actualPuertoID.getName());
            }
        } catch (UnsatisfiedLinkError ex) {
            System.err.println(ArduinoDevice.class.getName() + " Error 4x001 :" + ex.getMessage());
        } catch (NoClassDefFoundError ex) {
            System.err.println(ArduinoDevice.class.getName() + " Error 4x002 :" + ex.getMessage());
        }
        return l_ports;
    }

    /**
     * Initialize the serial f_port comunication
     */
    public void initialize() {
        initPacketsPerSecondCounter();
        CommPortIdentifier l_portID = null;
        final Enumeration l_puertoEnum = CommPortIdentifier.getPortIdentifiers();
        while (l_puertoEnum.hasMoreElements()) {
            CommPortIdentifier l_actualPortID = (CommPortIdentifier) l_puertoEnum.nextElement();
            if (port.equals(l_actualPortID.getName())) {
                l_portID = l_actualPortID;
                break;
            }
        }
        if (l_portID == null) {
            System.err.println(ArduinoDevice.class.getName() + " Error 4x003 :No ports avalaible");
            setConnected(false);
        } else {
            try {
                serialPort = (SerialPort) l_portID.open(this.getClass().getName(),
                        2000);
                serialPort.setSerialPortParams(dataRate,
                        SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);
                f_input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
                f_output = serialPort.getOutputStream();
                serialPort.addEventListener(this);
                serialPort.notifyOnDataAvailable(true);
                serialPort.disableReceiveTimeout();
                serialPort.enableReceiveThreshold(1);

                if (f_listeners != null) {
                    f_listeners.stream().map((listener) -> {
                        listener.onArduinoStateChanged("CONNECTED");
                        return listener;
                    }).forEach((listener) -> {
                        listener.onArduinoConnected();
                    });
                }

                setConnected(true);

            } catch (TooManyListenersException e) {
                setConnected(false);
                System.err.println(ArduinoDevice.class.getName() + " Error 4x004 :" + e.getMessage());
            } catch (PortInUseException e) {
                setConnected(false);
                System.err.println(ArduinoDevice.class.getName() + " Error 4x005 :" + e.getMessage());
            } catch (UnsupportedCommOperationException e) {
                setConnected(false);
                System.err.println(ArduinoDevice.class.getName() + " Error 4x006 :" + e.getMessage());
            } catch (IOException e) {
                setConnected(false);
                System.err.println(ArduinoDevice.class.getName() + " Error 4x007 :" + e.getMessage());
            }
        }
    }

    /**
     * This method should be called to allow serial flush for systems like linux
     */
    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
            if (f_listeners != null) {
                f_listeners.stream().forEach((listener) -> {
                    listener.onArduinoStateChanged("DISCONNECTED");
                });
            }
            setConnected(false);
        }
    }

    /**
     * This method is triggered when data is present in the serial f_port
     *
     * @param serialPortEvent
     */
    @Override
    public synchronized void serialEvent(SerialPortEvent serialPortEvent) {
        if (serialPortEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            if (f_listeners != null) {
                f_listeners.stream().forEach((ArduinoEventListener listener) -> {
                    try {
                        listener.onMessageReceived(f_input.readLine());
                    } catch (IOException e) {
                        System.err.println(ArduinoDevice.class.getName() + " Error 4x008 :" + e.getMessage());
                    }
                });
            }
        }
    }

    private void initPacketsPerSecondCounter() {
        final Timer l_timerMetters = new Timer("Arduino pps Counter");
        l_timerMetters.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (f_listeners != null) {
                    f_listeners.stream().forEach((listener) -> {
                        listener.onCalculatePacketsPerSecond(f_packetsPerSecond);
                    });
                }
                f_packetsPerSecond = 0;
            }
        }, 0, 1000);
    }

    /**
     * Send the data over the network
     *
     * @param p_data
     */
    public void sendData(final String p_data) {
        if (f_output != null) {
            try {
                f_output.write((p_data + "\n").getBytes());
                f_packetsPerSecond++;
            } catch (IOException ex) {
                System.err.println(ArduinoDevice.class
                        .getName()
                        + " Error 4x009 :" + ex.getMessage());
            }
        }
    }

    /**
     * Send the data over the network
     *
     * @param p_data
     */
    public void sendData(final int p_data) {
        if (f_output != null) {

            try {
                f_output.write((p_data + "\n").getBytes());
                f_packetsPerSecond++;
            } catch (IOException ex) {
                System.err.println(ArduinoDevice.class
                        .getName()
                        + " Error 4x010 :" + ex.getMessage());
            }
        }
    }
}
