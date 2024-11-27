package com.example.anhvt86_3.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.anhvt86_3.domain.model.UserProfile;
import com.example.anhvt86_3.domain.repository.UserRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileRepositoryImpl implements UserRepository {
    private DatabaseReference databaseReference;

    public UserProfileRepositoryImpl() {
        databaseReference = FirebaseDatabase.getInstance().getReference("user_profiles");
    }

    @Override
    public LiveData<UserProfile> getUserProfile(String userId) {
        MutableLiveData<UserProfile> userProfileLiveData = new MutableLiveData<>();
        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                userProfileLiveData.postValue(userProfile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "Failed to read user profile", error.toException());

            }
        });
        return userProfileLiveData;
    }

    @Override
    public void saveUserProfile(String userId, UserProfile userProfile) {
        databaseReference.child(userId).setValue(userProfile);
    }
}
