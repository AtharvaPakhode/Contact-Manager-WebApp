package com.yourcontacts.controllers;


import com.yourcontacts.helper.Message;
import com.yourcontacts.models.Contacts;
import com.yourcontacts.models.User;
import com.yourcontacts.repo.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    @PostMapping("/process-add-contact")
    public String saveContact(@Valid @ModelAttribute("contact") Contacts contact,
                              BindingResult bindingResult,
                              Model model,
                              Principal principal,
                              @RequestParam("contactImage") MultipartFile contactImage,
                              HttpSession session)  {

        try{

            if (bindingResult.hasErrors()) {
                // If validation fails, return the form with error messages
                model.addAttribute("contact",contact);
                return "user/addContactForm"; // Return to the form page (Thymeleaf template)
            }


            String name = principal.getName();
            User user = this.userRepo.getUserByName(name);

            //process and upload file
            if(contactImage.isEmpty()){
                System.out.println("empty image");
            }
            else{

                //upload file to folder and update the name of file  to contact
                contact.setContact_image(contactImage.getOriginalFilename());
                // Define the file path where the image will be saved
                String folderPath = "static/contact_images"; // or use an absolute path if needed
                File directory = new File(folderPath);

                // Ensure the directory exists
                if (!directory.exists()) {
                    directory.mkdirs();  // Creates directories if they don't exist
                }
                Path target_location = Paths.get(directory.getAbsolutePath() + File.separator + contactImage.getOriginalFilename());
                Files.copy(contactImage.getInputStream(),target_location, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("image uploaded");
            }

            contact.setUser(user);
            user.getContact_list().add(contact);
            this.userRepo.save(user);

            session.setAttribute("message", new Message("Contact Saved", "alert-success"));
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("error : " + e.getMessage() );
        }

        return "user/addContactForm";
    }
}
