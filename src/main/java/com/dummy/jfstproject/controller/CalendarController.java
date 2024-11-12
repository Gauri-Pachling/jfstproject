package com.dummy.jfstproject.controller;

import com.dummy.jfstproject.model.CalendarEvent;
import com.dummy.jfstproject.model.User;
import com.dummy.jfstproject.service.CalendarService;
import com.dummy.jfstproject.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    @Autowired
    private CalendarService calendarEventService;

    @Autowired
    private UserRepo userRepository;

    @GetMapping("/{userId}")
    public ResponseEntity<List<CalendarEvent>> getEventsByUserId(@PathVariable("userId") Long userId) {
        List<CalendarEvent> events = calendarEventService.getEventsByUserId(userId);
        System.out.println(events);
        return ResponseEntity.ok(events);
    }

    @PostMapping
public ResponseEntity<CalendarEvent> createEvent(@RequestBody CalendarEvent calendarEvent) {
    if (calendarEvent.getUser() == null || calendarEvent.getUser().getId() == null) {
        System.out.println("User information missing in request payload.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);  // Return null for the CalendarEvent body
    }

    Long userId = calendarEvent.getUser().getId();
    Optional<User> userOpt = userRepository.findById(userId);

    if (!userOpt.isPresent()) {
        System.out.println("User not found for ID: " + userId);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);  // Return null for the CalendarEvent body
    }

    //System.out.println(calendarEvent.getEventTitle()+"title");

    
    User user = userOpt.get();
    calendarEvent.setUser(user);
    calendarEvent.setEventTitle(calendarEvent.getEventTitle());
        calendarEvent.setDescription(calendarEvent.getDescription());
        calendarEvent.setStartTime(calendarEvent.getStartTime());
        calendarEvent.setEndTime(calendarEvent.getEndTime());

        // System.out.println(calendarEvent.getEndTime());
        // System.out.println(calendarEvent.getEndTime());

    CalendarEvent createdEvent = calendarEventService.createEvent(calendarEvent);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
}


}
