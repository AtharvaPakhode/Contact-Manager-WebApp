package com.yourcontacts.controllers;


import com.yourcontacts.helper.Message;
import com.yourcontacts.models.Contacts;
import com.yourcontacts.models.User;
import com.yourcontacts.repo.ContactRepository;
import com.yourcontacts.repo.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ContactRepository contactRepo;

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
                contact.setContact_image("contactDefault.png");
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
    
    
    @GetMapping("/view-contact/{page}")
    public String viewContact(@PathVariable("page") Integer page , Model model,Principal principal){

        // Set the page title for the view
        model.addAttribute("title","view contacts");


        /*
         * Retrieve the username of the currently authenticated user.
         * The 'principal' object represents the current user.
         */
        String name = principal.getName();
        User user = this.userRepo.getUserByName(name);


        /*
         * Retrieve the user ID and prepare pagination settings for fetching contacts.
         * 'page' determines the current page, and we are setting a page size of 5 contacts per page.
         */
        int id = user.getUser_id();
        Pageable pageable= PageRequest.of(page , 8);
        Page<Contacts> contactList =this.contactRepo.findContactByUser(id , pageable);


        // Add the contact list to the model, making it accessible to the view
        model.addAttribute("contacts", contactList);

        // Add the current page to the model, making it accessible to the view
        model.addAttribute("currentPage",page);

        // Add the total pages to the model, making it accessible to the view
        model.addAttribute("totalPages", contactList.getTotalPages());

        return "user/viewContacts";
    }

    @GetMapping("/show-contact-details/{contactID}")
    public String showContactDetails(@PathVariable ("contactID") Integer contact_id,
                                     Model model ,
                                     Principal principal ){

        model.addAttribute("title","Show Contact Details");

        Optional<Contacts> contactOptional = contactRepo.findById(contact_id);
        Contacts contact = contactOptional.get();

        String name = principal.getName();
        User loginUser = this.userRepo.getUserByName(name);

        if (loginUser.getUser_id() == contact.getUser().getUser_id()) {
            model.addAttribute("contact", contact);
        }



        return "user/showContactDetails";
    }

    @GetMapping("/delete-contact/{contactID}")
    public String deleteContact(@PathVariable ("contactID") Integer contact_id ,
                                Model model,
                                Principal principal,
                                HttpSession session){

        Optional<Contacts> contactOptional = this.contactRepo.findById(contact_id);
        Contacts contact =contactOptional.get();



        String name = principal.getName();
        User loginUser = this.userRepo.getUserByName(name);

        if (loginUser.getUser_id() == contact.getUser().getUser_id()) {

            contact.setUser(null);
            this.contactRepo.delete(contact);

            // removing contact image from folder
            String contactImage = contact.getContact_image();
            String fileNameToDelete = contactImage;

            // Define the file path where the image is located
            String folderPath = "static/contact_images"; // or use an absolute path if needed
            File directory = new File(folderPath);

            // Construct the full file path
            Path target_location = Paths.get(directory.getAbsolutePath() + File.separator + fileNameToDelete);

            // Check if the file exists and delete it
            File fileToDelete = target_location.toFile();
            if (fileToDelete.exists() && fileToDelete.isFile()) {
                boolean deleted = fileToDelete.delete();
            } else {
                System.out.println("Image not found.");
            }


            session.setAttribute("message" , new Message("Contact Deleted Successfully", "alert-success"));
        }

        return "redirect:/user/view-contact/0";


    }



}
