package com.thuydieutran.admin.user;

import com.thuydieutran.admin.FileUploadUtil;
import com.thuydieutran.kidshopcommon.entities.Role;
import com.thuydieutran.kidshopcommon.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

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
        model.addAttribute("pageTitle","Create a new user" );

        return "users/user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes redirectAttributes, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        // Because after persisting the User object into the database, we return a redirect view so we need to use RedirectAttributes to add an attribute

        if (!multipartFile.isEmpty()) {
            // It is recommended to use the StringUtils class from Spring to clean the path
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            user.setPhotos(fileName);
            User savedUser = userService.save(user);
            String uploadDir = "users-photos/" + savedUser.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }

        userService.save(user);
        redirectAttributes.addFlashAttribute("message", "The user has been saved successfully. ");
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            // if the user id exists, we can access into the form that show his current infos to be able to modify them
            User user = userService.get(id);
            List<Role> listRoles = userService.listRoles();
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Edit user id: " + id);
            model.addAttribute("roles", listRoles);
            return "users/user_form";
        } catch (UserNotFoundException ex) {
            // if the user id doesn't exist, it is redirected to the user list and show a flash message
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/users";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            userService.delete(id);
            redirectAttributes.addFlashAttribute("message", "The user id " + id + " has been deleted successfully");
        } catch (UserNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/enable/{status}")
    public String updateUserEnableStatus(@PathVariable("id") Integer id,
                                         @PathVariable("status") boolean enable, RedirectAttributes redirectAttributes) {
        userService.updateUserEnableStatus(id, enable);
        String status = enable ? "enabled" : "disabled";
        String message = "This user id " + id + " has been " + status;
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/users";
    }
}
