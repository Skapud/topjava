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
        model.addAttribute("meals", getAll());
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
            int mealId = Integer.parseInt(id);
            meal.setId(mealId);
            update(meal, mealId);
        } else {
            create(meal);
        }
        return "redirect:/meals";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int mealId, Model model) {
        model.addAttribute("meal", get(mealId));
        return "mealForm";
    }

    @PostMapping("/delete/{id}")
    public String remove(@PathVariable("id") int mealId) {
        delete(mealId);
        return "redirect:/meals";
    }
}
