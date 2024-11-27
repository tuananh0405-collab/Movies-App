package com.example.anhvt86_3.app.utils;


import com.example.anhvt86_3.R;

public class Functionutils {
    public static int displayStar(boolean isFavorite) {
        return isFavorite ? R.drawable.ic_like : R.drawable.ic_dislike;
    }
}
