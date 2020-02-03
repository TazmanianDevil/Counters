package ru.tazik.counters.endpoint;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.tazik.counters.dto.CounterUpdateRq;
import ru.tazik.counters.dto.GetCountersNamesRs;
import ru.tazik.counters.enums.CounterCommand;
import ru.tazik.counters.model.Counter;
import ru.tazik.counters.service.CounterService;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("counters")
@AllArgsConstructor
public class CounterController {

    private final CounterService counterService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> create() {
        String name = counterService.create();
        URI uri = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/counters/{id}").build()
                .expand(name).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "{name}")
    public ResponseEntity<Void> increment(@PathVariable("name") String name, @RequestBody CounterUpdateRq request) {
        CounterCommand command = request.getCommand();
        if (CounterCommand.INCREMENT == command) {
            counterService.increment(name);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @RequestMapping(value = "{name}")
    public ResponseEntity<Counter> value(@PathVariable("name") String name) {
        Counter counter = new Counter(name, counterService.getValue(name));
        return ResponseEntity.ok().body(counter);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{name}")
    public ResponseEntity<Counter> delete(@PathVariable("name") String name) {
        long value = counterService.delete(name);
        return ResponseEntity.ok().body(new Counter(name, value));
    }

    @RequestMapping(value = "total")
    public ResponseEntity<Counter> total() {
        return ResponseEntity.ok().body(new Counter(counterService.getTotal()));
    }

    @RequestMapping
    public ResponseEntity<GetCountersNamesRs> names() {
        Set<String> names = counterService.getNames();
        return ResponseEntity.ok().body(new GetCountersNamesRs(names));
    }
}
