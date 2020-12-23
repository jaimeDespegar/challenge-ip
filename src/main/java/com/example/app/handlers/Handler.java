package com.example.app.handlers;

public interface Handler {

    boolean canHandle(Context context);
    void handle(Context context);

}