package com.dummy.jfstproject.service;

import com.dummy.jfstproject.model.CalendarEvent;
import com.dummy.jfstproject.repository.CalendarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarService {

    @Autowired
    private CalendarRepo calendarRepo;

    public List<CalendarEvent> getEventsByUserId(Long userId) {
        return calendarRepo.findByUserId(userId);
    }

    public CalendarEvent createEvent(CalendarEvent calendarEvent) {         // inserts in table
        return calendarRepo.save(calendarEvent);
    }
}
