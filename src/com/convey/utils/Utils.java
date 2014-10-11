package com.convey.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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

    public static BufferedImage resizeImage(BufferedImage p_image, int p_width, int p_height) {
        if (p_image != null && p_width >= 0 && p_height >= 0) {
            int type;

            type = p_image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : p_image.getType();
            BufferedImage resizedImage = new BufferedImage(p_width, p_height, type);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(p_image, 0, 0, p_width, p_height, null);
            g.dispose();
            return resizedImage;
        } else {
            return p_image;
        }

    }

    public static int fromStringtoValue(String p_string) {

        switch (p_string) {
            case "A":
                return 1;
            case "B":
                return 2;
            case "C":
                return 3;
            case "D":
                return 4;
            default:
                return 0;
        }
    }

    public void playSound(String p_path) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(this.getClass().getResourceAsStream("/res/sounds/" + p_path));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }
}
