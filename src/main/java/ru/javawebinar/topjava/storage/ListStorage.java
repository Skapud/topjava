package ru.javawebinar.topjava.storage;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class ListStorage implements Storage {
    private static final Logger log = getLogger(ListStorage.class);

    public static final int CALORIES_PER_DAY = 2000;
    private static final AtomicInteger counter = new AtomicInteger(0);
    private static final List<Meal> meals = new ArrayList<>(Arrays.asList(
            new Meal(counter.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),
                    "Завтрак", 500),
            new Meal(counter.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 14, 30),
                    "Обед", 770),
            new Meal(counter.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 25),
                    "Ужин", 500),
            new Meal(counter.incrementAndGet(), LocalDateTime.of(2022, Month.DECEMBER, 5, 9, 30),
                    "Завтрак", 1050),
            new Meal(counter.incrementAndGet(), LocalDateTime.of(2022, Month.DECEMBER, 5, 14, 30),
                    "Обед", 450),
            new Meal(counter.incrementAndGet(), LocalDateTime.of(2022, Month.DECEMBER, 5, 19, 10),
                    "Ужин", 520),
            new Meal(counter.incrementAndGet(), LocalDateTime.of(2023, Month.AUGUST, 20, 2, 5),
                    "Срыв", 800),
            new Meal(counter.incrementAndGet(), LocalDateTime.of(2023, Month.AUGUST, 20, 8, 25),
                    "Завтрак", 375),
            new Meal(counter.incrementAndGet(), LocalDateTime.of(2023, Month.AUGUST, 20, 18, 15),
                    "Полдник", 650),
            new Meal(counter.incrementAndGet(), LocalDateTime.of(2023, Month.AUGUST, 20, 23, 8),
                    "Ужин", 750)
    ));


    @Override
    public void update(Meal meal, Integer index) {
        log.info("Update " + meal);
        for (int i = 0; i < meals.size(); i++) {
            if (meals.get(i).getId().equals(index)) {
                meals.set(i, meal);
                return;
            }
        }
    }

    @Override
    public void save(Meal meal) {
        log.info("Save " + meal);
        meal.setId(counter.incrementAndGet());
        meals.add(meal);
    }

    @Override
    public Meal get(Integer id) {
        log.info("Get " + id);
        for (Meal meal : meals) {
            if (meal.getId().equals(id)) {
                return meal;
            }
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        log.info("Delete " + id);
        meals.removeIf(meal -> meal.getId().equals(id));
    }

    @Override
    public List<Meal> getAll() {
        return meals;
    }
}
