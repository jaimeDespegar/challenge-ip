package com.example.app.handlers;

import java.math.BigDecimal;
import java.util.Map;

public class Context {

    private String ip;
    private String country;
    private String currency;
    private String countryIsoCode;
    private Map<String, BigDecimal> currenciesToEuroValue;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCountryIsoCode() {
        return countryIsoCode;
    }

    public void setCountryIsoCode(String countryIsoCode) {
        this.countryIsoCode = countryIsoCode;
    }

    public Map<String, BigDecimal> getCurrenciesToEuroValue() {
        return currenciesToEuroValue;
    }

    public void setCurrenciesToEuroValue(Map<String, BigDecimal> currenciesToEuroValue) {
        this.currenciesToEuroValue = currenciesToEuroValue;
    }

    @Override
    public String toString() {
        return "Context{" +
                "ip='" + ip + '\'' +
                ", country='" + country + '\'' +
                ", currency='" + currency + '\'' +
                ", countryIsoCode='" + countryIsoCode + '\'' +
                ", currenciesToEuroValue=" + currenciesToEuroValue +
                '}';
    }

}