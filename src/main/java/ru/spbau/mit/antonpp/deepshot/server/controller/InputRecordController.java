package ru.spbau.mit.antonpp.deepshot.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.spbau.mit.antonpp.deepshot.server.processing.ImageTask;

import java.io.IOException;

/**
 * @author antonpp
 * @since 13/11/15
 */
@Controller
@RequestMapping("/send")
public class InputRecordController {

    @Autowired
    private ImageTask imageTask;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public void postImage(@RequestParam String username,
                          @RequestParam String encodedImage,
                          @RequestParam long styleId,
                          @RequestParam(required = false) String gcmToken,
                          @RequestParam(required = false, defaultValue = "true") boolean useGpu) throws IOException {
        if (gcmToken != null) {
            System.out.println("InputRecordController.postImage");
        } else {
            System.out.println("InputRecordController.postImage (no gcm)");
        }
        imageTask.start(username, encodedImage, styleId, gcmToken, useGpu);
    }
}
