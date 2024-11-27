package com.example.anhvt86_3.app.di;

import android.app.Application;

import com.example.anhvt86_3.data.repository.MovieRepositoryImpl;
import com.example.anhvt86_3.domain.repository.IMovieRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    public Application provideApplication() {
        return application;
    }

    @Provides
    public IMovieRepository provideMovieRepository(MovieRepositoryImpl movieRepositoryImpl) {
        return movieRepositoryImpl;
    }
}
