package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {

    private static int id = 1;

    public static void setAuthUserId(int id) {
        SecurityUtil.id = id;
        System.out.println(id);
    }

    public static int authUserId() {
        return id;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}