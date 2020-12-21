package com.example.app.clients;

import com.example.app.clients.responses.CurrencyResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CurrencyClient {

    @Value("${currency.client.url}")
    private String url;
    private RestTemplate restTemplate = new RestTemplate();

    public CurrencyResponse getCurrencyInfo(String currency) {
        try {
            String urlWithParam = String.format(url, currency);
            return restTemplate.getForEntity(urlWithParam, CurrencyResponse.class).getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error Client Currency ", e);
        }
    }

}