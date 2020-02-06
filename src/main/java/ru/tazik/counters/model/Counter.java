package ru.tazik.counters.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonClassDescription("Счетчик")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Counter {
    @JsonPropertyDescription("Идентификатор")
    private String id;
    @JsonPropertyDescription("Значение")
    private Long value;

    public Counter(Long value) {
        this.value = value;
    }
}
