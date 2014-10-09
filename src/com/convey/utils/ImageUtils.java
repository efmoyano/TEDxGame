package com.convey.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

/**
 * @projectName TEDxGame
 * @package com.convey.utils
 * @filename ImageUtils.java
 * @encoding UTF-8
 * @author Ernesto Moyano
 * @date 09/10/2014 19:19:58
 */
public class ImageUtils {

    /**
     * This method resize an BufferedImage to the width and height setted
     *
     * @param p_image Source image to resize
     * @param p_width Width to resize
     * @param p_height Height to resize
     * @return The BufferedImage Resized
     */
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

    /**
     *
     * @param p_image
     * @param p_x
     * @param p_y
     * @return
     */
    public static BufferedImage displaceImage(BufferedImage p_image, int p_x, int p_y) {
        if (p_image != null) {
            int type;
            int width = p_image.getWidth();
            int height = p_image.getWidth();

            type = p_image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : p_image.getType();
            BufferedImage displaceImage = new BufferedImage(width, height, type);
            Graphics2D g = displaceImage.createGraphics();
            g.drawImage(p_image, p_x, p_y, width, height, null);
            g.dispose();
            return displaceImage;
        } else {
            return p_image;
        }
    }

    /**
     * Combine two images into a single one side by side
     *
     * @param p_left The left image to merge
     * @param p_right Thre right image to merge
     * @return
     */
    public static BufferedImage mergeImages(BufferedImage p_left, BufferedImage p_right) {

        BufferedImage merged = null;

        if (p_left != null && p_right != null) {
            merged = new BufferedImage(
                    p_left.getWidth() + p_right.getWidth(), p_left.getHeight(), BufferedImage.TYPE_INT_RGB);

            Graphics2D g = (Graphics2D) merged.getGraphics();

            g.drawImage(p_left, 0, 0, null);
            g.drawImage(p_right, p_left.getWidth(null), 0, null);
        }

        return merged;
    }

    /**
     *
     * @param p_image
     * @return
     */
    public static float getImageSizeInKb(BufferedImage p_image) {

        int lenght = getImageSizeInBytes(p_image);

        float size = (float) lenght / (8 * 1024);

        return size;

    }

    /**
     *
     * @param p_image
     * @return
     */
    public static int getImageSizeInBytes(BufferedImage p_image) {

        byte[] imageInByte = ((DataBufferByte) p_image.getData().getDataBuffer()).getData();

        return imageInByte.length;

    }

    /**
     *
     * @param p_image
     * @return
     */
    public static byte[] compressImage(BufferedImage p_image) {
        if (p_image != null) {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(p_image, "jpg", baos);
                baos.flush();
                return baos.toByteArray();
            } catch (IOException | IndexOutOfBoundsException | NullPointerException ex) {
                System.err.println(ImageUtils.class.getName() + " Error 6x002: " + ex.getMessage());
            }
        }
        return new ByteArrayOutputStream().toByteArray();
    }

    /**
     * Perform the conversion between org.opencv.core.Mat type to
     * java.awt.image.BufferedImage. This method is very useful to display the
     * OpenCV structure in java.swing or java.awt containers.
     *
     * @param mat The array containing the original image
     * @return An RGB converted image
     */
    public static BufferedImage matToBufferedImage(Mat mat) {

        if (mat.height() > 0 && mat.width() > 0) {
            BufferedImage image = new BufferedImage(mat.width(), mat.height(), BufferedImage.TYPE_3BYTE_BGR);
            WritableRaster raster = image.getRaster();
            DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
            byte[] data = dataBuffer.getData();
            mat.get(0, 0, data);

            return image;
        }

        return null;
    }

    public static BufferedImage toBufferedImage(Mat m) {
        if (m.height() > 0 && m.width() > 0) {
            int type = BufferedImage.TYPE_BYTE_GRAY;
            if (m.channels() > 1) {
                type = BufferedImage.TYPE_3BYTE_BGR;
            }
            byte[] b = new byte[m.channels() * m.cols() * m.rows()];
            m.get(0, 0, b);
            BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
            final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
            System.arraycopy(b, 0, targetPixels, 0, b.length);
            return image;
        }
        return null;
    }

    /**
     *
     * @param p_sourceImage
     * @return
     */
    public static Mat bufferedImageToMat(BufferedImage p_sourceImage) {
        byte[] data = ((DataBufferByte) p_sourceImage.getRaster().getDataBuffer()).getData();
        Mat mat = new Mat(p_sourceImage.getHeight(), p_sourceImage.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, data);
        return mat;
    }

    /**
     * This method load an RGB image from .jpg, .gif, .png, .bmp and .jpeg
     * extensions
     *
     * @return The loaded image selected with the JFileChooser
     */
    public static Mat loadImage() {

        JFileChooser l_chooser = new JFileChooser();
        l_chooser.setFileFilter(
                new FileNameExtensionFilter(
                        "Image Types",
                        "jpg",
                        "gif",
                        "png",
                        "bmp",
                        "jpeg"));
        l_chooser.showOpenDialog(null);
        File f = l_chooser.getSelectedFile();
        if (f != null) {
            return Highgui.imread(
                    f.getAbsolutePath());
        }
        return null;
    }
}
