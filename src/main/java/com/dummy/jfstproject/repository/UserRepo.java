package com.dummy.jfstproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dummy.jfstproject.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    
    // Query method to find a user by username, returning an Optional
@Query("SELECT u FROM User u WHERE u.username = :username")
Optional<User> findByUsernameCustom(@Param("username") String username);

User findByUsername(String username);

Optional<User> findByEmail(String email);


}