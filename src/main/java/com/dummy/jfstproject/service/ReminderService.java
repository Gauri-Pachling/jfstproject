package com.dummy.jfstproject.service;

import com.dummy.jfstproject.model.Reminder;
import com.dummy.jfstproject.repository.ReminderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReminderService {

    @Autowired
    private ReminderRepo reminderRepo;

    public Reminder saveReminder(Reminder reminder) {
        return reminderRepo.save(reminder);
    }

    public List<Reminder> getRemindersByUser(Long userId) {
        return reminderRepo.findByUserId(userId);
    }

    public List<Reminder> getAllReminders() {
        return reminderRepo.findAll();
    }
}
