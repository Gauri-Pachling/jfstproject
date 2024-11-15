package com.dummy.jfstproject.controller;

import com.dummy.jfstproject.model.Reminder;
import com.dummy.jfstproject.model.User;
import com.dummy.jfstproject.service.ReminderService;

import jakarta.mail.MessagingException;

import com.dummy.jfstproject.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.dummy.jfstproject.service.EmailService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<Reminder> createReminder(@RequestBody Reminder reminder) {
        // Check if user information is present in the request
        if (reminder.getUser() == null || reminder.getUser().getId() == null) {
            System.out.println("User information missing in request payload.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);  // Return null for the Reminder body
        }

        Long userId = reminder.getUser().getId();
        Optional<User> userOpt = userRepository.findById(userId);

        // Check if the user exists in the database
        if (!userOpt.isPresent()) {
            System.out.println("User not found for ID: " + userId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);  // Return null for the Reminder body
        }

        // Set the user to the reminder
        User user = userOpt.get();
        reminder.setUser(user);

        // Set the title, description, due date, and reminder date from the incoming request body
        reminder.setTitle(reminder.getTitle());
        reminder.setDescription(reminder.getDescription());
        reminder.setDueDate(reminder.getDueDate());
        reminder.setRemDate(reminder.getRemDate());

        System.out.println(reminder.getDueDate());
        System.out.println(reminder.getRemDate());

        // Proceed to save the reminder after associating it with the user
        Reminder createdReminder = reminderService.saveReminder(reminder);

        // try {
        //     emailService.sendEmail(
        //         user.getEmail(),
        //         "New Reminder Created",
        //         "Reminder Title: " + reminder.getTitle() + "\nDescription: " + reminder.getDescription()
        //     );
        //     System.out.println("Reminder creation email sent successfully.");
        // } catch (MessagingException e) {
        //     e.printStackTrace();
        // }


        return ResponseEntity.status(HttpStatus.CREATED).body(createdReminder);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reminder>> getRemindersByUser(@PathVariable Long userId) {
        List<Reminder> reminders = reminderService.getRemindersByUser(userId);
        return ResponseEntity.ok(reminders);
    }
}
