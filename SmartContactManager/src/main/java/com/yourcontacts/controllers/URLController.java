package com.yourcontacts.controllers;

import com.yourcontacts.helper.Message;
import com.yourcontacts.models.User;
import com.yourcontacts.repo.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
public class URLController {



    @Autowired
    private UserRepository userRepo ;


    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("title","Contact Manager");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model){
        model.addAttribute("title","About");
        return "about";
    }

    @GetMapping("/signup")
    public String signup(Model model){

        model.addAttribute("title","Register");
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/register")
    public String register(@Valid  @ModelAttribute("user") User user,
                           BindingResult result,
                           @RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
                           Model model,
                           HttpSession session) {

        System.out.println("Agreement value: " + agreement); // Add this line to check the value

        try {
            if (!agreement) {
                System.out.println("Please agree to the terms and conditions");
                throw new Exception("Please agree to the terms and conditions");
            }

            if(result.hasErrors()){
                System.out.println("ERROR : "+result.toString());
                model.addAttribute("user",user);
                return "signup";
            }

            


            // Set other user properties
            user.setUser_role("USER");
            user.setStatus(true);
            user.setImageURL("default.png");

            // Save the user
            User saved_user = this.userRepo.save(user);
            model.addAttribute("user", user);
            session.setAttribute("message", new Message("Successfully Registered - Click on login button to proceed", "alert-success"));

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("user", user);
            session.setAttribute("message", new Message("Something went wrong" , "alert-danger"));
            return "signup";
        }

        return "signup";
    }

}
