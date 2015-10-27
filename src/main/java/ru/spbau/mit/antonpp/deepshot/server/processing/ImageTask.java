package ru.spbau.mit.antonpp.deepshot.server.processing;

import org.springframework.beans.factory.annotation.Autowired;
import ru.spbau.mit.antonpp.deepshot.server.database.service.TaskRecordRepository;
import ru.spbau.mit.antonpp.deepshot.server.database.service.TaskResultRepository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * @author antonpp
 * @since 27/10/15
 */
public class ImageTask {

    @Autowired
    private static TaskRecordRepository taskRecordRepository;

    @Autowired
    private static TaskResultRepository taskResultRepository;

    private ImageTask() {
        throw new UnsupportedOperationException();
    }

    public static void start(long taskId, String encodedImage, long filterId) {
        final ImageFilteringTask task = new ImageFilteringTask(taskId, encodedImage, filterId);
        new Thread(task).start();
    }

    private static BufferedImage decodeImage(String encodedImage) throws IOException {
        final String encoded = encodedImage.replace("\n", "");
        final byte[] bytes = Base64.getDecoder().decode(encoded);
        final BufferedImage result;
        try (ByteArrayInputStream stream = new ByteArrayInputStream(bytes)) {
            result = ImageIO.read(stream);
        }
        return result;
    }

    private static String encodeImage(BufferedImage image) throws IOException {
        final byte[] bytes;
        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "jpg", baos);
            bytes = baos.toByteArray();
        }
        return Base64.getEncoder().encodeToString(bytes);
    }

    private static void saveTaskRecord(long id, String encodedImage, long filterId) {
        taskRecordRepository.addTaskRecord(id, encodedImage, filterId);
    }

    private static void saveTaskResult(String encodedImage, long taskId) {
        taskResultRepository.addTaskResult(encodedImage, taskId);
    }

    private static class ImageFilteringTask implements Runnable {

        private final long taskId;
        private final String encodedImage;
        private final long filterId;

        private ImageFilteringTask(long taskId, String encodedImage, long filterId) {
            this.taskId = taskId;
            this.encodedImage = encodedImage;
            this.filterId = filterId;
        }

        @Override
        public void run() {
            try {
                saveTaskRecord(taskId, encodedImage, filterId);
                final ImageFilter filter = ImageFilter.fromLong(filterId);
                final BufferedImage image = decodeImage(encodedImage);
                final BufferedImage filteredImage = filter.process(image);
                final String result = encodeImage(filteredImage);
                saveTaskResult(result, taskId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
