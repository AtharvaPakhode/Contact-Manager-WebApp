package com.yourcontacts.configuration;

import com.yourcontacts.models.User;
import com.yourcontacts.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        /*    -----get user from database-----
         *  This method accepts username (Email in our case)
         *  and returns the user object
         */
        User user = userRepo.getUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("USER_NOT_FOUND");
        }


        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        return customUserDetails;
    }
}
