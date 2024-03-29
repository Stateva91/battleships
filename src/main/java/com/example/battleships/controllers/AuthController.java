package com.example.battleships.controllers;

import com.example.battleships.models.dto.LoginDTO;
import com.example.battleships.models.dto.UserRegistrationDTO;
import com.example.battleships.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ModelAttribute("registrationDTO")
    public UserRegistrationDTO initRegistrationDTO(){
        return new UserRegistrationDTO();
    }


    @GetMapping("/register")
    public String register(){

        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegistrationDTO registrationDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){
    if(bindingResult.hasErrors() || !this.authService.register(registrationDTO)){
        redirectAttributes.addFlashAttribute("registrationDTO", registrationDTO);
        redirectAttributes.addFlashAttribute(
                "org.springframework.validation.BindingResult.registrationDTO",bindingResult);
        return "redirect:/register";
    }

   return  "redirect:/login";
    }
    @ModelAttribute("loginDTO")
    public LoginDTO initLoginDTO(){
        return new LoginDTO();
    }

    @GetMapping("/login")
    public String login(){
        return  "login";
    }

   @PostMapping("login")
    public String login(@Valid LoginDTO loginDTO,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes){
       if (this.authService.isLoggedIn()) {
           return "redirect:/home";
       }

       if (bindingResult.hasErrors()) {
           redirectAttributes.addFlashAttribute("loginDTO", loginDTO);
           redirectAttributes.addFlashAttribute(
                   "org.springframework.validation.BindingResult.loginDTO", bindingResult);

           return "redirect:/login";
       }

       if (!this.authService.login(loginDTO)) {
           redirectAttributes.addFlashAttribute("loginDTO", loginDTO);
           redirectAttributes.addFlashAttribute("badCredentials", true);

           return "redirect:/login";
       }

       return "redirect:/home";
   }
}
