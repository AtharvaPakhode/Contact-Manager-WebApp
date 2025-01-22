package com.yourcontacts.repo;

import com.yourcontacts.models.Contacts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on the Contacts entity.
 * It extends JpaRepository to provide standard database operations for Contacts.
 */
public interface ContactRepository extends JpaRepository<Contacts, Integer> {

    /**
     * Custom method to find contacts belonging to a specific user by their user ID.
     * The method uses pagination to retrieve contacts in a paged manner.
     *
     * @param id The user ID for which contacts are to be fetched.
     * @param pageable Contains information about pagination, including the number of contacts per page and the current page number.
     * @return A Page of Contacts associated with the specified user ID.
     */
    @Query("from Contacts c where c.user.user_id = :id")
    Page<Contacts> findContactByUser(@Param("id") int id, Pageable pageable);


    @Query("SELECT COUNT(c) FROM Contacts c WHERE c.user.user_id = :id")
    int countactCountByUser(@Param("id") int id);


}
