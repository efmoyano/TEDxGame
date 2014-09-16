package com.convey.properties;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @projectName TEDxGame
 * @package com.convey.utils
 * @filename PropertiesHandler.java
 * @encoding UTF-8
 * @author Ernesto Moyano
 * @date 15/09/2014 21:09:17
 */
public class PropertiesHandler extends Properties {

    private String fileName;
    public static final String PROP_FILENAME = "fileName";

    /**
     * Get the value of fileName
     *
     * @return the value of fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Set the value of fileName
     *
     * @param fileName new value of fileName
     */
    public void setFileName(String fileName) {
        String oldFileName = this.fileName;
        this.fileName = fileName;
        propertyChangeSupport.firePropertyChange(PROP_FILENAME, oldFileName, fileName);
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

    public void setProperties(Map<String, String> p_fields) {

        p_fields.keySet().stream().forEach((String key) -> {
            this.setProperty(key, p_fields.get(key));
        });
        try {
            PropertiesHandler.this.store(new FileOutputStream(fileName + ".properties"), fileName + " properties");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PropertiesHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PropertiesHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadProperties() {
        this.loadPropertiesFromFile(new File(fileName + ".properties"));
        if (this.isEmpty()) {
            System.out.println("Properties not loaded yet, loading defaults");
            this.loadDefaultProperties();
        }
    }

    public Properties loadDefaultProperties() {
        InputStream is = getClass().getResourceAsStream(fileName + ".properties");
        try {
            this.load(is);
        } catch (IOException ex) {
            Logger.getLogger(PropertiesHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(PropertiesHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this;
    }

    public Properties loadPropertiesFromFile(File p_inputFile) {
        InputStream is = null;
        if (p_inputFile.exists()) {
            try {
                is = new FileInputStream(p_inputFile);
                this.load(is);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PropertiesHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PropertiesHandler.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(PropertiesHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return this;
    }
}
