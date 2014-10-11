package com.convey.utils;

/**
 * @projectName TEDxGame
 * @package com.convey.utils
 * @filename Animation.java
 * @encoding UTF-8
 * @author Ernesto Moyano
 * @date 04/10/2014 11:45:12
 */
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Animation {

    public static final int RUN_TIME = 150;
    private final JPanel panel;
    private final Rectangle from;
    private final Rectangle to;
    private long startTime;

    public Animation(JPanel panel, Rectangle from, Rectangle to) {
        this.panel = panel;
        this.from = from;
        this.to = to;
    }

    public void start() {
        Timer l_timer = new Timer(40, (ActionEvent e) -> {
            this.panel.setVisible(true);
            long l_duration = System.currentTimeMillis() - startTime;
            double l_progress = (double) l_duration / (double) RUN_TIME;
            if (l_progress > 1f) {
                l_progress = 1f;
                ((Timer) e.getSource()).stop();
                new Utils().playSound("question_start.wav");
            }
            Rectangle l_target = calculateProgress(from, to, l_progress);
            panel.setBounds(l_target);
        });
        l_timer.setRepeats(true);
        l_timer.setCoalesce(true);
        l_timer.setInitialDelay(0);
        startTime = System.currentTimeMillis();
        l_timer.start();
    }

    public static Rectangle calculateProgress(Rectangle p_startBounds, Rectangle p_targetBounds, double p_progress) {
        Rectangle l_bounds = new Rectangle();
        if (p_startBounds != null && p_targetBounds != null) {
            l_bounds.setLocation(calculateProgress(p_startBounds.getLocation(), p_targetBounds.getLocation(), p_progress));
            l_bounds.setSize(calculateProgress(p_startBounds.getSize(), p_targetBounds.getSize(), p_progress));
        }
        return l_bounds;
    }

    public static Point calculateProgress(Point p_startPoint, Point p_targetPoint, double p_progress) {
        Point l_point = new Point();
        if (p_startPoint != null && p_targetPoint != null) {
            l_point.x = calculateProgress(p_startPoint.x, p_targetPoint.x, p_progress);
            l_point.y = calculateProgress(p_startPoint.y, p_targetPoint.y, p_progress);
        }
        return l_point;
    }

    public static int calculateProgress(int p_startValue, int p_endValue, double p_fraction) {
        int l_value = 0;
        int l_distance = p_endValue - p_startValue;
        l_value = (int) Math.round((double) l_distance * p_fraction);
        l_value += p_startValue;
        return l_value;
    }

    public static Dimension calculateProgress(Dimension p_startSize, Dimension p_targetSize, double p_progress) {
        Dimension l_size = new Dimension();
        if (p_startSize != null && p_targetSize != null) {

            l_size.width = calculateProgress(p_startSize.width, p_targetSize.width, p_progress);
            l_size.height = calculateProgress(p_startSize.height, p_targetSize.height, p_progress);

        }
        return l_size;
    }
}
