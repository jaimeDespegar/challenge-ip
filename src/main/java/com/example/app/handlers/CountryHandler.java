package com.example.app.handlers;

import com.example.app.clients.CountryInfoClient;
import com.example.app.models.IpInformation.IpInformationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.util.stream.Collectors;

@Component
public class CountryHandler extends BasicHandler<String> {

    private final CountryInfoClient client;
    private static final Integer LENGTH_ALLOW_COUNTRY_ISO_CODE = 3;

    @Autowired
    public CountryHandler(CountryInfoClient client) {
        this.client = client;
    }

    @Override
    public boolean canHandle(String isoCode) {
        return !StringUtils.isEmpty(isoCode) && isoCode.length() == LENGTH_ALLOW_COUNTRY_ISO_CODE;
    }

    @Override
    public IpInformationBuilder handle(IpInformationBuilder builder, String isoCode) {

        String codes = this.client.getCountryInfo(isoCode)
                                  .getCurrencies().stream()
                                  .map(i -> i.getCode())
                                  .collect(Collectors.joining(","));
        builder.currency(codes);
        return super.handle(builder, codes);
    }

}