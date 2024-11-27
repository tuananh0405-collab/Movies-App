package com.example.anhvt86_3.domain.repository;

import androidx.lifecycle.LiveData;

import com.example.anhvt86_3.domain.model.UserProfile;

public interface UserRepository {
    LiveData<UserProfile> getUserProfile(String userId);
    void saveUserProfile(String userId, UserProfile userProfile);
}
