package ru.spbau.mit.antonpp.deepshot.server;

import com.jhlabs.image.GaussianFilter;
import com.jhlabs.image.GrayscaleFilter;
import com.jhlabs.image.InvertFilter;
import com.jhlabs.image.SkeletonFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.spbau.mit.antonpp.deepshot.server.database.Util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author antonpp
 * @since 12/10/15
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException {

//        initStyle();

        SpringApplication.run(Application.class, args);
    }

    private static void initStyle() throws IOException {
        BufferedImage image = ImageIO.read(new File("input.jpg"));
        BufferedImage result;

        GaussianFilter gaussianFilter = new GaussianFilter(5);
        result = gaussianFilter.filter(image, null);

        write(result);

        SkeletonFilter skeletonFilter = new SkeletonFilter();
        result = skeletonFilter.filter(image, null);

        write(result);

        InvertFilter invertFilter = new InvertFilter();
        result = invertFilter.filter(image, null);

        write(result);

        GrayscaleFilter grayscaleFilter = new GrayscaleFilter();
        result = grayscaleFilter.filter(image, null);

        write(result);
    }

    private static void write(BufferedImage image) throws IOException {
        System.out.println(Util.saveImage(Util.ImageType.STYLE, image));
    }

}