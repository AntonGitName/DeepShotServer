package ru.spbau.mit.antonpp.deepshot.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.spbau.mit.antonpp.deepshot.server.controller.response.GetResultListResponse;
import ru.spbau.mit.antonpp.deepshot.server.controller.response.GetResultResponse;
import ru.spbau.mit.antonpp.deepshot.server.database.Util;
import ru.spbau.mit.antonpp.deepshot.server.database.model.MLOutputRecord;
import ru.spbau.mit.antonpp.deepshot.server.database.service.OutputRecordRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author antonpp
 * @since 13/11/15
 */
@Controller
@RequestMapping("/results")
public class OutputRecordController {

    @Autowired
    private OutputRecordRepository repository;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<Long> getAllResultsForUser(@RequestParam String username) {
        System.out.println("OutputRecordController.getAllResultsForUser");
        List<MLOutputRecord> res = repository.getAllRecordsByName(username);
        GetResultListResponse response = new GetResultListResponse();
        response.setIds(new ArrayList<Long>());
        for (MLOutputRecord record : res) {
            response.getIds().add(record.getId());
        }
        return response.getIds();
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public GetResultResponse getResultById(@RequestParam long id) {
        System.out.println("OutputRecordController.getResultById");
        GetResultResponse response = new GetResultResponse();
        MLOutputRecord record = repository.getTaskRecordById(id);
        response.setStatus(record.getStatus());
        response.setOwner(repository.getOwnerById(id));
        try {
            if (record.getStatus() == MLOutputRecord.Status.READY) {
                response.setEncodedImage(Util.getEncodedImageFromUrl(record.getImageUrl()));
            }
        } catch (Exception e) {
            response.setStatus(MLOutputRecord.Status.FAILED);
        }
        return response;
    }
}
