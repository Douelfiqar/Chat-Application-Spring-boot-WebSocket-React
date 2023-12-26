package com.example.chatapplicationbackend.entities.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

public record PublicMessage(String content, String usernameSender, String imageUrl, Date date) {
}
