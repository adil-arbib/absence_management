package com.ensah.eservice.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/index")
    public String home() {
        return "index";
    }


    @GetMapping({"/", ""})
    public String redirectToIndex(){
        return "redirect:/index";
    }



}
