package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MatcherFactory;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.adminMeal1;
import static ru.javawebinar.topjava.MealTestData.adminMeal2;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ActiveProfiles(profiles = DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Test
    public void getWithMeals() {
        MatcherFactory.Matcher<User> userMatcher = MatcherFactory.usingIgnoringFieldsComparator(
                "registered", "roles", "meals.user.registered");
        User user = service.getWithMeals(ADMIN_ID);

        User expectedUser = new User(UserTestData.admin);
        expectedUser.setMeals(List.of(adminMeal2, adminMeal1));
        adminMeal1.setUser(expectedUser);
        adminMeal2.setUser(expectedUser);

        userMatcher.assertMatch(user, expectedUser);
    }
}
