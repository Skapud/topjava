package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Storage {
    void update(Meal meal, Integer index);

    void save(Meal meal);

    Meal get(Integer id);

    void delete(Integer id);

    List<Meal> getAll();
}
