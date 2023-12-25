package com.example.chatapplicationbackend;

import com.example.chatapplicationbackend.entities.User;
import com.example.chatapplicationbackend.entities.dtos.Status;
import com.example.chatapplicationbackend.entities.enums.Permission;
import com.example.chatapplicationbackend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
@AllArgsConstructor
public class ChatapplicationbackendApplication {
	private UserRepository userRepository;
	public static void main(String[] args) {
		SpringApplication.run(ChatapplicationbackendApplication.class, args);
	}

	CommandLineRunner commandLineRunner (){
		return args -> {

		User user = User.builder()
					.username("ALBATROS")
					.firstName("doe")
					.lastName("doe")
					.email("doe@gmail.com")
					.permission(Permission.USER)
					.password("passwd")
					.status(Status.ONLINE)
					.lastConnectionTime(new Date())
					.build();
			User user1 = User.builder()
					.username("fc03")
					.firstName("fc03")
					.lastName("fc03")
					.email("fc03@gmail.com")
					.permission(Permission.USER)
					.password("passwd")
					.status(Status.ONLINE)
					.lastConnectionTime(new Date())
					.build();
			User user2 = User.builder()
					.username("fc04")
					.firstName("fc04")
					.lastName("fc04")
					.email("fc04@gmail.com")
					.permission(Permission.USER)
					.password("passwd")
					.status(Status.OFFLINE)
					.lastConnectionTime(new Date())
					.build();
			User user3 = User.builder()
					.username("DOO")
					.firstName("DOO")
					.lastName("DOO")
					.email("DOO@gmail.com")
					.permission(Permission.USER)
					.password("passwd")
					.status(Status.ONLINE)
					.lastConnectionTime(new Date())
					.build();
			userRepository.save(user3);
			//userRepository.save(user2);

		};
	}
}
