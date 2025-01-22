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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

/**
 * This controller handles the operations related to the user, including adding, updating, deleting,
 * and viewing contacts. It interacts with both the `User` and `Contacts` models,
 * and the corresponding repositories to manage contacts.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ContactRepository contactRepo;




    /**
     * This method adds the current user's information to the model for every request.
     * It ensures that the username and user details are available in the views.
     *
     * @param model The model to add attributes to the view
     * @param principal The principal object that holds the current user's information
     */
    @ModelAttribute
    public void addCommonData(Model model, Principal principal){
        String username = principal.getName();
        User user = userRepo.getUserByName(username);
        model.addAttribute("user", user);
    }




    /**
     * Displays the user dashboard.
     *
     * @param model The model object that holds the data for the view
     * @param principal The current authenticated user
     * @return the name of the view (user_dashboard)
     */
    @GetMapping("/index")
    public String dashboard(Model model, Principal principal){

        String name = principal.getName();
        User user = this.userRepo.getUserByName(name);


        model.addAttribute("title", "Dashboard");

        return "user/user_dashboard";
    }


    @GetMapping("/profile")
    public String userProfile(Model model, Principal principal){

        String name = principal.getName();
        User user = this.userRepo.getUserByName(name);




        model.addAttribute("title", "Profile");

        return "user/userProfile";
    }




    /**
     * Displays the form to add a new contact.
     *
     * @param model The model object to hold attributes for the view
     * @return the name of the view (addContactForm)
     */
    @GetMapping("/add-contact")
    public String addContactForm(Model model){
        model.addAttribute("title", "Add Contact");
        model.addAttribute("contact", new Contacts());
        return "user/addContactForm";
    }




    /**
     * Processes the form to save a new contact.
     *
     * @param contact The contact object submitted from the form
     * @param bindingResult Holds validation errors, if any
     * @param model The model object to hold attributes for the view
     * @param principal The current authenticated user
     * @param contactImage The contact's image to be uploaded
     * @param session The session object to store messages for the user
     * @return the name of the view (addContactForm)
     */
    @PostMapping("/process-add-contact")
    public String saveContact(@Valid @ModelAttribute("contact") Contacts contact,
                              BindingResult bindingResult,
                              Model model,
                              Principal principal,
                              @RequestParam("contactImage") MultipartFile contactImage,
                              HttpSession session) {

        try {
            // If there are validation errors, return the form with errors
            if (bindingResult.hasErrors()) {
                model.addAttribute("contact", contact);
                return "user/addContactForm";
            }

            String name = principal.getName();
            User user = this.userRepo.getUserByName(name);
            contact.setUser(user);

            Contacts savedContact = this.contactRepo.save(contact);
            this.userRepo.save(user);



            // Process and upload the contact image
            if (contactImage.isEmpty()) {
                contact.setContact_image("contactDefault.png");
            } else {
                // Upload image to folder and update the contact image name

                // Step 1: Get the original filename
                String originalFilename = contactImage.getOriginalFilename();

                // Step 2: Get the Contact ID
                String contactID = String.valueOf(savedContact.getContact_id());

                // Step 3: Extract the file extension (e.g., ".jpg")
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

                // Step 4: Create a new filename with the format "photo_username.jpg"
                String imgName = contactID +"_"+"profile"+ fileExtension;

                // Step 5: Set the contact image filename
                contact.setContact_image(imgName);
                String folderPath = "static/contact_images";
                File directory = new File(folderPath);

                if (!directory.exists()) {
                    directory.mkdirs();
                }

                Path targetLocation = Paths.get(directory.getAbsolutePath() + File.separator + imgName);
                Files.copy(contactImage.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            }

            this.contactRepo.save(contact);

            session.setAttribute("message", new Message("Contact Saved", "alert-success"));



        } catch (Exception e) {
            e.printStackTrace();
        }

        return "user/addContactForm";
    }




    /**
     * Displays the list of contacts with pagination.
     *
     * @param page The page number for pagination
     * @param model The model object to hold the contact list
     * @param principal The current authenticated user
     * @return the name of the view (viewContacts)
     */
    @GetMapping("/view-contact/{page}")
    public String viewContact(@PathVariable("page") Integer page, Model model, Principal principal) {

        model.addAttribute("title", "View Contacts");

        String name = principal.getName();
        User user = this.userRepo.getUserByName(name);

        int id = user.getUser_id();
        Pageable pageable = PageRequest.of(page, 8);
        Page<Contacts> contactList = this.contactRepo.findContactByUser(id, pageable);

        model.addAttribute("contacts", contactList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", contactList.getTotalPages());

        return "user/viewContacts";
    }




    /**
     * Displays the details of a specific contact.
     *
     * @param contact_id The ID of the contact to be displayed
     * @param model The model object to hold the contact details
     * @param principal The current authenticated user
     * @return the name of the view (showContactDetails)
     */
    @GetMapping("/show-contact-details/{contactID}")
    public String showContactDetails(@PathVariable("contactID") Integer contact_id,
                                     Model model,
                                     Principal principal) {

        model.addAttribute("title", "Show Contact Details");

        Optional<Contacts> contactOptional = contactRepo.findById(contact_id);
        Contacts contact = contactOptional.get();

        String name = principal.getName();
        User loginUser = this.userRepo.getUserByName(name);

        if (loginUser.getUser_id() == contact.getUser().getUser_id()) {
            model.addAttribute("contact", contact);
        }

        return "user/showContactDetails";
    }




    /**
     * Deletes a contact and its associated image.
     *
     * @param contact_id The ID of the contact to be deleted
     * @param model The model object
     * @param principal The current authenticated user
     * @param session The session object to store messages for the user
     * @return redirect to the contact list page
     */
    @GetMapping("/delete-contact/{contactID}")
    public String deleteContact(@PathVariable("contactID") Integer contact_id,
                                Model model,
                                Principal principal,
                                HttpSession session) {

        Optional<Contacts> contactOptional = this.contactRepo.findById(contact_id);
        Contacts contact = contactOptional.get();

        String name = principal.getName();
        User loginUser = this.userRepo.getUserByName(name);

        if (loginUser.getUser_id() == contact.getUser().getUser_id()) {
            contact.setUser(null);
            this.contactRepo.delete(contact);

            // Delete the contact's image from the folder
            String contactImage = contact.getContact_image();
            String fileNameToDelete = contactImage;

            String folderPath = "static/contact_images";
            File directory = new File(folderPath);
            Path targetLocation = Paths.get(directory.getAbsolutePath() + File.separator + fileNameToDelete);

            File fileToDelete = targetLocation.toFile();
            if (fileToDelete.exists() && fileToDelete.isFile()) {
                fileToDelete.delete();
            }

            session.setAttribute("message", new Message("Contact Deleted Successfully", "alert-success"));
        }

        return "redirect:/user/view-contact/0";
    }




    /**
     * Displays the form to update an existing contact.
     *
     * @param contact_id The ID of the contact to be updated
     * @param model The model object to hold the contact data
     * @param principal The current authenticated user
     * @return the name of the view (updateContact)
     */
    @PostMapping("/update-contact/{contactID}")
    public String updateContact(@PathVariable("contactID") Integer contact_id,
                                Model model,
                                Principal principal)  {



        String name = principal.getName();
        User loginUser = this.userRepo.getUserByName(name);

        Optional<Contacts> contactOptional = this.contactRepo.findById(contact_id);
        Contacts contactToBeUpdated = contactOptional.get();


        model.addAttribute("contact", contactToBeUpdated);

        return "user/updateContact";
    }




    /**
     * Processes the form to update the contact information.
     *
     * @param contact The contact object with updated data
     * @param bindingResult Holds validation errors, if any
     * @param model The model object to hold attributes for the view
     * @param principal The current authenticated user
     * @param contactImage The new contact image to be uploaded (if any)
     * @param contact_id The ID of the contact being updated
     * @param session The session object to store messages for the user
     * @return redirect to the contact details page
     */
    @PostMapping("/process-update-contact")
    public String updateContactForm(@Valid @ModelAttribute Contacts contact,
                                    BindingResult bindingResult,
                                    Model model,
                                    Principal principal,
                                    @RequestParam("contactImage") MultipartFile contactImage,
                                    @RequestParam("contact_id") Integer contact_id,
                                    HttpSession session) {

        try {
            // If validation fails, return the form with errors
            if (bindingResult.hasErrors()) {
                model.addAttribute("contact", contact);
                return "user/updateContact";
            }

            String name = principal.getName();
            User user = this.userRepo.getUserByName(name);
            contact.setUser(user);

            Optional<Contacts> existingContactOptional = this.contactRepo.findById(contact.getContact_id());
            Contacts existingContact = existingContactOptional.get();

            // Step 1: Delete the existing image file if it exists
            String oldImage = existingContact.getContact_image();
            if (oldImage != null && !oldImage.isEmpty()) {
                String folderPath = "static/contact_images";
                File fileToDelete = new File(folderPath, oldImage);

                if (fileToDelete.exists()) {
                    fileToDelete.delete();
                }
            }

            // Step 2: Upload the new image (if provided)
            if (contactImage.isEmpty()) {
                existingContact.setContact_image("contactDefault.png");
            }
            else if (!contactImage.isEmpty()) {
                String folderPath = "static/contact_images";
                File directory = new File(folderPath);

                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Step 1: Get the original filename
                String originalFilename = contactImage.getOriginalFilename();

                // Step 2: Get the existing contact ID
                String contactID = String.valueOf(existingContact.getContact_id());

                // Step 3: Extract the file extension (e.g., ".jpg")
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

                // Step 4: Create a new filename with the format "photo_username.jpg"
                String imgName =  contactID +"_"+ "profile" + fileExtension;

                // Step 5: Set the contact image filename
                contact.setContact_image(imgName);

                Path targetLocation = Paths.get(directory.getAbsolutePath() + File.separator + imgName);
                Files.copy(contactImage.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

                existingContact.setContact_image(imgName);
            }

            // Step 3: Update the contact details
            existingContact.setContact_name(contact.getContact_name());
            existingContact.setContact_email(contact.getContact_email());
            existingContact.setPhone_number(contact.getPhone_number());
            existingContact.setContact_nickname(contact.getContact_nickname());
            existingContact.setContact_work(contact.getContact_work());
            existingContact.setAbout_contact(contact.getAbout_contact());
            this.contactRepo.save(existingContact);

            session.setAttribute("message", new Message("Contact Updated", "alert-success"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/user/show-contact-details/" + contact.getContact_id();
    }



    @PostMapping("/process-profile-image-form")
    public String profileImageUpdate(Model model,
                                     Principal principal,
                                    @RequestParam("profileImage") MultipartFile profileImage) throws IOException {

        String name = principal.getName();
        User user = this.userRepo.getUserByName(name);

        // Upload image to folder and update the user profile image name

        // Step 1: Get the original filename
        String originalFilename = profileImage.getOriginalFilename();

        // Step 2: Get the User ID
        String userID = String.valueOf(user.getUser_id());

        // Step 3: Extract the file extension (e.g., ".jpg")
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

        // Step 4: Create a new filename with the format "photo_username.jpg"
        String imgName = userID +"_"+"profile"+ fileExtension;

        // Step 5: Set the contact image filename
        user.setImage_URL(imgName);
        String folderPath = "static/user_profile_images";
        File directory = new File(folderPath);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        Path targetLocation = Paths.get(directory.getAbsolutePath() + File.separator + imgName);
        Files.copy(profileImage.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        this.userRepo.save(user);

        model.addAttribute("user",user);


        return "user/userProfile";

    }
}
