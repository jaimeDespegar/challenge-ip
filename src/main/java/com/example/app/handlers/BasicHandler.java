package com.example.app.handlers;

public class BasicHandler implements Handler {

    private Handler next;

    public BasicHandler() {}

    public BasicHandler(Handler next) {
        this.next = next;
    }

    public void setNext(Handler handler) {
        this.next = handler;
    }

    @Override
    public boolean canHandle(Context context) {
        return false;
    }

    @Override
    public void handle(Context context) {
        if (this.next != null) {
            this.next.handle(context);
        }
    }
}