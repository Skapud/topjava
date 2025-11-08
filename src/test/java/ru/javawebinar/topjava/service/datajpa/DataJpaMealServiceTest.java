package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.MatcherFactory;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.Profiles.DATAJPA;


@ActiveProfiles(profiles = DATAJPA)
public class DataJpaMealServiceTest extends AbstractMealServiceTest {

    @Transactional
    @Test
    public void getWithUser() {
        MatcherFactory.Matcher<Meal> mealMatcher = MatcherFactory.usingIgnoringFieldsComparator("user.registered");

        Meal actualMeal = service.getWithUser(ADMIN_MEAL_ID, UserTestData.ADMIN_ID);

        User expectedUser = new User(UserTestData.admin);

        Meal expectedMeal1 = new Meal(adminMeal1.getId(), adminMeal1.getDateTime(),
                adminMeal1.getDescription(), adminMeal1.getCalories());
        expectedMeal1.setUser(expectedUser);
        Meal expectedMeal2 = new Meal(adminMeal2.getId(), adminMeal2.getDateTime(),
                adminMeal2.getDescription(), adminMeal2.getCalories());
        expectedMeal2.setUser(expectedUser);

        expectedUser.setMeals(List.of(expectedMeal2, expectedMeal1));

        Meal expectedMeal = expectedMeal1;
        expectedMeal.setUser(expectedUser);

        mealMatcher.assertMatch(actualMeal, expectedMeal);
    }
}
