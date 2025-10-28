package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int NOT_FOUND = -1;

    public static final Meal MEAL1 = new Meal(1, LocalDateTime.of(2020, 12, 5, 10, 3), "Завтрак user", 750);
    public static final Meal MEAL2 = new Meal(2, LocalDateTime.of(2020, 12, 5, 14, 30), "Обед user", 900);
    public static final Meal MEAL3 = new Meal(3, LocalDateTime.of(2020, 12, 5, 20, 15), "Ужин user", 500);
    public static final Meal MEAL4 = new Meal(4, LocalDateTime.of(2023, 3, 15, 8, 20), "Завтрак admin", 375);
    public static final Meal MEAL5 = new Meal(5, LocalDateTime.of(2023, 3, 15, 12, 15), "Обед admin", 450);
    public static final Meal MEAL6 = new Meal(6, LocalDateTime.of(2023, 3, 15, 16, 50), "Полдник admin", 250);
    public static final Meal MEAL7 = new Meal(7, LocalDateTime.of(2023, 3, 15, 20, 35), "Ужин admin", 600);

    public static final List<Meal> USER_MEALS_LIST = Arrays.asList(MEAL3, MEAL2, MEAL1);
    public static final List<Meal> ADMIN_MEALS_LIST = Arrays.asList(MEAL7, MEAL6, MEAL5, MEAL4);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2020, 2, 2, 2, 2), "New meal", 222);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL1);
        updated.setId(1);
        updated.setDateTime(LocalDateTime.of(2005, 5, 5, 5, 5));
        updated.setDescription("Updated");
        updated.setCalories(555);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
