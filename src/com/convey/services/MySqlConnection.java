package com.convey.services;

import com.convey.properties.PropertiesHandler;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
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
public final class MySqlConnection {

    private Connection conection;
    private Statement statement;
    private String password;
    public static final String PROP_PASSWORD = "password";
    private String user;
    public static final String PROP_USER = "user";
    private String ip;
    public static final String PROP_IP = "ip";
    private String dataBase;
    public static final String PROP_DATABASE = "dataBase";
    private PropertiesHandler l_mysqlPropertiesHandler;
    public static final String PROP_MYSQLPROPERTIESHANDLER = "MysqlPropertiesHandler";
    private Map<String, String> l_mySqlProperties;
    private String status = "Disconnected";
    public static final String PROP_STATUS = "status";

    /**
     * Get the value of status
     *
     * @return the value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set the value of status
     *
     * @param status new value of status
     */
    public void setStatus(String status) {
        String oldStatus = this.status;
        this.status = status;
        propertyChangeSupport.firePropertyChange(PROP_STATUS, oldStatus, status);
    }

    /**
     * Get the value of l_mySqlProperties
     *
     * @return the value of l_mySqlProperties
     */
    public Map<String, String> getMySqlproperties() {
        return l_mySqlProperties;
    }

    /**
     * Set the value of l_mySqlProperties
     *
     * @param MySqlproperties new value of l_mySqlProperties
     */
    public void setMySqlproperties(Map<String, String> MySqlproperties) {
        this.l_mySqlProperties = MySqlproperties;
    }

    /**
     * Get the value of l_mysqlPropertiesHandler
     *
     * @return the value of l_mysqlPropertiesHandler
     */
    public PropertiesHandler getMysqlPropertiesHandler() {
        return l_mysqlPropertiesHandler;
    }

    /**
     * Set the value of l_mysqlPropertiesHandler
     *
     * @param MysqlPropertiesHandler new value of l_mysqlPropertiesHandler
     */
    public void setMysqlPropertiesHandler(PropertiesHandler MysqlPropertiesHandler) {
        PropertiesHandler oldMysqlPropertiesHandler = this.l_mysqlPropertiesHandler;
        this.l_mysqlPropertiesHandler = MysqlPropertiesHandler;
        propertyChangeSupport.firePropertyChange(PROP_MYSQLPROPERTIESHANDLER, oldMysqlPropertiesHandler, MysqlPropertiesHandler);
    }

    public MySqlConnection() {
        l_mySqlProperties = new HashMap<>();
        l_mysqlPropertiesHandler = new PropertiesHandler();
        l_mysqlPropertiesHandler.setFileName(this.getClass().getSimpleName());
        l_mysqlPropertiesHandler.loadProperties();
        this.setIp(l_mysqlPropertiesHandler.getProperty(PROP_IP));
        this.setDataBase(l_mysqlPropertiesHandler.getProperty(PROP_DATABASE));
        this.setUser(l_mysqlPropertiesHandler.getProperty(PROP_USER));
        this.setPassword(l_mysqlPropertiesHandler.getProperty(PROP_PASSWORD));
        setStatus("Disconnected");
    }

    /**
     * Get the value of f_dataBase
     *
     * @return the value of f_dataBase
     */
    public String getDataBase() {
        return dataBase;
    }

    /**
     * Set the value of f_dataBase
     *
     * @param p_dataBase new value of f_dataBase
     */
    public final void setDataBase(String p_dataBase) {
        String oldDataBase = this.dataBase;
        l_mySqlProperties.put(PROP_DATABASE, this.dataBase);
        this.dataBase = p_dataBase;
        propertyChangeSupport.firePropertyChange(PROP_DATABASE, oldDataBase, p_dataBase);
    }

    /**
     * Get the value of f_ip
     *
     * @return the value of f_ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * Set the value of f_ip
     *
     * @param ip new value of f_ip
     */
    public final void setIp(String ip) {
        String oldIp = this.ip;
        l_mySqlProperties.put(PROP_IP, this.ip);
        this.ip = ip;
        propertyChangeSupport.firePropertyChange(PROP_IP, oldIp, ip);
    }

    /**
     * Get the value of f_user
     *
     * @return the value of f_user
     */
    public String getUser() {
        return user;
    }

    /**
     * Set the value of f_user
     *
     * @param user new value of f_user
     */
    public final void setUser(String user) {
        String oldUser = this.user;
        l_mySqlProperties.put(PROP_USER, this.user);
        this.user = user;
        propertyChangeSupport.firePropertyChange(PROP_USER, oldUser, user);
    }

    /**
     * Get the value of f_password
     *
     * @return the value of f_password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the value of f_password
     *
     * @param password new value of f_password
     */
    public final void setPassword(String password) {
        String oldPassword = this.password;
        l_mySqlProperties.put(PROP_PASSWORD, this.password);
        this.password = password;
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
        conection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conection = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + dataBase, user, password);

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        if (conection != null) {
            setStatus("Connected");
            return true;
        } else {
            System.out.println("Could not connect to DB");
            setStatus("Disconnected");
            return false;
        }
    }

    public void execute(String p_query) {
        try {
            statement = conection.createStatement();
            statement.execute(p_query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public ResultSet query(String p_query) {
        ResultSet reg = null;
        try {
            statement = conection.createStatement();
            reg = statement.executeQuery(p_query);
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

    public ResultSet getQuestion() {
        try {
            CallableStatement callableStatement = conection.prepareCall("{ call getQuestion() }");
            return callableStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(MySqlConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public void insertQuestion(String p_question, String p_answer1, String p_answer2, String p_answer3, String p_answer4, int p_difficulty, int p_correct) {
        try {
            CallableStatement callableStatement = conection.prepareCall("{ call insertQuestion(?,?,?,?,?,?,?) }");
            callableStatement.setString(1, p_question);
            callableStatement.setString(2, p_answer1);
            callableStatement.setString(3, p_answer2);
            callableStatement.setString(4, p_answer3);
            callableStatement.setString(5, p_answer4);
            callableStatement.setInt(6, p_difficulty);
            callableStatement.setInt(7, p_correct);
            callableStatement.executeQuery();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error loading data");
            Logger.getLogger(MySqlConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
