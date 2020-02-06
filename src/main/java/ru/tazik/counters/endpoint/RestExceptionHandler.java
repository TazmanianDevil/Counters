package ru.tazik.counters.endpoint;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tazik.counters.dto.Error;
import ru.tazik.counters.exception.CounterException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {CounterException.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleBadRequestException(Exception e) {
        return new Error(e.getLocalizedMessage());
    }

}
