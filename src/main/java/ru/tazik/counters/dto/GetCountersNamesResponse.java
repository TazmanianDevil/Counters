package ru.tazik.counters.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class GetCountersNamesResponse {
    private Set<String> names;
}
