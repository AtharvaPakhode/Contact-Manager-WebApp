package com.yourcontacts.repo;

import com.yourcontacts.models.Contacts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contacts,Integer> {

    /*
        custom method to find contact by user id which belongs to specific user
        'pegeable' object contains info about :
         1.contacts per page
         2.current page

     */
    @Query("from Contacts as c where c.user.user_id =:id")
    public Page<Contacts> findContactByUser(@Param("id")int id , Pageable pageable);


}
