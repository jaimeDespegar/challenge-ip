package com.example.app.handlers;

import com.example.app.clients.CurrencyClient;
import com.example.app.clients.responses.CurrencyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CurrencyHandler extends BasicHandler {

    private final CurrencyClient client;

    @Autowired
    public CurrencyHandler(CurrencyClient client) {
        this.client = client;
    }

    @Override
    public boolean canHandle(Context context) {
        return !StringUtils.isEmpty(context.getCurrency());
    }

    @Override
    public void handle(Context context) {
        CurrencyResponse response = this.client.getCurrencyInfo(context.getCurrency());
        context.setCurrenciesToEuroValue(response.getRates());
        //super.handle(context);
    }

}