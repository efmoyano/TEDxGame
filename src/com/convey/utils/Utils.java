package com.convey.utils;

import java.awt.Color;

/**
 * @projectName TEDxGame
 * @package com.convey.utils
 * @filename Utils.java
 * @encoding UTF-8
 * @author Ernesto Moyano
 * @date 28/08/2014 22:32:50
 */
public class Utils {

    public static Color getRGB(int p_minRange, int p_maxRange, int p_value) {

        int l_red = 0;
        int l_green = 0;
        int l_blue = 0;
        double l_diff = (p_maxRange - p_minRange);
        double l_step = l_diff / 3;
        double l_scale = 255 / l_step;

        if (p_value < l_step) {
            l_red = (int) (p_value * l_scale);
        } else {
            if (p_value < (l_step * 2)) {
                p_value = p_value / 2;
                l_green = (int) (p_value * l_scale);
            } else {
                p_value = p_value / 3;
                l_blue = (int) (p_value * l_scale);
            }
        }
        return new Color(l_red, l_green, l_blue);
    }
}
