package com.example.chatapplicationbackend.repositories;


import com.example.chatapplicationbackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByUsername(String username);
    void deleteUserByUsername(String username);
}
