package com.convey.component;

import com.convey.utils.Utils;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.border.AbstractBorder;

/**
 * @projectName TEDxGame
 * @package com.convey.component
 * @filename CustomBorder.java
 * @encoding UTF-8
 * @author Ernesto Moyano
 * @date 05/10/2014 14:18:03
 */
public class CustomBorder extends AbstractBorder {

    private int l_gap;
    private int l_type;
    private int l_width;
    private int l_height;
    private transient BufferedImage image;

    /**
     *
     * @param p_type
     * @param p_gape
     */
    public CustomBorder(int p_type, int p_gape) {
        try {
            l_gap = p_gape;
            l_type = p_type;
            image = ImageIO.read(CustomBorder.class.getResourceAsStream("/res/images/border" + l_type + ".png"));
        } catch (IOException ex) {
            Logger.getLogger(CustomBorder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param c
     * @param g
     * @param x
     * @param y
     * @param width
     * @param height
     */
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        super.paintBorder(c, g, x, y, width, height);
        l_width = width;
        l_height = height;
        Graphics2D g2d;

        image = Utils.resizeImage(image, l_width, l_height);

        if (g instanceof Graphics2D) {
            g2d = (Graphics2D) g;
            g2d.drawImage(image, 0, 0, null);
        }
    }

    /**
     *
     * @param c
     * @return
     */
    @Override
    public Insets getBorderInsets(Component c) {
        return getBorderInsets(c, new Insets(l_gap, l_gap, l_gap, l_gap));
    }

    /**
     *
     * @param c
     * @param insets
     * @return
     */
    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.top = insets.right = insets.bottom = l_gap;
        return insets;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isBorderOpaque() {
        return true;
    }
}
