package ru.spbau.mit.antonpp.deepshot.server.processing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.spbau.mit.antonpp.deepshot.server.Constants;
import ru.spbau.mit.antonpp.deepshot.server.database.Util;
import ru.spbau.mit.antonpp.deepshot.server.database.model.MLOutputRecord;
import ru.spbau.mit.antonpp.deepshot.server.database.service.InputRecordRepository;
import ru.spbau.mit.antonpp.deepshot.server.database.service.OutputRecordRepository;
import ru.spbau.mit.antonpp.deepshot.server.gcm.GcmSender;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author antonpp
 * @since 27/10/15
 */
@Component
public class ImageTask {

    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private final AtomicBoolean isGpuUsed = new AtomicBoolean(false);

    @Autowired
    public InputRecordRepository inputRecordRepository;
    @Autowired
    public OutputRecordRepository outputRecordRepository;

    public void start(String username, String encodedImage, long filterId, String gcmToken, boolean useGpu) {
        final ImageFilteringTask task = new ImageFilteringTask(encodedImage, filterId, username, gcmToken, useGpu);
        threadPool.execute(task);
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

    private void startProcessForImageUrl(String inputImageUrl, String outputImageUrl, boolean useGpu) throws IOException, InterruptedException {
        final String cmd = String.format("%s %s %s %s", Constants.IMAGE_EXECUTABLE, inputImageUrl, outputImageUrl, useGpu);
        Process process = Runtime.getRuntime().exec(cmd);
        process.waitFor();
    }

    private class ImageFilteringTask implements Runnable {

        private final String encodedImage;
        private final long styleId;
        private final String username;
        private final String gcmToken;
        private boolean useGpu;

        private ImageFilteringTask(String encodedImage, long styleId, String username, String gcmToken, boolean useGpu) {
            this.encodedImage = encodedImage;
            this.styleId = styleId;
            this.username = username;
            this.gcmToken = gcmToken;
            this.useGpu = useGpu;
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
                if (useGpu) {
                    if (isGpuUsed.get()) {
                        useGpu = false;
                    } else {
                        isGpuUsed.set(true);
                    }
                }
                System.out.printf("GPU used: %s\n", useGpu);
                startProcessForImageUrl(inputImageUrl, outputImageUrl, useGpu);
                System.out.printf("Task %d, time Elapsed: %d\n", inputRecordId, (System.currentTimeMillis() - start) / 1000);
                if (useGpu) {
                    isGpuUsed.set(false);
                }

                updateOutputRecord(outputRecordId, MLOutputRecord.Status.READY, outputImageUrl);
                if (gcmToken != null) {
                    GcmSender.send(gcmToken, String.format("Image(request id = %s) ready", inputRecordId));
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
