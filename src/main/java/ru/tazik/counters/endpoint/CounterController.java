package ru.tazik.counters.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tazik.counters.service.CounterService;

import javax.validation.constraints.NotNull;
import java.util.Set;

@RestController
@RequestMapping("counters")
public class CounterController {

    private CounterService counterService;

    @Autowired
    public CounterController(CounterService counterService) {
        this.counterService = counterService;
    }

    @RequestMapping("create")
    public String create() {
        return counterService.create();
    }

    @RequestMapping(value = "{name}/increment")
    public void increment(@PathVariable("name") @NotNull String name) {
        counterService.increment(name);
    }

    @RequestMapping(value = "{name}/value")
    public long value(@PathVariable("name") @NotNull String name) {
        return counterService.getValue(name);
    }

    @RequestMapping(value = "{name}/delete")
    public void delete(@PathVariable("name") @NotNull String name) {
        counterService.delete(name);
    }

    @RequestMapping(value = "total")
    public long total() {
        return counterService.getTotal();
    }

    @RequestMapping(value = "names")
    public Set<String> names() {
        return counterService.getNames();
    }

}
