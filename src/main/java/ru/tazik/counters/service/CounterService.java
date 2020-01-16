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
        String name = UUID.randomUUID().toString();
        LongAdder counter = new LongAdder();
        if (!counters.containsKey(name)) {
            counters.put(name, counter);
            return name;
        } else {
            return create();
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

    private LongAdder getCounter(String name) {
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
    public void delete(String name) {
        counters.remove(name);
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
