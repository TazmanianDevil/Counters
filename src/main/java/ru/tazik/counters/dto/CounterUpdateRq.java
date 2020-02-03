package ru.tazik.counters.dto;

import lombok.Data;
import ru.tazik.counters.enums.CounterCommand;

@Data
public class CounterUpdateRq {
    private CounterCommand command;
}
