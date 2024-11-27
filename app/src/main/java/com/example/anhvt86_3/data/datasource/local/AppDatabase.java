package com.example.anhvt86_3.data.datasource.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.anhvt86_3.data.datasource.local.dao.MovieDAO;
import com.example.anhvt86_3.data.datasource.local.dao.ReminderDAO;
import com.example.anhvt86_3.data.datasource.local.entity.MovieEntity;
import com.example.anhvt86_3.domain.model.Reminder;


@Database(entities = {MovieEntity.class, Reminder.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract MovieDAO movieDAO();

    public abstract ReminderDAO reminderDAO();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "movie_database")
                    .allowMainThreadQueries()
//                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
