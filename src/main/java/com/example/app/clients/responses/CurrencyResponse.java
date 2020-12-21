package com.example.app.clients.responses;

import java.math.BigDecimal;
import java.util.Map;

public class CurrencyResponse {

    private String base;
    private Map<String, BigDecimal> rates;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Map<String, BigDecimal> getRates() {
        return rates;
    }

    public void setRates(Map<String, BigDecimal> rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "CurrencyResponse{" +
                "base='" + base + '\'' +
                ", rates=" + rates +
                '}';
    }

}