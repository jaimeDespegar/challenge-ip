package com.example.app.clients;

import com.example.app.clients.responses.IpInformationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class IpClient {

    @Value("${ip.client.url}")
    private String url;
    private RestTemplate restTemplate = new RestTemplate();

    public IpInformationResponse getIpInfo(String ip) {
        try {
            String urlWithIp = String.format(url, ip);
            return restTemplate.getForEntity(urlWithIp, IpInformationResponse.class).getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error Client Ip", e);
        }
    }

}