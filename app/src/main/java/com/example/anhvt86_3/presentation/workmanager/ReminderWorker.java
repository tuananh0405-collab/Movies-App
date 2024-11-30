package com.example.anhvt86_3.presentation.workmanager;


import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Looper;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.anhvt86_3.R;
import com.example.anhvt86_3.presentation.activities.MainActivity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Handler;

public class ReminderWorker extends Worker {
    private static final String CHANNEL_ID = "ReminderChannel";
    int notificationId;
    Context context;
    String movieTitle;
    String overview;
    String posterPath;
    Bitmap bitmap;

    public ReminderWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        // Lấy dữ liệu từ inputData
        movieTitle = getInputData().getString("movieTitle");
        notificationId = getInputData().getInt("notificationId", 0);
        overview = getInputData().getString("overview");
        posterPath = getInputData().getString("posterPath");
        Log.e("Reminder", "Notification ID: " + notificationId + " \nMovie Title: " + movieTitle + " \nOverview: " + overview + " \nPoster Path: " + posterPath + " \nThread: " + Thread.currentThread().getName());
        try {
            bitmap = Picasso.get().load("https://image.tmdb.org/t/p/w500" + posterPath).get();
            // Do something with the bitmap, like saving it to a file
            Log.e("ReminderWorker", "Bitmap loaded successfully");
        } catch (Exception e) {
            Log.e("ReminderWorker", "Failed to load image: " + e.getMessage());
        }
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
            Uri sound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.tung_cach);
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Reminders",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setSound(sound,attributes);
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
        // COLLAPSED NOTIFICATION VIEW
        RemoteViews notificationLayout = new RemoteViews(context.getPackageName(), R.layout.custom_notification);
        notificationLayout.setTextViewText(R.id.tvNotificationTitle, movieTitle);
        notificationLayout.setTextViewText(R.id.tvNotificationContent, "Time to go get the ticket !");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String strDate = sdf.format(new Date());
        notificationLayout.setTextViewText(R.id.tvNotificationTime, strDate);
        notificationLayout.setImageViewBitmap(R.id.imgCustomNotification, bitmap);
        // EXPANDED NOTIFICATION VIEW
        RemoteViews notificationLayoutExpanded = new RemoteViews(context.getPackageName(), R.layout.custome_notification_expanded);
        notificationLayoutExpanded.setTextViewText(R.id.tvNotificationTitleExpanded, movieTitle);
        notificationLayoutExpanded.setTextViewText(R.id.tvNotificationContentExpanded, overview);
        notificationLayoutExpanded.setImageViewBitmap(R.id.imgCustomNotificationExpanded, bitmap);
//        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Uri sound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.tung_cach);

        // Xây dựng Notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_about)
                .setCustomContentView(notificationLayout)
                .setCustomBigContentView(notificationLayoutExpanded)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(sound)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        notificationManager.notify(notificationId, notificationBuilder.build());
    }
}
