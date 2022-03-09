package com.thuydieutran.admin.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {
    @Autowired
    private UserService userService;

    @PostMapping("/users/check_email")
    public String checkDuplicateEmail(@Param("id") Integer id, @Param("email")  String email) {
        // Because now the method isEmailUnique requires 2 parameters so must declare an additional parameter for user id in the method signature checkDuplicateEMail()
        return userService.isEmailUnique(id, email) ? "OK" : "Duplicated email";
    }
}
