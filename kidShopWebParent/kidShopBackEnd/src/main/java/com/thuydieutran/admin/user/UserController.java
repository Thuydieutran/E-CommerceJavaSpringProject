package com.thuydieutran.admin.user;

import com.thuydieutran.kidshopcommon.entities.Role;
import com.thuydieutran.kidshopcommon.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String listAll(Model model) {
        List<User> listUsers = userService.listAll();
        model.addAttribute("listUsers", listUsers);
        return "users/users";
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        List<Role> listRoles = userService.listRoles();
        // Create new "user" object then put it onto the "model" so Spring MVC and Thymeleaf will bind this user object into the form
        User user = new User();
        user.setEnable(true);
        model.addAttribute("user", user);
        model.addAttribute("roles", listRoles);

        return "users/user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes redirectAttributes) {
        // Because after persisting the User object into the database, we return a redirect view so we need to use RedirectAttributes to add an attribute
        System.out.println(user);
        userService.save(user);
        redirectAttributes.addFlashAttribute("message", "The user has been saved successfully. ");
        return "redirect:/users";
    }
}
