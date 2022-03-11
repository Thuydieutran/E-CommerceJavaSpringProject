package com.thuydieutran.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MainController {
    @GetMapping("")
    public String viewHomePage(Principal principal){
        if ( principal == null) {
            return "redirect:/login";
        }

        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
