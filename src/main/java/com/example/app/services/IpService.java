package com.example.app.services;

import com.example.app.handlers.*;
import com.example.app.models.CountryInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IpService implements com.example.app.services.Service {

    private final IpHandler ipHandler;
    private final CountryHandler countryHandler;
    private final CurrencyHandler currencyHandler;

    @Autowired
    public IpService(IpHandler ipHandler, CountryHandler countryHandler, CurrencyHandler currencyHandler) {
        this.ipHandler = ipHandler;
        this.countryHandler = countryHandler;
        this.currencyHandler = currencyHandler;
    }

    @Override
    public CountryInformation getInfoUser(String ip) {
        Context context = new Context();
        context.setIp(ip);

        this.countryHandler.setNext(this.currencyHandler);
        this.ipHandler.setNext(this.countryHandler);

        this.ipHandler.handle(context);
        return new CountryInformation(context.getCountry(), context.getCountryIsoCode(), context.getCurrency(), context.getCurrenciesToEuroValue());
    }

}