package com.example.app.adapters;

import com.example.app.handlers.Context;
import com.example.app.models.IpInformation;
import org.springframework.stereotype.Component;

@Component
public class IpInformationAdapter {

    public IpInformation adapt(Context context) {
        return new IpInformation(context.getCountry(),
                                      context.getCountryIsoCode(),
                                      context.getCurrency(),
                                      context.getCurrenciesToEuroValue());
    }

}