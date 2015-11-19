package ru.spbau.mit.antonpp.deepshot.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.spbau.mit.antonpp.deepshot.server.controller.response.GetStyleResponse;
import ru.spbau.mit.antonpp.deepshot.server.database.Util;
import ru.spbau.mit.antonpp.deepshot.server.database.model.Style;
import ru.spbau.mit.antonpp.deepshot.server.database.service.StyleRepository;

import java.io.IOException;
import java.util.List;

/**
 * @author antonpp
 * @since 26/10/15
 */
@Controller
@RequestMapping("/styles")
public class StyleController {

    @Autowired
    private StyleRepository repository;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<Long> getStyles() {
        System.out.println("StyleController.getStyles");
        return repository.getStyleIds();
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public GetStyleResponse getStyleById(@RequestParam Long id) throws IOException {
        GetStyleResponse response = new GetStyleResponse();
        Style style = repository.getStyleById(id);
        String encodedImage = Util.getEncodedImageFromUrl(style.getUri());
        response.setEncodedImage(encodedImage);
        response.setName(style.getName());
        return response;
    }
}
