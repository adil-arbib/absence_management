package com.ensah.eservice.controllers.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth/login")
public class LoginController {

    @GetMapping
    public String showFormLogin() {
        return "auth/login";
    }


    @GetMapping("/auth/login-error")
    public String errorLogin(Model model){
        model.addAttribute("error", true);
        return "auth/login";
    }

}
