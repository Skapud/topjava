package ru.javawebinar.topjava.storage;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class CollectionMealStorage implements MealStorage {
    private static final Logger log = getLogger(CollectionMealStorage.class);

    private final AtomicInteger counter = new AtomicInteger(0);
    private final Map<Integer, Meal> meals = new ConcurrentHashMap<>();

    @Override
    public Meal update(Meal meal) {
        log.info("Update " + meal);
        int replacedId = meal.getId();
        Meal replacedMeal = meals.replace(replacedId, meal);
        if (!replacedMeal.equals(meals.get(replacedId))) {
            return meal;
        }
        return null;
    }

    @Override
    public Meal create(Meal meal) {
        log.info("Save " + meal);
        int id = counter.incrementAndGet();
        meal.setId(id);
        meals.put(id, meal);
        return meal;
    }

    @Override
    public Meal get(int id) {
        log.info("Get " + id);
        return meals.get(id);
    }

    @Override
    public void delete(int id) {
        log.info("Delete " + id);
        meals.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }
}
