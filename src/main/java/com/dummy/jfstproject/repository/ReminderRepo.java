package com.dummy.jfstproject.repository;

import com.dummy.jfstproject.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReminderRepo extends JpaRepository<Reminder, Long> {
    List<Reminder> findByUserId(Long userId);
}