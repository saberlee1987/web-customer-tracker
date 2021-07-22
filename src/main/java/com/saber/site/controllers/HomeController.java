package com.saber.site.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    @GetMapping(value = {"/", "/home"})
    public String homePage(Model model) {
        model.addAttribute("name", "Saber");
        model.addAttribute("family", "Azizi");
        List<String> colors= new ArrayList<>();
        colors.add("red");
        colors.add("green");
        colors.add("blue");
        colors.add("white");
        colors.add("black");
        colors.add("orange");
        colors.add("yellow");
        colors.add("pink");
        model.addAttribute("colors",colors);
        log.info("home method called ...............");
        return "home";
    }
}
