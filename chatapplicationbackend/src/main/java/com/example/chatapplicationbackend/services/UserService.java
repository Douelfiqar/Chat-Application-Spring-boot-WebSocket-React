package com.example.chatapplicationbackend.services;

import com.example.chatapplicationbackend.entities.User;
import com.example.chatapplicationbackend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    // Hash a password using BCrypt
    public String hashPassword(String plainTextPassword) {
        // Gensalt's log_rounds parameter determines the complexity
        // The higher the value, the more work the hashing function will do
        int logRounds = 12;
        String hashedPassword = BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(logRounds));
        return hashedPassword;
    }
    public boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }

    public Optional<User> getUserById(int userId) {
        return userRepository.findById(userId);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(int userId, User updatedUser) {
        // Check if the user with the given ID exists
        Optional<User> existingUserOptional = userRepository.findById(userId);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            // Update the fields you want to allow updating
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setEmail(updatedUser.getEmail());

            // Save the updated user
            return userRepository.save(existingUser);
        } else {
            // Handle the case where the user with the given ID is not found
            // You may choose to throw an exception or handle it differently based on your requirements
            return null;
        }
    }

    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }
}
