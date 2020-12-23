package com.example.app.handlers;

import com.example.app.clients.CountryInfoClient;
import com.example.app.clients.responses.CountryInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.util.stream.Collectors;

@Component
public class CountryHandler extends BasicHandler {

    private final CountryInfoClient client;
    private static final Integer LENGTH_ALLOW_COUNTRY_ISO_CODE = 3;

    @Autowired
    public CountryHandler(CountryInfoClient client) {
        this.client = client;
    }

    @Override
    public boolean canHandle(Context context) {
        String isoCode = context.getCountryIsoCode();
        return !StringUtils.isEmpty(isoCode) && isoCode.length() == LENGTH_ALLOW_COUNTRY_ISO_CODE;
    }

    @Override
    public void handle(Context context) {
        CountryInfoResponse response = this.client.getCountryInfo(context.getCountryIsoCode());
        String codes = response.getCurrencies().stream()
                                               .map(i -> i.getCode())
                                               .collect(Collectors.joining(","));
        context.setCurrency(codes);
        super.handle(context);
    }

}