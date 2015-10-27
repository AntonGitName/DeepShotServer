package ru.spbau.mit.antonpp.deepshot.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.spbau.mit.antonpp.deepshot.server.database.model.Filter;
import ru.spbau.mit.antonpp.deepshot.server.database.service.FilterRepository;

/**
 * @author antonpp
 * @since 26/10/15
 */
@Controller
@RequestMapping("/filter")
public class FilterController {

    @Autowired
    private FilterRepository repository;


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Filter> getFilters() {
        return repository.getFilters();
    }

}
