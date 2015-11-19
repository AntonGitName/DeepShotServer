package ru.spbau.mit.antonpp.deepshot.server.database;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

/**
 * @author antonpp
 * @since 13/11/15
 */
public class Util {

    private static final String IMAGES_FOLDER = "images/";

    private Util() {
        throw new UnsupportedOperationException();
    }

    public static String getEncodedImageFromUrl(String url) throws IOException {
        return encodeImage(ImageIO.read(new File(url)));
    }

    public static BufferedImage decodeImage(String encodedImage) throws IOException {
        final String encoded = encodedImage.replace("\n", "");
        final byte[] bytes = Base64.getDecoder().decode(encoded);
        final BufferedImage result;
        try (ByteArrayInputStream stream = new ByteArrayInputStream(bytes)) {
            result = ImageIO.read(stream);
        }
        return result;
    }

    public static String encodeImage(BufferedImage image) throws IOException {
        final byte[] bytes;
        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "jpg", baos);
            bytes = baos.toByteArray();
        }
        return Base64.getEncoder().encodeToString(bytes);
    }

    private static String createImageUrl(ImageType type) {
        File dir = new File(IMAGES_FOLDER + type + "/");
        dir.mkdirs();
        long id = 1;
        File imageFile = new File(dir.getAbsolutePath() + "/" + id + ".jpg");
        while (imageFile.exists()) {
            ++id;
            imageFile = new File(dir.getAbsolutePath() + "/" + id + ".jpg");
        }
        return imageFile.getAbsolutePath();
    }

    public static synchronized String saveImage(ImageType type, BufferedImage image) throws IOException {
        File imageFile = new File(createImageUrl(type));
        if (image != null) {
            ImageIO.write(image, "jpg", imageFile);
        } else {
            imageFile.createNewFile();
        }
        return imageFile.getAbsolutePath();
    }

    public static enum ImageType {
        INPUT, OUTPUT, STYLE
    }
}
