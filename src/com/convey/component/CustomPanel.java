package com.convey.component;

import com.convey.utils.Utils;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * @projectName TEDxGame
 * @package com.convey.component
 * @filename CustomPanel.java
 * @encoding UTF-8
 * @author Ernesto Moyano
 * @date 05/10/2014 12:39:05
 */
public class CustomPanel extends JComponent {

    private transient BufferedImage imagenFondo;
    private Graphics2D g2;

    public CustomPanel() {
        try {
            imagenFondo = ImageIO.read(CustomPanel.class.getResourceAsStream("/res/images/wallpaper.png"));
        } catch (IOException ex) {
            Logger.getLogger(CustomPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setImageBackground(String p_imageResource) {
        try {
            imagenFondo = ImageIO.read(CustomPanel.class.getResourceAsStream("/res/images/" + p_imageResource));
        } catch (IOException ex) {
            Logger.getLogger(CustomPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {

        g2 = (Graphics2D) g.create();

        imagenFondo = Utils.resizeImage(imagenFondo, this.getWidth(), this.getHeight());

        g2.drawImage(imagenFondo, 0, 0, imagenFondo.getWidth(), imagenFondo.getHeight(), null);

    }
}
