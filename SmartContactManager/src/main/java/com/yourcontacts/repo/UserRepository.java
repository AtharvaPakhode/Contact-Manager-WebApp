package com.yourcontacts.repo;

import com.yourcontacts.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Integer> {


    /*
     * This method accepts an email as a parameter from the user.
     * It returns a User object
     * whose email matches the provided email parameter.
     */
    @Query("SELECT u FROM User u WHERE u.user_email = :email")
    public User getUserByName(@Param("email") String email);


}
