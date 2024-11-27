package com.example.anhvt86_3.domain.usecase;

import androidx.lifecycle.LiveData;

import com.example.anhvt86_3.domain.model.UserProfile;
import com.example.anhvt86_3.domain.repository.UserRepository;

import javax.inject.Inject;

public class UserProfileUseCase {
    private final UserRepository userRepository;

    @Inject
    public UserProfileUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<UserProfile> getUserProfile(String userId) {
        return userRepository.getUserProfile(userId);
    }

    public void saveUserProfile(String userId, UserProfile userProfile) {
        userRepository.saveUserProfile(userId, userProfile);
    }
}
