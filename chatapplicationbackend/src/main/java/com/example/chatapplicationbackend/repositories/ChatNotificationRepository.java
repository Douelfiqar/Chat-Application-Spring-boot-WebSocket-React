package com.example.chatapplicationbackend.repositories;

import com.example.chatapplicationbackend.entities.ChatNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatNotificationRepository extends JpaRepository<ChatNotification, Integer> {
}
