package com.aloha;

import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.image.BufferedImage;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class ImageUtils {
 
    /**
     * Make QR Code
     * @param text
     * @param width
     * @param height
     * @return
     */
    public static byte[] makeQR(String text, int width, int height) {
        byte[] data = null;
        // Generate QR Code
        BitMatrix encode;
        try {
            encode = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(encode, "PNG", out);
            data = out.toByteArray();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Make Image to Transparent Image
     * @param file
     * @return
     */
    public static Image toTransparentImage(File file) {
        try {
            BufferedImage bufferedImage = ImageIO.read(file);

            BufferedImage transparentImage 
            = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), bufferedImage.TYPE_INT_ARGB);

            // background white to transparnet
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                for (int x = 0; x < bufferedImage.getWidth(); x++) {
                    int rgba = bufferedImage.getRGB(x, y);
                    Color color = new Color(rgba, true);
                    if( color.getRed() == 255 && color.getGreen() == 255 && color.getBlue() == 255 ) {
                        // white ➡ 🧊
                        transparentImage.setRGB(x, y, 0x00FFFFFF);
                    } else {
                        // qr color
                        transparentImage.setRGB(x, y, rgba);
                    }
                }
            }
            Image fxImage = SwingFXUtils.toFXImage(transparentImage, null);
            return fxImage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    

    /**
     * Image to File
     * @param image
     * @param file
     */
    public static void saveImageToFile(Image image, File file) {
        try {
            WritableImage writableImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());
            PixelReader pixelReader = image.getPixelReader();

            // Image(javaFX) to File
            for (int y = 0; y < writableImage.getHeight(); y++) {
                for (int x = 0; x < writableImage.getWidth(); x++) {
                    writableImage.getPixelWriter().setArgb(x, y, pixelReader.getArgb(x, y));
                }
            }

            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);

            ImageIO.write(bufferedImage, "png", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
