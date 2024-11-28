package com.example.anhvt86_3.presentation.workmanager;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.anhvt86_3.R;
import com.example.anhvt86_3.presentation.activities.MainActivity;

public class ReminderWorker extends Worker {
    private static final String CHANNEL_ID = "ReminderChannel";
    int notificationId;
    Context context;
    public ReminderWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        // Lấy dữ liệu từ inputData
        String movieTitle = getInputData().getString("movieTitle");
         notificationId = getInputData().getInt("notificationId", 0);

        // Hiển thị thông báo
        showNotification(movieTitle, notificationId);

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

        // Tạo Intent để mở MainActivity khi người dùng click vào thông báo
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Đặt thêm dữ liệu nếu cần (ví dụ, thông tin về movie)
        intent.putExtra("notificationId", notificationId);
        Log.e("ReminderWorker", "Notification Intent: " + intent.getExtras());

        // PendingIntent để mở MainActivity
        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(),
                notificationId,  // notificationId dùng làm requestCode để phân biệt các thông báo
                intent,
                PendingIntent.FLAG_IMMUTABLE
        );

        // Xây dựng Notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_about)
                .setContentTitle("Movie Reminder")
                .setContentText("Don't forget to watch: " + movieTitle)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent); // Đặt PendingIntent vào Notification


        notificationManager.notify(notificationId, notificationBuilder.build());
    }
}
