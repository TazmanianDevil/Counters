package ru.tazik.counters.service;

import java.util.List;

public interface CounterService {

    /**
     * Создать новый счетчик
     *
     * @return имя созданного счетчика
     */
    String create();

    /**
     * Увеличить значение счетчика на 1
     *
     * @param name имя счетчика
     * @throws IllegalArgumentException если отсутствует счетчик с указанным name
     */
    void increment(String name);

    /**
     * Получить значение счетчика с переданным идентификатором
     *
     * @param name имя счетчика
     * @throws IllegalArgumentException если отсутствует счетчик с указанным name
     */
    long getValue(String name);

    /**
     * Удалить счетчик
     *
     * @param name имя счетчика
     */
    void delete(String name);

    /**
     * Получить суммарное значение всех счетчиков
     */
    long getTotal();

    /**
     * Получить список имен всех счетчиков
     */
    List<String> getNames();
}
