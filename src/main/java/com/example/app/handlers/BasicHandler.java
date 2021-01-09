package com.example.app.handlers;

import com.example.app.exceptions.HandlerException;
import com.example.app.models.IpInformation.IpInformationBuilder;

public abstract class BasicHandler<P> implements Handler<P> {

    private Handler next;

    public BasicHandler() {}

    public BasicHandler(Handler next) {
        this.next = next;
    }

    @Override
    public void setNext(Handler handler) {
        this.next = handler;
    }

    @Override
    public IpInformationBuilder handle(IpInformationBuilder builder, P param) {
        if (this.next != null) {
            return this.next.doHandle(builder, param);
        }
        return builder;
    }

    @Override
    public IpInformationBuilder doHandle(IpInformationBuilder builder, P param) {
        if (this.canHandle(param)) {
            return this.handle(builder, param);
        }
        throw new HandlerException(getClass().getSimpleName() + " can't handle with parameter " + param);
    }

}