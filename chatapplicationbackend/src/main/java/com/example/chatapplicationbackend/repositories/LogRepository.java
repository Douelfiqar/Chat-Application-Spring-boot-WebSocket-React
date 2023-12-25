package com.example.chatapplicationbackend.repositories;

import com.example.chatapplicationbackend.entities.Groupe;
import com.example.chatapplicationbackend.entities.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Integer> {
}
