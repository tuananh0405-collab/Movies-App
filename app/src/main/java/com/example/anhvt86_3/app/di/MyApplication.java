package com.example.anhvt86_3.app.di;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public AppComponent appComponent = DaggerAppComponent.builder()
            .appModule(new AppModule(this))
            .build();
}
