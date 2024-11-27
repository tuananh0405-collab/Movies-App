package com.example.anhvt86_3.data.repository;

import androidx.lifecycle.LiveData;

import com.example.anhvt86_3.data.datasource.local.dao.ReminderDAO;
import com.example.anhvt86_3.domain.model.Reminder;
import com.example.anhvt86_3.domain.repository.ReminderRepository;

import java.util.List;

import javax.inject.Inject;

public class ReminderRepositoryImpl implements ReminderRepository {
    private final ReminderDAO reminderDao;

    @Inject
    public ReminderRepositoryImpl(ReminderDAO reminderDao) {
        this.reminderDao = reminderDao;
    }

    @Override
    public void insertReminder(Reminder reminder) {
        reminderDao.insert(reminder);
    }

    @Override
    public void updateReminder(Reminder reminder) {
        reminderDao.updateReminderByMovieId(reminder.getReminderTime(), reminder.getMovieId());
    }

    @Override
    public void deleteReminderByMovieId(int movieId) {
        reminderDao.deleteReminderByMovieId(movieId);
    }

    @Override
    public LiveData<List<Reminder>> getAllReminders() {
        return reminderDao.getAllReminders();
    }

    @Override
    public Reminder getReminderByMovieId(int movieId) {
        return reminderDao.getReminderByMovieId(movieId);
    }
}
