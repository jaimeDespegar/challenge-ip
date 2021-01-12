package com.example.app.services;

import com.example.app.handlers.*;
import com.example.app.models.IpInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("simpleIpService")
public class SimpleIpService implements IpService {

    private final Handler ipHandler, countryHandler, currencyHandler;

    @Autowired
    public SimpleIpService(IpHandler ipHandler,
                           CountryHandler countryHandler,
                           CurrencyHandler currencyHandler) {
        this.ipHandler = ipHandler;
        this.countryHandler = countryHandler;
        this.currencyHandler = currencyHandler;

        this.ipHandler.setNext(this.countryHandler);
        this.countryHandler.setNext(this.currencyHandler);
    }

    @Override
    public IpInformation getInformationUser(String ip) {
        return this.ipHandler.doHandle(IpInformation.builder(), ip)
                             .build();
    }

}