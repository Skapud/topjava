package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;


@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController {

    @Autowired
    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("meals", MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay()));
        return "/meals";
    }

    @GetMapping("/filter")
    public String filter(Model model, HttpServletRequest request) {
        model.addAttribute("meals", getBetween(
                parseLocalDate(request.getParameter("startDate")),
                parseLocalTime(request.getParameter("startTime")),
                parseLocalDate(request.getParameter("endDate")),
                parseLocalTime(request.getParameter("endTime"))));
        return "meals";
    }

    @GetMapping("/add")
    public String newMeal() {
        return "mealForm";
    }

    @PostMapping("/save")
    public String saveOrUpdate(HttpServletRequest request) {
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        String id = request.getParameter("id");
        if (StringUtils.hasLength(id)) {
            meal.setId(Integer.parseInt(id));
            service.update(meal, SecurityUtil.authUserId());
        } else {
            service.create(meal, SecurityUtil.authUserId());
        }
        return "redirect:/meals";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int mealId, Model model) {
        model.addAttribute("meal", service.get(mealId, SecurityUtil.authUserId()));
        return "mealForm";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int mealId) {
        service.delete(mealId, SecurityUtil.authUserId());
        return "redirect:/meals";
    }
}
