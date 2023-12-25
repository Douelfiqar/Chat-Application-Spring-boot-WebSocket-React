package com.example.chatapplicationbackend.services;

import com.example.chatapplicationbackend.entities.Log;
import com.example.chatapplicationbackend.repositories.LogRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class LogService {

    private final LogRepository logRepository;


    public List<Log> getAllLogs() {
        return logRepository.findAll();
    }

    public Optional<Log> getLogById(int logId) {
        return logRepository.findById(logId);
    }

    public Log createLog(Log log) {
        return logRepository.save(log);
    }

    public Log updateLog(int logId, Log updatedLog) {
        Optional<Log> existingLogOptional = logRepository.findById(logId);

        if (existingLogOptional.isPresent()) {
            Log existingLog = existingLogOptional.get();
            // Update the fields you want to allow updating
            existingLog.setType(updatedLog.getType());

            // Save the updated log
            return logRepository.save(existingLog);
        } else {
            return null;
        }
    }

    public void deleteLog(int logId) {
        logRepository.deleteById(logId);
    }
}
