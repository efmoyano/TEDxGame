package com.convey.utils;

import java.awt.EventQueue;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 * @projectName TEDxGame
 * @package com.convey.utils
 * @filename Utils.java
 * @encoding UTF-8
 * @author Ernesto Moyano
 * @date 28/08/2014 22:32:50
 */
public class Utils {

    /**
     *
     */
    protected Utils() {
    }

    // Clase estatica oculta. Tan solo se instanciara el singleton una vez
    private static class SingletonHolder {

        // El constructor de Singleton puede ser llamado desde aquí al ser protected
        private final static Utils INSTANCE = new Utils();
    }

    // Método para obtener la instancia de nuestra clase
    /**
     *
     * @return
     */
    public static Utils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * Método encargado de obtener las componentes de la posicion del sensor
     * depositando el valor de PAN en el índice 0 (cero) y el valor de TILT en
     * el índice 1 (uno)
     *
     * @param p_inputComponents
     * @param p_delimiter
     * @return
     */
    public static int[] getComponents(final String p_inputComponents, final String p_delimiter) {

        final String[] tokens = p_inputComponents.split(p_delimiter);
        int[] components = new int[tokens.length];

        for (int i = 0; i < tokens.length; i++) {
            try {
                components[i] = Integer.parseInt(tokens[i]);
            } catch (NumberFormatException e) {
                System.err.println(Utils.class.getName() + " Message Received Cannot Process");
            }
        }
        return components;
    }

    /**
     *
     * @param p_value
     * @param p_scale
     * @return
     */
    public static int changeScale(int p_value, double p_scale) {

        int l_adjust = (int) ((90 - p_value) / p_scale);
        int l_fixed = p_value + l_adjust;
        return l_fixed;

    }

    /**
     * Este método transforma el angulo que arriva desde el dispositivo en el
     * formato de [-179º , 179º] a la forma de [0º , º360]
     *
     * @param p_angle
     * @return
     */
    public static int getAngleConverted(int p_angle) {
        if (p_angle <= 0) {
            return p_angle + 360;
        } else {
            return p_angle;
        }   //endif
    }

    /**
     * Método para calibrar el ángulo de movilidad
     *
     * @param p_position Posicion actual del sensor
     * @param p_calibrationCenter Centro de calibración
     * @return Devuelve el angulo calibrado
     */
    public static int getCalibratedAngle(int p_position, int p_calibrationCenter) {
        int l_calibratedAngle;    // Ángulo calibrado
        int l_rangeLeft = Math.abs(p_calibrationCenter - 90);
        int l_rangeRight = Math.abs(p_calibrationCenter + 90);

        if (p_calibrationCenter < 90) {
            if (p_position <= l_rangeRight) {
                l_calibratedAngle = p_position + l_rangeLeft;
                return l_calibratedAngle;

            } else {
                int adjust = 360 - l_rangeLeft;
                l_calibratedAngle = Math.abs(p_position - adjust);
                return l_calibratedAngle;
            }
        } else {
            if (l_rangeRight >= 360) {

                // Aqui se calcula el valor ajustado si la posición excede los 360º
                int ex = l_rangeRight - 360;
                if (p_position <= ex) {
                    int adjusted = 360 + p_position;
                    l_calibratedAngle = adjusted - l_rangeLeft;
                    return l_calibratedAngle;
                } else {
                    l_calibratedAngle = p_position - l_rangeLeft;
                    return l_calibratedAngle;
                }   //endif
            } else {
                l_calibratedAngle = (p_position - l_rangeLeft);
                return l_calibratedAngle;
            }   // endif
        } // endif
    }

    /**
     *
     * @param p_listmodel
     * @param p_list
     * @param p_data
     * @param p_size
     */
    public static void updateLismodel(final DefaultListModel p_listmodel, final JList p_list, final String p_data, final int p_size) {
        EventQueue.invokeLater(() -> {
            p_listmodel.addElement(p_data);
            if (p_listmodel.size() - 1 >= 0) {
                p_list.ensureIndexIsVisible(p_listmodel.size() - 1);
            }
            if (p_listmodel.size() == p_size) {
                p_listmodel.remove(0);
            }
        });
    }

    /**
     *
     * @param p_value
     * @return
     */
    public static int unsignedInt(final int p_value) {
        return p_value >= 0 ? p_value : 256 + p_value;

    }

}
