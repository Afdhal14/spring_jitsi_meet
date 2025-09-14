package com.jitsi.meet.service;

import com.jitsi.meet.model.Meeting;
import com.jitsi.meet.repository.MeetingRepository;
import com.jitsi.meet.util.JaaSJwtBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;

@Service
public class JitsiService {

    private final RSAPrivateKey privateKey;
    private final MeetingRepository meetingRepository;

    @Value("${jitsi.api.key}")
    private String apiKey;

    @Value("${jitsi.app.id}")
    private String appId;

    public JitsiService(RSAPrivateKey privateKey, MeetingRepository meetingRepository) {
        this.privateKey = privateKey;
        this.meetingRepository = meetingRepository;
    }

    public String generateToken(String userName, String email, boolean moderator,String roomName) {

        Meeting meeting = meetingRepository.findByRoomName(roomName);

        if (moderator) {
            if (meeting == null) {
                meeting = new Meeting();
                meeting.setRoomName(roomName);
                meeting.setCreatedBy(email);
            }
            meeting.setTeacherJoined(true);
            meetingRepository.save(meeting);
        } else {
            if (meeting == null || !meeting.isTeacherJoined()) {
                throw new RuntimeException("Teacher has not joined yet.");
            }
        }

        return new JaaSJwtBuilder()
                .withDefaults()
                .withApiKey(apiKey)
                .withAppID(appId)
                .withUser(userName, email, moderator)
                .signWith(privateKey);
    }
}
