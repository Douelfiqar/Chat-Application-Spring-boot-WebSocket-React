package com.example.chatapplicationbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class ChatNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    private User userSender;
    @ManyToOne
    private User userReceiver;
    @ManyToOne
    private Message message;
}