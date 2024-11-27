package com.example.anhvt86_3.domain.model;

public class UserProfile {
    private String fullName;
    private String email;
    private String birthday;
    private String profileImage;
    private String gender;

    public UserProfile() {
    }

    public UserProfile(String fullName, String email, String birthday, String profileImage, String gender) {
        this.fullName = fullName;
        this.email = email;
        this.birthday = birthday;
        this.profileImage = profileImage;
        this.gender = gender;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

