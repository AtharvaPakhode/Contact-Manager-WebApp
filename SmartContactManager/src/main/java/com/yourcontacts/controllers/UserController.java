package com.yourcontacts.controllers;


import com.yourcontacts.models.Contacts;
import com.yourcontacts.models.User;
import com.yourcontacts.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepo;

    @ModelAttribute
    public void addCommonData(Model model,Principal principal){
        String username = principal.getName();
        User user =userRepo.getUserByName(username);
        model.addAttribute("user",user);
    }

    @GetMapping("/index")
    public String dashboard(Model model, Principal principal){

        return "user/user_dashboard";
    }

    @GetMapping("/add-contact")
    public String addContactForm (Model model){
        model.addAttribute("title","Add Contact Form");
        model.addAttribute("contact", new Contacts());
        return "user/addContactForm";
    }
}
