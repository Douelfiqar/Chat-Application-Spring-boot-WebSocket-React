package com.example.chatapplicationbackend;

import com.example.chatapplicationbackend.entities.User;
import com.example.chatapplicationbackend.entities.dtos.Status;
import com.example.chatapplicationbackend.entities.enums.Permission;
import com.example.chatapplicationbackend.repositories.UserRepository;
import com.example.chatapplicationbackend.services.UserService;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
@AllArgsConstructor
public class ChatapplicationbackendApplication {
	private UserRepository userRepository;
	private UserService userService;
	public static void main(String[] args) {
		SpringApplication.run(ChatapplicationbackendApplication.class, args);
	}


	CommandLineRunner commandLineRunner (){
		return args -> {

		User user = User.builder()
					.username("USER1")
					.firstName("user")
					.lastName("rafce")
					.email("user@gmail.com")
					.permission(Permission.USER)
					.password(BCrypt.hashpw("passwd",BCrypt.gensalt()))
					.status(Status.ONLINE)
					.lastConnectionTime(new Date())
					.build();
			userRepository.save(user);

			User user1 = User.builder()
					.username("USER2")
					.firstName("user2")
					.lastName("lioop")
					.email("fc03@gmail.com")
					.permission(Permission.USER)
					.password(userService.hashPassword("passwd"))
					.status(Status.ONLINE)
					.lastConnectionTime(new Date())
					.build();
			userRepository.save(user1);

			User user2 = User.builder()
					.username("USER3OFFLINE")
					.firstName("user3")
					.lastName("fc04")
					.email("fc04@gmail.com")
					.permission(Permission.USER)
					.password(userService.hashPassword("passwd"))
					.status(Status.OFFLINE)
					.lastConnectionTime(new Date())
					.build();
			userRepository.save(user2);

			User user3 = User.builder()
					.username("ADMIN1")
					.firstName("DOO")
					.lastName("DOO")
					.email("DOO@gmail.com")
					.permission(Permission.ADMIN)
					.password(userService.hashPassword("passwd"))
					.status(Status.ONLINE)
					.lastConnectionTime(new Date())
					.build();
			userRepository.save(user3);

			User user4 = User.builder()
					.username("MOD1")
					.firstName("Idriss")
					.lastName("Douelfiqar")
					.email("douidriss@gmail.com")
					.permission(Permission.ADMIN)
					.password(userService.hashPassword("passwd"))
					.status(Status.ONLINE)
					.lastConnectionTime(new Date())
					.build();
			userRepository.save(user4);

		};
	}
}
