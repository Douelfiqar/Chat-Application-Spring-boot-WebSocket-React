package com.example.chatapplicationbackend.controllers;

import com.example.chatapplicationbackend.entities.ChatNotification;
import com.example.chatapplicationbackend.entities.Message;
import com.example.chatapplicationbackend.entities.dtos.MessageRequestDto;
import com.example.chatapplicationbackend.entities.User;
import com.example.chatapplicationbackend.entities.dtos.PublicMessage;
import com.example.chatapplicationbackend.repositories.ChatNotificationRepository;
import com.example.chatapplicationbackend.repositories.MessageRepository;
import com.example.chatapplicationbackend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@AllArgsConstructor
public class MessageController {
    private UserRepository userRepository;
    private MessageRepository messageRepository;
    private ChatNotificationRepository chatNotificationRepository;

    @MessageMapping("/publicRoomLink")
    @SendTo("/chatroom/public")
    public PublicMessage greeting(@Payload PublicMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return message;
    }
    private final SimpMessagingTemplate messagingTemplate;
    @MessageMapping("/chat")
    public void processMessage(@Payload MessageRequestDto messageDto) {
        //String savedMsg = chatMessageService.save(chatMessage);
        //User user = message.getReceiverMessage().stream().findFirst().orElseThrow();

        User sender = userRepository.findUserByUsername(messageDto.usernameSender());
        User receiver = userRepository.findUserByUsername(messageDto.usernameReceiver());

        Message message = Message.builder()
                .timeStamp(new Date())
                .content(messageDto.content())
                .build();

        //message = messageRepository.save(message);
        /*ChatNotification chatNotification = ChatNotification.builder()
                .message(message)
                .userReceiver(receiver)
                .userSender(sender)
                .build();*/

        //chatNotification = chatNotificationRepository.save(chatNotification);

        messagingTemplate.convertAndSendToUser(
                receiver.getUsername(), "/queue/messages",
                new ChatNotification(

                        1,
                        sender,
                        receiver,
                        message
                )
        );
    }
}
