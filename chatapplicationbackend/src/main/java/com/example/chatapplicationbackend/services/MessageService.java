package com.example.chatapplicationbackend.services;

import com.example.chatapplicationbackend.entities.Message;
import com.example.chatapplicationbackend.repositories.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(int messageId) {
        return messageRepository.findById(messageId);
    }

    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    public Message updateMessage(int messageId, Message updatedMessage) {
        Optional<Message> existingMessageOptional = messageRepository.findById(messageId);

        if (existingMessageOptional.isPresent()) {
            Message existingMessage = existingMessageOptional.get();
            // Update the fields you want to allow updating
            existingMessage.setContent(updatedMessage.getContent());

            // Save the updated message
            return messageRepository.save(existingMessage);
        } else {
            return null;
        }
    }

    public void deleteMessage(int messageId) {
        messageRepository.deleteById(messageId);
    }
}

