package com.convey.services;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @projectName TEDxGame
 * @package com.convey.services
 * @filename MySqlConnection.java
 * @encoding UTF-8
 * @author Ernesto Moyano
 * @date 07/09/2014 16:19:04
 */
public class MySqlConnection {

    private Connection f_conection;
    private Statement f_statement;
    private String f_password;
    private String f_user;
    private String f_ip;
    private String f_dataBase;

    public static final String PROP_DATABASE = "dataBase";

    /**
     * Get the value of f_dataBase
     *
     * @return the value of f_dataBase
     */
    public String getDataBase() {
        return f_dataBase;
    }

    /**
     * Set the value of f_dataBase
     *
     * @param p_dataBase new value of f_dataBase
     */
    public void setDataBase(String p_dataBase) {
        String oldDataBase = this.f_dataBase;
        this.f_dataBase = p_dataBase;
        propertyChangeSupport.firePropertyChange(PROP_DATABASE, oldDataBase, p_dataBase);
    }

    public static final String PROP_IP = "ip";

    /**
     * Get the value of f_ip
     *
     * @return the value of f_ip
     */
    public String getIp() {
        return f_ip;
    }

    /**
     * Set the value of f_ip
     *
     * @param ip new value of f_ip
     */
    public void setIp(String ip) {
        String oldIp = this.f_ip;
        this.f_ip = ip;
        propertyChangeSupport.firePropertyChange(PROP_IP, oldIp, ip);
    }

    public static final String PROP_USER = "user";

    /**
     * Get the value of f_user
     *
     * @return the value of f_user
     */
    public String getUser() {
        return f_user;
    }

    /**
     * Set the value of f_user
     *
     * @param user new value of f_user
     */
    public void setUser(String user) {
        String oldUser = this.f_user;
        this.f_user = user;
        propertyChangeSupport.firePropertyChange(PROP_USER, oldUser, user);
    }

    public static final String PROP_PASSWORD = "password";

    /**
     * Get the value of f_password
     *
     * @return the value of f_password
     */
    public String getPassword() {
        return f_password;
    }

    /**
     * Set the value of f_password
     *
     * @param password new value of f_password
     */
    public void setPassword(String password) {
        String oldPassword = this.f_password;
        this.f_password = password;
        propertyChangeSupport.firePropertyChange(PROP_PASSWORD, oldPassword, password);
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

    public boolean connect() {
        f_conection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            f_conection = DriverManager.getConnection("jdbc:mysql://" + f_ip + "/" + f_dataBase, f_user, f_password);

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        if (f_conection != null) {
            JOptionPane.showMessageDialog(null, "Conexion Exitosa");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo conectar a BD");
            return false;
        }
    }

    public void execute(String p_query) {
        try {
            f_statement = f_conection.createStatement();
            f_statement.execute(p_query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public ResultSet query(String p_query) {
        ResultSet reg = null;
        try {
            f_statement = f_conection.createStatement();
            reg = f_statement.executeQuery(p_query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return reg;
    }

    public void goToFirsRow(ResultSet consulta) {
        try {
            while (consulta.previous()) {
            }
        } catch (SQLException ex) {
        }
    }

    public void addPeople(String firstName, String lastName, String telephone, String email, String ivaDetail, String type) {
        try {
            CallableStatement callableStatement = f_conection.prepareCall("{ call AddPeople(?,?,?,?,?,?) }");
            callableStatement.setString(1, firstName);
            callableStatement.setString(2, lastName);
            callableStatement.setString(3, telephone);
            callableStatement.setString(4, email);
            callableStatement.setString(5, ivaDetail);
            callableStatement.setString(6, type);
            callableStatement.executeQuery();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar \n Revise los datos");
            Logger.getLogger(MySqlConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
