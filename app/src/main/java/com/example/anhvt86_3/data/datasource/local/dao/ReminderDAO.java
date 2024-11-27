package com.example.anhvt86_3.data.datasource.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.anhvt86_3.domain.model.Reminder;

import java.util.List;

@Dao
public interface ReminderDAO {
    @Insert
    void insert(Reminder reminder);

    @Query("UPDATE reminders SET reminderTime = :reminderTime WHERE movieId = :movieId")
    void updateReminderByMovieId(long reminderTime, int movieId);

    @Query("DELETE FROM reminders WHERE movieId = :movieId")
    void deleteReminderByMovieId(int movieId);

    @Query("SELECT * FROM reminders ORDER BY reminderTime ASC")
    LiveData<List<Reminder>> getAllReminders();

    @Query("SELECT * FROM reminders WHERE movieId = :movieId LIMIT 1")
    Reminder getReminderByMovieId(int movieId);
}
