package ru.tazik.counters.service;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

@Service
public class CounterService {

    private final ConcurrentHashMap<String, LongAdder> counters = new ConcurrentHashMap<>();

    /**
     * Создать новый счетчик
     *
     * @return имя созданного счетчика
     */
    public String create() {
        while (true) {
            String name = UUID.randomUUID().toString();
            if (!counters.containsKey(name)) {
                LongAdder counter = new LongAdder();
                counters.put(name, counter);
                return name;
            }
        }
    }

    /**
     * Увеличить значение счетчика на 1
     *
     * @param name имя счетчика
     * @throws IllegalArgumentException если отсутствует счетчик с указанным name
     */
    public void increment(String name) {
        LongAdder counter = getCounter(name);
        counter.increment();
    }

    public LongAdder getCounter(String name) {
        LongAdder counter = counters.get(name);
        if (counter == null) {
            throw new IllegalArgumentException(String.format("Отсутствует счетчик с именем %s", name));
        }
        return counter;
    }

    /**
     * Получить значение счетчика с переданным идентификатором
     *
     * @param name имя счетчика
     * @throws IllegalArgumentException если отсутствует счетчик с указанным name
     */
    public long getValue(String name) {
        LongAdder counter = getCounter(name);
        return counter.longValue();
    }

    /**
     * Удалить счетчик
     *
     * @param name имя счетчика
     */
    public long delete(String name) {
        LongAdder value = counters.remove(name);
        return value != null ? value.longValue() : 0;
    }

    /**
     * Получить суммарное значение всех счетчиков
     */
    public long getTotal() {
        return counters
                .values()
                .stream()
                .mapToLong(LongAdder::longValue)
                .reduce(Long::sum)
                .orElse(0);
    }

    /**
     * Получить список имен всех счетчиков
     */
    public Set<String> getNames() {
        return counters.keySet();
    }
}
