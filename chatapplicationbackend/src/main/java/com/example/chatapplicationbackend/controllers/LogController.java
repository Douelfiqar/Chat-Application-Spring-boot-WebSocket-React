package com.example.chatapplicationbackend.controllers;

import com.example.chatapplicationbackend.entities.Log;
import com.example.chatapplicationbackend.services.LogService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
@AllArgsConstructor
public class LogController {

    private final LogService logService;

    @GetMapping
    public List<Log> getAllLogs() {
        return logService.getAllLogs();
    }

    @GetMapping("/{logId}")
    public ResponseEntity<Log> getLogById(@PathVariable int logId) {
        return logService.getLogById(logId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Log> createLog(@RequestBody Log log) {
        Log createdLog = logService.createLog(log);
        return ResponseEntity.ok(createdLog);
    }

    @PutMapping("/{logId}")
    public ResponseEntity<Log> updateLog(@PathVariable int logId, @RequestBody Log updatedLog) {
        Log updated = logService.updateLog(logId, updatedLog);

        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{logId}")
    public ResponseEntity<Void> deleteLog(@PathVariable int logId) {
        logService.deleteLog(logId);
        return ResponseEntity.noContent().build();
    }
}
