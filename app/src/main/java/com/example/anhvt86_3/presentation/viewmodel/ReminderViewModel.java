package com.example.anhvt86_3.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.anhvt86_3.domain.model.Reminder;
import com.example.anhvt86_3.domain.usecase.ReminderUseCase;

import java.util.List;

import javax.inject.Inject;

public class ReminderViewModel extends ViewModel {
    private final ReminderUseCase reminderUseCase;

    @Inject
    public ReminderViewModel(ReminderUseCase reminderUseCase) {
        this.reminderUseCase = reminderUseCase;
        getAllReminders();
    }

    public void insertReminder(Reminder reminder) {
        reminderUseCase.insertReminder(reminder);
    }

    public void updateReminder(Reminder reminder) {
        reminderUseCase.updateReminder(reminder);
    }

    public void deleteReminderByMovieId(int movieId) {
        reminderUseCase.deleteReminderByMovieId(movieId);
    }


    public LiveData<List<Reminder>> getAllReminders() {
        return reminderUseCase.getAllReminders();
    }

    public Reminder getReminderByMovieId(int movieId) {
        return reminderUseCase.getReminderByMovieId(movieId);
    }
}
