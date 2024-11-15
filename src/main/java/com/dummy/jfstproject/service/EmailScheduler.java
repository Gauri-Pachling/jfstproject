package com.dummy.jfstproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.dummy.jfstproject.model.Reminder;

import jakarta.mail.MessagingException;

@Service
public class EmailScheduler {

    @Autowired
    private ReminderService reminderService;

    @Autowired
    private EmailService emailService;

    @Scheduled(fixedRate = 60000) // Check every 60 seconds
    public void sendScheduledEmails() {
        List<Reminder> remindersDue = reminderService.getRemindersDueForEmail();
        
        for (Reminder reminder : remindersDue) {
            try {
                emailService.sendEmail(
                    reminder.getUser().getEmail(),
                    "Reminder Notification",
                    "Reminder Title: " + reminder.getTitle() + "\nDescription: " + reminder.getDescription()
                );

                // Mark the reminder as notified
                reminder.setNotified(true);
                reminderService.saveReminder(reminder);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
}
