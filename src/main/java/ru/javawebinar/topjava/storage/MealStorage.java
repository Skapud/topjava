package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public interface MealStorage {
    Meal update(Meal meal);

    Meal create(Meal meal);

    Meal get(int id);

    void delete(int id);

    List<Meal> getAll();
}
