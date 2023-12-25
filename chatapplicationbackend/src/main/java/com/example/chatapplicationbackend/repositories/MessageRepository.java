package com.example.chatapplicationbackend.repositories;

import com.example.chatapplicationbackend.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}
