package com.example.anhvt86_3.app.di;

import com.example.anhvt86_3.presentation.activities.MainActivity;
import com.example.anhvt86_3.presentation.fragments.FavoriteFragment;
import com.example.anhvt86_3.presentation.fragments.ListFragment;
import com.example.anhvt86_3.presentation.fragments.SettingsFragment;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {NetworkModule.class, AppModule.class})
public interface AppComponent {
    void inject(MainActivity activity);
    void inject(ListFragment fragment);
    void inject(FavoriteFragment fragment);
    void inject(SettingsFragment fragment);
}
