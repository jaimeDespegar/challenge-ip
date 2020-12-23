package com.example.app.services;

import com.example.app.exceptions.InvalidFormatIpException;
import com.example.app.handlers.*;
import com.example.app.models.CountryInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class IpService implements com.example.app.services.Service {

    private static final String zeroTo255 = "([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])";
    private static final String FORMAT_IP = zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255;

    private IpHandler ipHandler;
    private CountryHandler countryHandler;
    private CurrencyHandler currencyHandler;

    @Autowired
    public IpService(IpHandler ipHandler, CountryHandler countryHandler, CurrencyHandler currencyHandler) {
        this.ipHandler = ipHandler;
        this.countryHandler = countryHandler;
        this.currencyHandler = currencyHandler;
    }

    @Override
    public CountryInformation getInfoUser(String ip) {
/*        if (!StringUtils.isEmpty(ip) && ip.matches(FORMAT_IP)) {
            return new CountryInformation("Argentina", "AR", "ARS", "82.7");
        }
        throw new InvalidFormatIpException("The format ip is not valid");*/

        Context context = new Context();
        context.setIp(ip);

        this.countryHandler.setNext(this.currencyHandler);
        this.ipHandler.setNext(this.countryHandler);

        this.ipHandler.handle(context);
        return new CountryInformation(context.getCountry(), context.getCountryIsoCode(), context.getCurrency(), context.getCurrenciesToEuroValue().toString());
    }

}