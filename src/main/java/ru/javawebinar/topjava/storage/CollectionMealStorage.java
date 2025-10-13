package ru.javawebinar.topjava.storage;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class CollectionMealStorage implements MealStorage {
    private static final Logger log = getLogger(CollectionMealStorage.class);

    private final AtomicInteger counter = new AtomicInteger(0);
    private final ConcurrentHashMap<Integer, Meal> meals = new ConcurrentHashMap<>();

    @Override
    public Meal update(Meal meal) {
        log.info("Update " + meal);
        return meals.replace(meal.getId(), meal);
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
    public ConcurrentHashMap<Integer, Meal> getAll() {
        return new ConcurrentHashMap<>(meals);
    }

    public void populate() {
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),
                "Завтрак", 500));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 14, 30),
                "Обед", 770));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 25),
                "Ужин", 500));
        create(new Meal(LocalDateTime.of(2022, Month.DECEMBER, 5, 9, 30),
                "Завтрак", 1050));
        create(new Meal(LocalDateTime.of(2022, Month.DECEMBER, 5, 14, 30),
                "Обед", 450));
        create(new Meal(LocalDateTime.of(2022, Month.DECEMBER, 5, 19, 10),
                "Ужин", 520));
        create(new Meal(LocalDateTime.of(2023, Month.AUGUST, 20, 2, 5),
                "Срыв", 800));
        create(new Meal(LocalDateTime.of(2023, Month.AUGUST, 20, 8, 25),
                "Завтрак", 375));
        create(new Meal(LocalDateTime.of(2023, Month.AUGUST, 20, 18, 15),
                "Полдник", 650));
        create(new Meal(LocalDateTime.of(2023, Month.AUGUST, 20, 23, 8),
                "Ужин", 750));
    }
}
