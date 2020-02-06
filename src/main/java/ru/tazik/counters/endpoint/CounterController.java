package ru.tazik.counters.endpoint;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.tazik.counters.dto.CounterUpdateRequest;
import ru.tazik.counters.dto.GetCountersNamesResponse;
import ru.tazik.counters.enums.CounterCommand;
import ru.tazik.counters.exception.CounterException;
import ru.tazik.counters.model.Counter;
import ru.tazik.counters.service.CounterService;

import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("counters")
@RequiredArgsConstructor
public class CounterController {

    private final CounterService counterService;

    @PostMapping
    public ResponseEntity<Void> create() {
        String name = counterService.create();
        URI uri = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/counters/{id}").build()
                .expand(name).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PatchMapping(value = "{name}")
    @ResponseStatus(code = HttpStatus.OK)
    public void increment(@PathVariable("name") String name, @RequestBody CounterUpdateRequest request) throws CounterException {
        CounterCommand command = request.getCommand();
        if (CounterCommand.INCREMENT == command) {
            counterService.increment(name);
            return;
        }
        throw new CounterException("Передан неверный запрос");
    }

    @GetMapping(value = "{name}")
    @ResponseStatus(code = HttpStatus.OK)
    public Counter value(@PathVariable("name") String name) {
        long value = counterService.getValue(name);
        return new Counter(name, value);
    }

    @DeleteMapping(value = "{name}")
    @ResponseStatus(code = HttpStatus.OK)
    public Counter delete(@PathVariable("name") String name) {
        long value = counterService.delete(name);
        return new Counter(name, value);
    }

    @GetMapping(value = "total")
    @ResponseStatus(code = HttpStatus.OK)
    public Counter total() {
        return new Counter(counterService.getTotal());
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public GetCountersNamesResponse names() {
        Set<String> names = counterService.getNames();
        return new GetCountersNamesResponse(names);
    }
}
