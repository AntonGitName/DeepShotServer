package ru.spbau.mit.antonpp.deepshot.server.processing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.spbau.mit.antonpp.deepshot.server.Constants;
import ru.spbau.mit.antonpp.deepshot.server.database.Util;
import ru.spbau.mit.antonpp.deepshot.server.database.model.MLOutputRecord;
import ru.spbau.mit.antonpp.deepshot.server.database.service.InputRecordRepository;
import ru.spbau.mit.antonpp.deepshot.server.database.service.OutputRecordRepository;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author antonpp
 * @since 27/10/15
 */
@Component
public class ImageTask {

    @Autowired
    public InputRecordRepository inputRecordRepository;

    @Autowired
    public OutputRecordRepository outputRecordRepository;

    public void start(String username, String encodedImage, long filterId) {
        final ImageFilteringTask task = new ImageFilteringTask(encodedImage, filterId, username);
        new Thread(task).start();
    }

    private long addInputRecord(String username, String imageUrl, long styleId) {
        return inputRecordRepository.addInputRecord(username, imageUrl, styleId);
    }

    private long addOutputRecord(long taskId) {
        return outputRecordRepository.addOutputRecord(MLOutputRecord.Status.PROCESSING, null, taskId);
    }

    private void updateOutputRecord(long id, MLOutputRecord.Status status, String imageUrl) {
        outputRecordRepository.updateOutputRecord(id, status, imageUrl);
    }

    private void startProcessForImageUrl(String inputImageUrl, String outputImageUrl) throws IOException, InterruptedException {
        final String cmd = String.format("%s %s %s", Constants.IMAGE_EXECUTABLE, inputImageUrl, outputImageUrl);
        Process process = Runtime.getRuntime().exec(cmd);
        process.waitFor();
    }

    private class ImageFilteringTask implements Runnable {

        private final String encodedImage;
        private final long styleId;
        private final String username;

        private ImageFilteringTask(String encodedImage, long styleId, String username) {
            this.encodedImage = encodedImage;
            this.styleId = styleId;
            this.username = username;
        }

        @Override
        public void run() {
            try {
                final BufferedImage inputImage = Util.decodeImage(encodedImage);
                final String inputImageUrl = Util.saveImage(Util.ImageType.INPUT, inputImage);

                final long inputRecordId = addInputRecord(username, inputImageUrl, styleId);

                final long outputRecordId = addOutputRecord(inputRecordId);

                long start = System.currentTimeMillis();
                final String outputImageUrl = Util.saveImage(Util.ImageType.OUTPUT, null);
                startProcessForImageUrl(inputImageUrl, outputImageUrl);
                System.out.printf("Task %d, time Elapsed: %d\n", inputRecordId, (System.currentTimeMillis() - start) / 1000);

                updateOutputRecord(outputRecordId, MLOutputRecord.Status.READY, outputImageUrl);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
