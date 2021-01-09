package com.example.app.handlers;

import com.example.app.clients.CurrencyClient;
import com.example.app.clients.responses.CurrencyResponse;
import com.example.app.models.IpInformation.IpInformationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CurrencyHandler extends BasicHandler<String> {

    private final CurrencyClient client;

    @Autowired
    public CurrencyHandler(CurrencyClient client) {
        this.client = client;
    }

    @Override
    public boolean canHandle(String currencies) {
        return !StringUtils.isEmpty(currencies);
    }

    @Override
    public IpInformationBuilder handle(IpInformationBuilder builder, String currencies) {
        CurrencyResponse response = this.client.getCurrencyInfo(currencies);
        builder.quotation(response.getRates());
        return builder;
    }

}