package ru.tazik.counters.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

public class ConcurrentHashMapCounterService implements CounterService {

    private ConcurrentHashMap<String, LongAdder> counters = new ConcurrentHashMap<>();

    @Override
    public String create() {
        String name = UUID.randomUUID().toString();
        LongAdder counter = new LongAdder();
        if (!counters.containsKey(name)) {
            counters.put(name, counter);
            return name;
        } else {
            return create();
        }
    }

    @Override
    public void increment(String name) {
        LongAdder counter = getCounter(name);
        counter.increment();
    }

    private LongAdder getCounter(String name) {
        LongAdder counter = counters.get(name);
        if (counter == null) {
            throw new IllegalArgumentException(String.format("Отсутствует счетчик с именем %s", name));
        }
        return counter;
    }

    @Override
    public long getValue(String name) {
        LongAdder counter = getCounter(name);
        return counter.longValue();
    }

    @Override
    public void delete(String name) {
        counters.remove(name);
    }

    @Override
    public long getTotal() {
        return counters
                .values()
                .stream()
                .mapToLong(LongAdder::longValue)
                .reduce(Long::sum)
                .orElse(0);
    }

    @Override
    public List<String> getNames() {
        return new ArrayList<>(counters.keySet());
    }
}
