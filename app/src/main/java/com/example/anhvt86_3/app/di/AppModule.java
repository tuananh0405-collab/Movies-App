package com.example.anhvt86_3.app.di;

import android.app.Application;

import com.example.anhvt86_3.data.datasource.remote.MovieRetrofitAPI;
import com.example.anhvt86_3.data.repository.MovieRepositoryImpl;
import com.example.anhvt86_3.data.repository.UserProfileRepositoryImpl;
import com.example.anhvt86_3.domain.repository.IMovieRepository;
import com.example.anhvt86_3.domain.repository.UserRepository;
import com.example.anhvt86_3.presentation.viewmodel.ProfileViewModel;

import javax.inject.Singleton;

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
    public IMovieRepository provideMovieRepository(MovieRepositoryImpl movieRepositoryImpl, MovieRetrofitAPI movieRetrofitAPI) {
        return movieRepositoryImpl;
    }

    @Provides
    @Singleton
    public UserRepository provideUserRepository() {
        return new UserProfileRepositoryImpl();
    }

    @Provides
    @Singleton
    public ProfileViewModel provideProfileViewModel(UserRepository userRepository) {
        return new ProfileViewModel(userRepository);
    }
}
