package com.example.app.clients;

import com.example.app.clients.responses.CurrencyResponse;
import com.example.app.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CurrencyClient extends TemplateClient {

    @Value("${currency.client.url}")
    private String url;

    @Cacheable("currencyInformation")
    public CurrencyResponse getCurrencyInfo(String currency) {
        try {
            return this.get(url, currency, CurrencyResponse.class);
        } catch (Exception e) {
            throw new ClientException("Error Client Currency ", e);
        }
    }

}