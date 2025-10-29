package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() {
        Meal created = service.create(getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        MealTestData.assertMatch(created, newMeal);
        MealTestData.assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(userMeal0.getDateTime(), "Duplicate", 777), USER_ID));
    }

    @Test
    public void delete() {
        int id = userMeal0.getId();
        service.delete(id, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(id, USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void deletedOthers() {
        assertThrows(NotFoundException.class, () -> service.delete(USER_MEAL_0_ID, ADMIN_ID));
    }

    @Test
    public void get() {
        Meal meal = service.get(userMeal0.getId(), USER_ID);
        MealTestData.assertMatch(meal, userMeal0);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getOthers() {
        assertThrows(NotFoundException.class, () -> service.get(ADMIN_MEAL_4_ID, USER_ID));
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        MealTestData.assertMatch(service.get(updated.getId(), USER_ID), getUpdated());
    }

    @Test
    public void updateOthers() {
        Meal updated = getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(updated, ADMIN_ID));
    }

    @Test
    public void getAll() {
        List<Meal> allUser = service.getAll(USER_ID);
        MealTestData.assertMatch(allUser, USER_MEALS_LIST);
        List<Meal> allAdmin = service.getAll(ADMIN_ID);
        MealTestData.assertMatch(allAdmin, ADMIN_MEALS_LIST);
    }

    @Test
    public void getFiltered() {
        List<Meal> adminFiltered = service.getBetweenInclusive(adminMeal4.getDate(), adminMeal6.getDate(), ADMIN_ID);
        assertMatch(adminFiltered, Arrays.asList(adminMeal6, adminMeal5, adminMeal4));
    }
}
