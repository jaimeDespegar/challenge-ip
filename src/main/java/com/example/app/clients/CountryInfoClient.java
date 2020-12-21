package com.example.app.clients;

import com.example.app.clients.responses.CountryInfoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CountryInfoClient {

    @Value("${country.info.client.url}")
    private String url;
    private RestTemplate restTemplate = new RestTemplate();

    public CountryInfoResponse getCountryInfo(String countryIsoCode) {
        try {
            String urlWithParam = String.format(url, countryIsoCode);
            return restTemplate.getForEntity(urlWithParam, CountryInfoResponse.class).getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error Client Country Info", e);
        }
    }

}