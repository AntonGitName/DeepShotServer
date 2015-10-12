package ru.spbau.mit.antonpp.deepshot.server;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author antonpp
 * @since 12/10/15
 */
@RestController
public class TaskController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/task", method = RequestMethod.GET)
    public Task task(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Task(counter.incrementAndGet(),
                String.format(template, name));
    }
}