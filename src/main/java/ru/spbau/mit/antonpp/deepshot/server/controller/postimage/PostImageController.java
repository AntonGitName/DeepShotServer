package ru.spbau.mit.antonpp.deepshot.server.controller.postimage;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.spbau.mit.antonpp.deepshot.server.processing.ImageTask;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author antonpp
 * @since 12/10/15
 */
@RestController
public class PostImageController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public PostImageResponse postImage(@RequestBody PostImageRequest request) throws IOException {

        final Long taskId = counter.incrementAndGet();

        ImageTask.start(taskId, request.getEncodedImage(), request.getFilterId());

        return new PostImageResponse(taskId);
    }
}