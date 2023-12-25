package com.example.chatapplicationbackend.entities;

import com.example.chatapplicationbackend.entities.dtos.Status;
import com.example.chatapplicationbackend.entities.enums.Permission;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "username", unique = true)
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Permission permission;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date lastConnectionTime;
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "userSender", fetch = FetchType.EAGER)
    private Collection<ChatNotification> message_sended = new ArrayList<>();
    @OneToMany(mappedBy = "userReceiver", fetch = FetchType.EAGER)
    private Collection<ChatNotification> message_received = new ArrayList<>();
  //  @OneToMany(mappedBy = "user")
   // private Collection<Groupe> groupe;
}
