package com.example.chatapplicationbackend.controllers;

import com.example.chatapplicationbackend.entities.User;
import com.example.chatapplicationbackend.entities.dtos.LoginDto;
import com.example.chatapplicationbackend.entities.dtos.Status;
import com.example.chatapplicationbackend.repositories.UserRepository;
import com.example.chatapplicationbackend.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private  final UserRepository userRepository;
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/connectedUsers")
    public List<User> userConnectedList(){
        List<User> userList = userService.getAllUsers();
        List<User> userConnectedList = userList.stream()
                .filter(user -> user.getStatus() == Status.ONLINE)
                .collect(Collectors.toList());
        //userConnectedList.stream().forEach(System.out::println);
        System.out.println("hh");
        return userConnectedList;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable int userId) {
        return userService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> authentification(@RequestBody LoginDto loginDto) {
        User user1 = userRepository.findUserByUsername(loginDto.username());
        if(user1 != null) {
            if(loginDto.password().equals(user1.getPassword()))
                return ResponseEntity.ok(user1);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable int userId, @RequestBody User updatedUser) {
        User updated = userService.updateUser(userId, updatedUser);

        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
