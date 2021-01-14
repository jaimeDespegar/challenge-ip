package com.example.app.clients;

import com.example.app.clients.responses.CountryInfoResponse;
import com.example.app.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CountryClient extends TemplateClient {

    @Value("${country.info.client.url}")
    private String url;

    @Cacheable("countryInformation")
    public CountryInfoResponse getCountryInfo(String countryIsoCode) {
        try {
            return this.get(url, countryIsoCode, CountryInfoResponse.class);
        } catch (Exception e) {
            throw new ClientException("Error Client Country Info", e);
        }
    }

}