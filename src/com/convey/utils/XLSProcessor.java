package com.convey.utils;

import com.convey.services.MySqlConnection;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.JOptionPane;
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
    private MySqlConnection mySqlConnection;
    public static final String PROP_MYSQLCONNECTION = "mySqlConnection";
    public static final String PROP_PATH = "path";

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

    public void read() {
        int l_count = 0;
        if (f_path != null) {
            LinkedList<Object> l_question = new LinkedList<>();

            Workbook l_workbook = null;
            Sheet l_sheet;
            try {
                l_workbook = Workbook.getWorkbook(new File(f_path));
            } catch (IOException | BiffException ex) {
                JOptionPane.showMessageDialog(null, "Not supported file");
            }

            if (l_workbook != null) {
                l_sheet = l_workbook.getSheet(f_sheetIndex);

                for (int i = 1; i < l_sheet.getRows() - 1; i++) {

                    for (int j = 0; j < 7; j++) {
                        Cell l_cell = l_sheet.getCell(j, i);
                        if (j == 6) {
                            l_question.add(Utils.fromStringtoValue(l_cell.getContents()));
                        } else {
                            l_question.add(l_cell.getContents());
                        }
                    }
                    mySqlConnection.insertQuestion(
                            l_question.get(0).toString(),
                            l_question.get(1).toString(),
                            l_question.get(2).toString(),
                            l_question.get(3).toString(),
                            l_question.get(4).toString(),
                            Integer.parseInt(l_question.get(5).toString()),
                            Integer.parseInt(l_question.get(6).toString()));
                    l_question.removeAll(l_question);
                    l_count++;
                }
                JOptionPane.showMessageDialog(null, "Inserted " + l_count + " rows");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No file selected");
        }
    }
}
