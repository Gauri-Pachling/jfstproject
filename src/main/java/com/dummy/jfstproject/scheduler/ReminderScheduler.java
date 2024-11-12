package com.dummy.jfstproject.scheduler;

import com.dummy.jfstproject.model.Reminder;
import com.dummy.jfstproject.service.EmailService;
import com.dummy.jfstproject.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.mail.*;
import java.util.Date;
import java.util.List;

@Component
public class ReminderScheduler {

    @Autowired
    private ReminderService reminderService;

    @Autowired
    private EmailService emailService;

    // Check for reminders every hour
    @Scheduled(fixedRate = 3600000)  // 1 hour in milliseconds
    public void sendReminderEmails() {
        List<Reminder> reminders = reminderService.getAllReminders();
        Date now = new Date();

        for (Reminder reminder : reminders) {
            if (reminder.getDueDate() != null &&
                reminder.getDueDate().before(new Date(now.getTime() + 3600000)) &&  // Due within 1 hour
                reminder.getDueDate().after(now)) {

                try {
                    emailService.sendEmail(
                        reminder.getUser().getEmail(),
                        "Reminder: " + reminder.getTitle(),
                        "Don't forget: " + reminder.getDescription()
                    );
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
