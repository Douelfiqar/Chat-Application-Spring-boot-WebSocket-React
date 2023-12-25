package com.example.chatapplicationbackend.entities.dtos;

public record MessageRequestDto(String content, String usernameSender, String usernameReceiver) {
}
