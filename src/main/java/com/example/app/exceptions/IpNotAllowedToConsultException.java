package com.example.app.exceptions;

public class IpNotAllowedToConsultException extends RuntimeException {

    public IpNotAllowedToConsultException(String message) {
        super(message);
    }

}