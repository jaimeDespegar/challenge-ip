package com.example.app.clients;

import com.example.app.clients.responses.IpInformationResponse;
import com.example.app.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.cache.annotation.Cacheable;

@Component
public class IpClient extends TemplateClient {

    @Value("${ip.client.url}")
    private String url;

    @Cacheable("ipInformation")
    public IpInformationResponse getIpInfo(String ip) {
        try {
            return this.get(url, ip, IpInformationResponse.class);
        } catch (Exception e) {
            throw new ClientException("Error Client Ip", e);
        }
    }

}