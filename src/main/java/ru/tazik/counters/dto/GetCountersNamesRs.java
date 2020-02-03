package ru.tazik.counters.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class GetCountersNamesRs {
    private Set<String> names;
}
