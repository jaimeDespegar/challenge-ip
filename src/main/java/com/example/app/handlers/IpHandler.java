package com.example.app.handlers;

import com.example.app.clients.IpClient;
import com.example.app.clients.responses.IpInformationResponse;
import com.example.app.helpers.IpHelper;
import com.example.app.models.IpInformation.IpInformationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IpHandler extends BasicHandler<String> {

    private final IpClient client;

    @Autowired
    public IpHandler(IpClient client) {
        this.client = client;
    }

    @Override
    public boolean canHandle(String ip) {
        return IpHelper.isValid(ip);
    }

    @Override
    public IpInformationBuilder handle(IpInformationBuilder builder, String ip) {
        IpInformationResponse response = this.client.getIpInfo(ip);
        builder.countryName(response.getCountryName());
        builder.countryIsoCode(response.getCountryCode3());
        return super.handle(builder, response.getCountryCode3());
    }

}