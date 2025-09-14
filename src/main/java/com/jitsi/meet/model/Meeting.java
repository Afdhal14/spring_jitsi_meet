package com.jitsi.meet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomName;
    private String createdBy;
    private boolean teacherJoined = false;
    private int teacherCount = 0;
    private int studentCount = 0;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Meeting(Long id, String roomName, String createdBy, boolean teacherJoined, LocalDateTime createdAt) {
        this.id = id;
        this.roomName = roomName;
        this.createdBy = createdBy;
        this.teacherJoined = teacherJoined;
        this.createdAt = createdAt;
    }

    public Meeting() {
    }

    public int getTeacherCount() {
        return teacherCount;
    }

    public void setTeacherCount(int teacherCount) {
        this.teacherCount = teacherCount;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isTeacherJoined() {
        return teacherJoined;
    }

    public void setTeacherJoined(boolean teacherJoined) {
        this.teacherJoined = teacherJoined;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
