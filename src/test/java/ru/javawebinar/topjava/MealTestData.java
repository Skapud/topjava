package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_MEAL_0_ID = START_SEQ + 3;
    public static final int USER_MEAL_1_ID = START_SEQ + 4;
    public static final int USER_MEAL_2_ID = START_SEQ + 5;
    public static final int USER_MEAL_3_ID = START_SEQ + 6;
    public static final int ADMIN_MEAL_4_ID = START_SEQ + 7;
    public static final int ADMIN_MEAL_5_ID = START_SEQ + 8;
    public static final int ADMIN_MEAL_6_ID = START_SEQ + 9;
    public static final int ADMIN_MEAL_7_ID = START_SEQ + 10;
    public static final int NOT_FOUND = -1;

    public static final Meal userMeal0 = new Meal(USER_MEAL_0_ID, LocalDateTime.of(2020, 12, 5, 8, 0), "Контрольный прием user", 333);
    public static final Meal userMeal1 = new Meal(USER_MEAL_1_ID, LocalDateTime.of(2020, 12, 5, 10, 3), "Завтрак user", 750);
    public static final Meal userMeal2 = new Meal(USER_MEAL_2_ID, LocalDateTime.of(2020, 12, 5, 14, 30), "Обед user", 900);
    public static final Meal userMeal3 = new Meal(USER_MEAL_3_ID, LocalDateTime.of(2020, 12, 5, 20, 15), "Ужин user", 500);
    public static final Meal adminMeal4 = new Meal(ADMIN_MEAL_4_ID, LocalDateTime.of(2023, 3, 15, 8, 20), "Завтрак admin", 375);
    public static final Meal adminMeal5 = new Meal(ADMIN_MEAL_5_ID, LocalDateTime.of(2023, 3, 15, 12, 15), "Обед admin", 450);
    public static final Meal adminMeal6 = new Meal(ADMIN_MEAL_6_ID, LocalDateTime.of(2023, 3, 15, 16, 50), "Полдник admin", 250);
    public static final Meal adminMeal7 = new Meal(ADMIN_MEAL_7_ID, LocalDateTime.of(2023, 3, 16, 0, 35), "Ужин admin", 600);

    public static final List<Meal> USER_MEALS_LIST = Arrays.asList(userMeal3, userMeal2, userMeal1, userMeal0);
    public static final List<Meal> ADMIN_MEALS_LIST = Arrays.asList(adminMeal7, adminMeal6, adminMeal5, adminMeal4);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2020, 2, 2, 2, 2), "New meal", 222);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal();
        updated.setId(USER_MEAL_0_ID);
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
