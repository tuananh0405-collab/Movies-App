package com.example.anhvt86_3.app.utils;

public class NetworkState {
    private final Status status;
    private final String msg;

    public NetworkState(Status status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Status getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public enum Status{
        LOADING,
        LOADED,
        ERROR
    }
}