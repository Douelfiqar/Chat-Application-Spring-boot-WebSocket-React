package com.example.chatapplicationbackend.controllers;

import com.example.chatapplicationbackend.entities.User;
import com.example.chatapplicationbackend.entities.dtos.LoginDto;
import com.example.chatapplicationbackend.entities.dtos.Status;
import com.example.chatapplicationbackend.entities.enums.Permission;
import com.example.chatapplicationbackend.repositories.UserRepository;
import com.example.chatapplicationbackend.services.UserService;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
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
        List<User> userList = userService.getAllUsers().stream()
                .filter(user -> user.getPermission() == Permission.USER)
                .collect(Collectors.toList());

        return userList;
    }
    @GetMapping("/getAdmins")
    public List<User> getUsersAdmin() {
        List<User> userList = userService.getAllUsers().stream()
                .filter(user -> user.getPermission() == Permission.ADMIN)
                .collect(Collectors.toList());
        return userList;
    }
    @PostMapping("/moveUserToAdmin")
    public void moveUserToAdmin(@RequestBody String username) {
        User user = userRepository.findUserByUsername(username);
        user.setPermission(Permission.ADMIN);

        userRepository.save(user);
    }
    @PostMapping("/moveAdminToUser")
    public void moveAdminToUser(@RequestBody String username) {
        User user = userRepository.findUserByUsername(username);
        user.setPermission(Permission.USER);
        userRepository.save(user);
    }

    @GetMapping("/connectedUsers")
    public List<User> userConnectedList(){
        List<User> userList = userService.getAllUsers();
        List<User> userConnectedList = userList.stream()
                .filter(user -> user.getStatus() == Status.ONLINE)
                .collect(Collectors.toList());

        return userConnectedList;
    }


    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable int userId) {
        return userService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/closeSession")
    public ResponseEntity<Void> closeSession(String username){
        User user = userRepository.findUserByUsername(username);
        user.setStatus(Status.OFFLINE);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }
    @PostMapping
    public ResponseEntity<User> authentication(@RequestBody LoginDto loginDto) {

        User user = userRepository.findUserByUsername(loginDto.username());

        if (BCrypt.checkpw(loginDto.password(), user.getPassword())) {
            user.setStatus(Status.ONLINE);
            userRepository.save(user);
            return ResponseEntity.ok(user);
        }


        // Authentication failed
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        userRepository.deleteUserByUsername(username);
        return ResponseEntity.noContent().build();
    }
}
