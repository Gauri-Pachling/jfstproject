package com.dummy.jfstproject.repository;

import com.dummy.jfstproject.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReminderRepo extends JpaRepository<Reminder, Long> {
    List<Reminder> findByUserId(Long userId);  
     
    @Query("SELECT r FROM Reminder r WHERE r.remDate <= CURRENT_TIMESTAMP AND r.notified = false")
    List<Reminder> findDueReminders(); 
}
