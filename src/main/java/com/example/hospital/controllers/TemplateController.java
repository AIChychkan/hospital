package com.example.hospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplateController {

    @GetMapping("login")
    public String getLogin(){
        return "login"; //has to be the same as target -> templates -> login.html, but without .html
    }

    @GetMapping("doctors")
    public String getDoctors(){
        return "doctors";
    }

}
