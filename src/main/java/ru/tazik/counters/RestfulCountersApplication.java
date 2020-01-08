package ru.tazik.counters;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tazik.counters.service.ConcurrentHashMapCounterService;
import ru.tazik.counters.service.CounterService;

@SpringBootApplication
@Configuration
public class RestfulCountersApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestfulCountersApplication.class, args);
    }

    @Bean
    public CounterService counterService() {
        return new ConcurrentHashMapCounterService();
    }

}
