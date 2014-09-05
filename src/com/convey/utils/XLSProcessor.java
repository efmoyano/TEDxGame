package com.convey.utils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.*;
import jxl.read.biff.BiffException;

/**
 * @projectName TEDxGame
 * @package com.convey.utils
 * @filename XLSProcessor.java
 * @encoding UTF-8
 * @author Ernesto Moyano
 * @date 28/08/2014 22:20:04
 */
public class XLSProcessor {

    private String f_path;

    private int f_sheetIndex;

    public static final String PROP_SHEETINDEX = "sheetIndex";

    /**
     * Get the value of f_sheetIndex
     *
     * @return the value of f_sheetIndex
     */
    public int getSheetIndex() {
        return f_sheetIndex;
    }

    /**
     * Set the value of f_sheetIndex
     *
     * @param sheetIndex new value of f_sheetIndex
     */
    public void setSheetIndex(int sheetIndex) {
        int oldSheetIndex = this.f_sheetIndex;
        this.f_sheetIndex = sheetIndex;
        propertyChangeSupport.firePropertyChange(PROP_SHEETINDEX, oldSheetIndex, sheetIndex);
    }

    public static final String PROP_PATH = "path";

    /**
     * Get the value of f_path
     *
     * @return the value of f_path
     */
    public String getPath() {
        return f_path;
    }

    /**
     * Set the value of f_path
     *
     * @param path new value of f_path
     */
    public void setPath(String path) {
        String oldPath = this.f_path;
        this.f_path = path;
        propertyChangeSupport.firePropertyChange(PROP_PATH, oldPath, path);
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

    public void test() {
        File l_fileSheet = new File(f_path);;
        Workbook l_workbook = null;
        Sheet l_sheet;
        try {
            l_workbook = Workbook.getWorkbook(l_fileSheet);
        } catch (IOException | BiffException ex) {
            System.out.println("Error loading file");
            Logger.getLogger(XLSProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (l_workbook != null) {
            l_sheet = l_workbook.getSheet(f_sheetIndex);
            Cell a1 = l_sheet.getCell(0, 0);
            Cell b2 = l_sheet.getCell(0, 1);
            Cell c2 = l_sheet.getCell(0, 2);

            String stringa1 = a1.getContents();
            String stringb2 = b2.getContents();
            String stringc2 = c2.getContents();

            System.out.println(stringa1);
            System.out.println(stringb2);
            System.out.println(stringc2);
        }
    }
}
