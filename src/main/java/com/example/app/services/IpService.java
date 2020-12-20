package com.example.app.services;

import com.example.app.exceptions.InvalidFormatIpException;
import com.example.app.models.CountryInformation;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class IpService implements com.example.app.services.Service {

    private static final String zeroTo255 = "([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])";
    private static final String FORMAT_IP = zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255;

    @Override
    public CountryInformation getInfoUser(String ip) {
        if (!StringUtils.isEmpty(ip) && ip.matches(FORMAT_IP)) {
            return new CountryInformation("Argentina", "AR", "ARS", "82.7");
        }
        throw new InvalidFormatIpException("The format ip is not valid");
    }

}