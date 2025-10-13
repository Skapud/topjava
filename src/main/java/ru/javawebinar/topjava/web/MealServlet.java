package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.CollectionMealStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    public static final int CALORIES_PER_DAY = 2000;

    private CollectionMealStorage mealStorage;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealStorage = new CollectionMealStorage();
        mealStorage.populate();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String action = request.getParameter("action");
        if (action == null) {
            log.debug("get all meals");
            List<MealTo> meals = MealsUtil.filteredByStreams(
                    mealStorage.getAll().values(),
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