package com.example.app.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public abstract class TemplateClient {

    protected Logger LOGGER = LoggerFactory.getLogger(getClass());
    private RestTemplate restTemplate = new RestTemplate();

    public <T> T get(String url, String param, Class classResponse) {
        LOGGER.info("GET Service with param {}", param);
        String urlWithParam = String.format(url, param);
        return (T) restTemplate.getForEntity(urlWithParam, classResponse).getBody();
    }

}