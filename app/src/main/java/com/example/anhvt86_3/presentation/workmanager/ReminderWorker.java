package com.example.anhvt86_3.presentation.workmanager;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.anhvt86_3.R;

public class ReminderWorker extends Worker {
    private static final String CHANNEL_ID = "ReminderChannel";
    int notificationId;
    public ReminderWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
//        FavoriteMovieDatabase db = Room.databaseBuilder(getApplicationContext(), FavoriteMovieDatabase.class, "favorite_movie_database")
//                .allowMainThreadQueries()
//                .build();
//        ReminderDAO reminderDao = db.reminderDao();
        // Lấy dữ liệu từ inputData
        String movieTitle = getInputData().getString("movieTitle");
         notificationId = getInputData().getInt("notificationId", 0);

        // Hiển thị thông báo
        showNotification(movieTitle, notificationId);
//        reminderDao.deleteReminderByMovieId(notificationId);

        sendBroadcastToUpdateUI();


        return Result.success();
    }
    private void sendBroadcastToUpdateUI() {
        Intent intent = new Intent("UPDATE_REMINDERS");
        intent.putExtra("movieId", notificationId);
        getApplicationContext().sendBroadcast(intent);
    }
    private void showNotification(String movieTitle, int notificationId) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);

        // Tạo Notification Channel (chỉ cần tạo một lần)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Reminders",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(channel);
        }

        // Xây dựng Notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_about)
                .setContentTitle("Movie Reminder")
                .setContentText("Don't forget to watch: " + movieTitle)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        notificationManager.notify(notificationId, notificationBuilder.build());
    }
}
