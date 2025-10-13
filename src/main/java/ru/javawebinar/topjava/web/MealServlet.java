package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.CollectionMealStorage;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    public static final int CALORIES_PER_DAY = 2000;

    private MealStorage mealStorage;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealStorage = new CollectionMealStorage();
        populate();
    }

    private void populate() {
        List<Meal> mealsToPopulate = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),
                        "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 14, 30),
                        "Обед", 770),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 25),
                        "Ужин", 500),
                new Meal(LocalDateTime.of(2022, Month.DECEMBER, 5, 9, 30),
                        "Завтрак", 1050),
                new Meal(LocalDateTime.of(2022, Month.DECEMBER, 5, 14, 30),
                        "Обед", 450),
                new Meal(LocalDateTime.of(2022, Month.DECEMBER, 5, 19, 10),
                        "Ужин", 520),
                new Meal(LocalDateTime.of(2023, Month.AUGUST, 20, 2, 5),
                        "Срыв", 800),
                new Meal(LocalDateTime.of(2023, Month.AUGUST, 20, 8, 25),
                        "Завтрак", 375),
                new Meal(LocalDateTime.of(2023, Month.AUGUST, 20, 18, 15),
                        "Полдник", 650),
                new Meal(LocalDateTime.of(2023, Month.AUGUST, 20, 23, 8),
                        "Ужин", 750));

        for (Meal meal: mealsToPopulate) {
            mealStorage.create(meal);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String action = request.getParameter("action");
        if (action == null) {
            log.debug("get all meals");
            List<MealTo> meals = MealsUtil.filteredByStreams(
                    mealStorage.getAll(),
                    LocalTime.MIN,
                    LocalTime.MAX,
                    CALORIES_PER_DAY);
            request.setAttribute("meals", meals);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }

        Meal meal;
        switch (action) {
            case ("create"):
                log.debug("create meal");
                meal = new Meal();
                break;
            case ("edit"):
                log.debug("edit meal");
                meal = mealStorage.get(Integer.parseInt(id));
                break;
            case ("delete"):
                log.debug("delete meal");
                mealStorage.delete(Integer.parseInt(id));
            default:
                response.sendRedirect("meals");
                return;
        }
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("/edit-meal.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");
        String dateTime = request.getParameter("dateTime");

        Meal meal = new Meal(LocalDateTime.parse(dateTime), description, Integer.parseInt(calories));
        final boolean isCreate = (id == null || id.isEmpty());
        if (isCreate) {
            mealStorage.create(meal);
        } else {
            meal.setId(Integer.parseInt(id));
            mealStorage.update(meal);
        }
        response.sendRedirect("meals");
    }
}