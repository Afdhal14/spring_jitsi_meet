package com.jitsi.meet.controller;

import com.jitsi.meet.service.JitsiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/jitsi")
public class JitsiController {
    private final JitsiService jitsiService;

    public JitsiController(JitsiService jitsiService) {
        this.jitsiService = jitsiService;
    }

    @GetMapping("/generateToken")
    public ResponseEntity<?> generateToken(
            @RequestParam String userName,
            @RequestParam String email,
            @RequestParam(defaultValue = "false") boolean moderator,
            @RequestParam String roomName) {
        try {
            String token = jitsiService.generateToken(userName, email, moderator, roomName);
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
