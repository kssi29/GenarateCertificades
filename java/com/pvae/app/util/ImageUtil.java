package com.pvae.app.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageUtil {

    public static String encodeImageToBase64(String imagePath) {
        try (FileInputStream imageInFile = new FileInputStream(imagePath)) {
            byte[] imageData = new byte[(int) new File(imagePath).length()];
            imageInFile.read(imageData);
            return Base64.getEncoder().encodeToString(imageData);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
