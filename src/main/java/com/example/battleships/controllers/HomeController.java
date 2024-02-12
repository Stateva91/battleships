package com.example.battleships.controllers;

import ch.qos.logback.core.model.Model;
import com.example.battleships.services.AuthService;
import com.example.battleships.services.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final AuthService authService;
    private final ShipService shipService;

    @Autowired
    public HomeController(ShipService shipService, AuthService authService) {
        this.shipService = shipService;
        this.authService = authService;
    }
    @GetMapping("/")
    public String loggedOutIndex() {
        if (this.authService.isLoggedIn()) {
            return "redirect:/home";
        }

        return "index";
    }

    @GetMapping("/home")
    public String loggedInIndex(Model model) {
        if (!this.authService.isLoggedIn()) {
            return "redirect:/";
        }
        return "home";
    }
}
