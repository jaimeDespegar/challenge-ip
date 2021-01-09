package com.example.app.handlers;

import com.example.app.models.IpInformation.*;

public interface Handler<P> {

    boolean canHandle(P context);
    IpInformationBuilder handle(IpInformationBuilder builder, P param);
    IpInformationBuilder doHandle(IpInformationBuilder builder, P param);
    void setNext(Handler handler);

}