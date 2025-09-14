package com.jitsi.meet.repository;

import com.jitsi.meet.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting,Long> {
    Meeting findByRoomName(String roomName);
}
