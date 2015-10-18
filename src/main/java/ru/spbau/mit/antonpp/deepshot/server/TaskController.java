package ru.spbau.mit.antonpp.deepshot.server;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author antonpp
 * @since 12/10/15
 */
@RestController
public class TaskController {

    private static final InvertFilter FILTER = new InvertFilter();
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/task", method = RequestMethod.POST)
    public Task task(@RequestParam(value = "image", defaultValue = Constants.SMB) String encodedImage) throws IOException {

        encodedImage = encodedImage.replace("\n", "");

        System.out.println("encodedImage = [" + encodedImage + "]");

        final byte[] b = Base64.getDecoder().decode(encodedImage);

        final BufferedImage inputImage = ImageIO.read(new ByteArrayInputStream(b));
        final BufferedImage outputImage = FILTER.processImage(inputImage);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(outputImage, "jpg", baos);
        baos.flush();
        final byte[] imageInByte = baos.toByteArray();
        baos.close();

        final String encodedFilteredImage = Base64.getEncoder().encodeToString(imageInByte);

        return new Task(counter.incrementAndGet(), encodedFilteredImage);
    }
}