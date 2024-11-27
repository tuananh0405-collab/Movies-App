package com.example.anhvt86_3.domain.usecase;

import androidx.lifecycle.LiveData;

import com.example.anhvt86_3.domain.model.Reminder;
import com.example.anhvt86_3.domain.repository.ReminderRepository;

import java.util.List;

import javax.inject.Inject;

public class ReminderUseCase {
    private final ReminderRepository reminderRepository;

    @Inject
    public ReminderUseCase(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    public void insertReminder(Reminder reminder) {
        reminderRepository.insertReminder(reminder);
    }

    public void updateReminder(Reminder reminder) {
        reminderRepository.updateReminder(reminder);
    }

    public void deleteReminderByMovieId(int movieId) {
        reminderRepository.deleteReminderByMovieId(movieId);
    }

    public LiveData<List<Reminder>> getAllReminders() {
        return reminderRepository.getAllReminders();
    }

    public Reminder getReminderByMovieId(int movieId) {
        return reminderRepository.getReminderByMovieId(movieId);
    }
}
