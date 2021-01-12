package com.example.app.clients;

import com.example.app.clients.responses.IpInformationResponse;
import com.example.app.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.cache.annotation.Cacheable;

@Component(value = "ipClient")
public class IpClient extends TemplateClient {

    @Value("${ip.client.url}")
    private String url;

    @Cacheable("ipInformation")
    public IpInformationResponse getIpInfo(String ip) {
        try {
            //LOGGER.info("GET Ip Service with ip {}", ip);
            return this.get(url, ip, IpInformationResponse.class);
        } catch (Exception e) {
            throw new ClientException("Error Client Ip", e);
        }
    }

}