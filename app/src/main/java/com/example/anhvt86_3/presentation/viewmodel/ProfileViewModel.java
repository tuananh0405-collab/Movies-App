package com.example.anhvt86_3.presentation.viewmodel;

import android.graphics.Bitmap;
import android.util.Base64;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.anhvt86_3.domain.model.UserProfile;
import com.example.anhvt86_3.domain.repository.UserRepository;
import com.example.anhvt86_3.domain.usecase.UserProfileUseCase;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;

public class ProfileViewModel extends ViewModel {
    private final UserProfileUseCase userProfileUseCase;

    private final MutableLiveData<UserProfile> userProfileLiveData = new MutableLiveData<>();

    @Inject
    public ProfileViewModel(UserRepository userRepository) {
        userProfileUseCase = new UserProfileUseCase(userRepository);
        fetchUserProfile("anh");
    }

    public LiveData<UserProfile> getUserProfileLiveData() {
        return userProfileLiveData;
    }

    private void fetchUserProfile(String userId) {
        LiveData<UserProfile> userProfile = userProfileUseCase.getUserProfile(userId);
        userProfile.observeForever(userProfileLiveData::postValue);
    }

    public void saveUserProfile(UserProfile userProfile) {
        userProfileUseCase.saveUserProfile("anh", userProfile);
    }

    public String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] byteArray = baos.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
