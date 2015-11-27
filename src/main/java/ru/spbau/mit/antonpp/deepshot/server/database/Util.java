package ru.spbau.mit.antonpp.deepshot.server.database;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

/**
 * @author antonpp
 * @since 13/11/15
 */
public class Util {

    private static final String IMAGES_FOLDER = "images/";
    private static final char[] symbols;

    static {
        final StringBuilder tmp = new StringBuilder();
        for (char ch = '0'; ch <= '9'; ++ch) {
            tmp.append(ch);
        }
        for (char ch = 'a'; ch <= 'z'; ++ch) {
            tmp.append(ch);
        }
        for (char ch = 'A'; ch <= 'Z'; ++ch) {
            tmp.append(ch);
        }
        symbols = tmp.toString().toCharArray();
    }

    private static Random RND = new Random();
    private static int FILE_NAME_LENGTH = 32;

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

    private static String generateRandomFilename() {
        final StringBuilder builder = new StringBuilder(FILE_NAME_LENGTH + 4);
        for (int i = 0; i < FILE_NAME_LENGTH; ++i) {
            builder.append(symbols[RND.nextInt(symbols.length)]);
        }
        builder.append(".jpg");
        return builder.toString();
    }

    private static String createImageUrl(ImageType type) {
        File dir = new File(IMAGES_FOLDER + type + "/");
        dir.mkdirs();
        String fname = generateRandomFilename();
        File imageFile = new File(dir.getAbsolutePath() + "/" + fname);
        while (imageFile.exists()) {
            fname = generateRandomFilename();
            imageFile = new File(dir.getAbsolutePath() + "/" + fname);
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
