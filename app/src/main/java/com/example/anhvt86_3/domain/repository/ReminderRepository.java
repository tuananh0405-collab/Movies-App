package com.example.anhvt86_3.domain.repository;

import androidx.lifecycle.LiveData;

import com.example.anhvt86_3.domain.model.Reminder;

import java.util.List;

public interface ReminderRepository {
    void insertReminder(Reminder reminder);
    void updateReminder(Reminder reminder);
    void deleteReminderByMovieId(int movieId);
    LiveData<List<Reminder>> getAllReminders();
    Reminder getReminderByMovieId(int movieId);
}
