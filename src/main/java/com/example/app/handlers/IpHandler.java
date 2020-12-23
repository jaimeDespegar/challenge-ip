package com.example.app.handlers;

import com.example.app.clients.IpClient;
import com.example.app.clients.responses.IpInformationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class IpHandler extends BasicHandler {

    private final IpClient client;
    private static final String zeroTo255 = "([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])";
    private static final String FORMAT_IP = zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255;

    @Autowired
    public IpHandler(IpClient client) {
        this.client = client;
    }

    @Override
    public boolean canHandle(Context context) {
        return !StringUtils.isEmpty(context.getIp()) && context.getIp().matches(FORMAT_IP);
    }

    @Override
    public void handle(Context context) {
        IpInformationResponse response = this.client.getIpInfo(context.getIp());
        context.setCountryIsoCode(response.getCountryCode3());
        context.setCountry(response.getCountryName());
        super.handle(context);
    }

}