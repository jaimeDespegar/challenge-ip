package com.example.app.services;

import com.example.app.adapters.IpInformationAdapter;
import com.example.app.handlers.*;
import com.example.app.models.IpInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleIpIpService implements IpService {

    private final IpHandler ipHandler;
    private final CountryHandler countryHandler;
    private final CurrencyHandler currencyHandler;
    private final IpInformationAdapter adapter;

    @Autowired
    public SimpleIpIpService(IpHandler ipHandler,
                             CountryHandler countryHandler,
                             CurrencyHandler currencyHandler,
                             IpInformationAdapter adapter) {
        this.ipHandler = ipHandler;
        this.countryHandler = countryHandler;
        this.currencyHandler = currencyHandler;
        this.adapter = adapter;
    }

    @Override
    public IpInformation getInformationUser(String ip) {
        Context context = new Context();
        context.setIp(ip);

        this.ipHandler.setNext(this.countryHandler);
        this.countryHandler.setNext(this.currencyHandler);
        this.ipHandler.handle(context);

        return this.adapter.adapt(context);
    }

}