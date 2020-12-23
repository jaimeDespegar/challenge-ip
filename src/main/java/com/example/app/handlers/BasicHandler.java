package com.example.app.handlers;

public abstract class BasicHandler implements Handler {

    private Handler next;

    public BasicHandler() {}

    public BasicHandler(Handler next) {
        this.next = next;
    }

    public void setNext(Handler handler) {
        this.next = handler;
    }

    @Override
    public void handle(Context context) {
        if (this.next != null) {
            this.next.handle(context);
        }
    }

}