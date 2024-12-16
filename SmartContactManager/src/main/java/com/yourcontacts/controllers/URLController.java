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

/**
 * This controller handles user-related operations such as registration, login,
 * and the navigation of the main pages (home, about, etc.).
 * It interacts with the user model and repository to handle registration logic.
 */
@Controller
public class URLController {

    // Injecting BCryptPasswordEncoder for password encryption
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Injecting UserRepository to interact with user data in the database
    @Autowired
    private UserRepository userRepo;




    /**
     * Handler for the home page.
     * @param model Model to hold attributes for the view
     * @return the name of the view (home)
     */
    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("title", "SmartConnect");
        return "home";
    }




    /**
     * Handler for the about page.
     * @param model Model to hold attributes for the view
     * @return the name of the view (about)
     */
    @GetMapping("/about")
    public String about(Model model){
        model.addAttribute("title", "About Us");
        return "about";
    }




    /**
     * Handler for the signup page where users can register.
     * @param model Model to hold attributes for the view
     * @return the name of the view (signup)
     */
    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("title", "Register Page");
        model.addAttribute("user", new User());  // Initialize a new user object for the form
        return "signup";
    }





    /**
     * Handler for registering a new user.
     * This method processes the user registration form submission.
     * @param user The user object from the form
     * @param result The binding result to check for validation errors
     * @param agreement The agreement checkbox value indicating if the user agrees to the terms
     * @param model Model to hold attributes for the view
     * @param session HttpSession to set messages for the user
     * @return the name of the view (signup)
     */
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user,
                           BindingResult result,
                           @RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
                           Model model,
                           HttpSession session) {

        // Print the agreement checkbox value to console for debugging
        System.out.println("Agreement value: " + agreement);

        try {
            // Check if user has agreed to the terms and conditions
            if (!agreement) {
                System.out.println("Please agree to the terms and conditions");
                throw new Exception("Please agree to the terms and conditions");
            }

            // If there are validation errors, return the user to the signup form
            if (result.hasErrors()) {
                System.out.println("ERROR: " + result.toString());
                model.addAttribute("user", user);
                return "signup";
            }

            // Set default properties for the new user
            user.setUser_role("ROLE_USER");
            user.setStatus(true);
            user.setImageURL("default.png");

            // Encrypt the password before saving
            System.out.println("Original password: " + user.getUser_password());
            user.setUser_password(passwordEncoder.encode(user.getUser_password()));
            System.out.println("Encrypted password: " + user.getUser_password());

            // Save the user to the database
            User saved_user = this.userRepo.save(user);

            // Add a success message to the session
            model.addAttribute("user", user);
            session.setAttribute("message", new Message("Successfully Registered - Click on login button to proceed", "alert-success"));

        } catch (Exception e) {
            // Handle any errors, log them and display an error message
            e.printStackTrace();
            model.addAttribute("user", user);
            session.setAttribute("message", new Message("Something went wrong", "alert-danger"));
            return "signup";
        }

        // Redirect back to the signup page (could be a success page or login)
        return "signup";
    }



    /**
     * Handler for the login page.
     * @param model Model to hold attributes for the view
     * @return the name of the view (login)
     */
    @GetMapping("/signin")
    public String customLogin(Model model){
        model.addAttribute("title", "Login Page");
        return "login";
    }
}
