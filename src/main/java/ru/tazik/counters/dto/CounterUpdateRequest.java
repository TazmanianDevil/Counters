package ru.tazik.counters.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import ru.tazik.counters.enums.CounterCommand;

@Value
@JsonDeserialize(builder = CounterUpdateRequest.CounterUpdateRequestBuilder.class)
@Builder
public class CounterUpdateRequest {

    private CounterCommand command;

    @JsonPOJOBuilder(withPrefix = "")
    public static class CounterUpdateRequestBuilder {
    }
}
