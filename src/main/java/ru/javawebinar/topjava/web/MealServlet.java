package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
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

    public void init(ServletConfig config) {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("get all meals");
        List<MealTo> meals = MealsUtil.filteredByStreams(
                MealsData.getAll(),
                LocalTime.of(0,0),
                LocalTime.of(23,59),
                MealsData.CALORIES_PER_DAY);

        log.debug("request to meals");
        request.setAttribute("meals", meals);

        request.getRequestDispatcher("/meals.jsp").forward(request, response);
        //        response.sendRedirect("meals.jsp");
    }

    static class MealsData {
        public static final int CALORIES_PER_DAY = 2000;

        private static final List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 14, 30), "Обед", 770),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 25), "Ужин", 500),
                new Meal(LocalDateTime.of(2022, Month.DECEMBER, 5, 9, 30), "Завтрак", 1050),
                new Meal(LocalDateTime.of(2022, Month.DECEMBER, 5, 14, 30), "Обед", 450),
                new Meal(LocalDateTime.of(2022, Month.DECEMBER, 5, 19, 10), "Ужин", 520),
                new Meal(LocalDateTime.of(2023, Month.AUGUST, 20, 2, 5), "Срыв", 800),
                new Meal(LocalDateTime.of(2023, Month.AUGUST, 20, 8, 25), "Завтрак", 375),
                new Meal(LocalDateTime.of(2023, Month.AUGUST, 20, 18, 15), "Полдник", 650),
                new Meal(LocalDateTime.of(2023, Month.AUGUST, 20, 23, 8), "Ужин", 750)
        );

        public static List<Meal> getAll() {
            return meals;
        }
    }
}