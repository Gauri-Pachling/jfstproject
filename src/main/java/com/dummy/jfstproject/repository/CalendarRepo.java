package com.dummy.jfstproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dummy.jfstproject.model.CalendarEvent;

@Repository
public interface CalendarRepo extends JpaRepository<CalendarEvent, Long> {
    List<CalendarEvent> findByUserId(Long userId);
}
