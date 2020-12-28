package com.example.app.models;

import java.math.BigDecimal;
import java.util.Map;

public class IpInformation {

    private String countryName;
    private String countryIsoCode;
    private String currency;
    private Map<String, BigDecimal> quotation;

    public IpInformation(String countryName, String countryIsoCode, String currency, Map<String, BigDecimal> quotation) {
        this.countryName = countryName;
        this.countryIsoCode = countryIsoCode;
        this.currency = currency;
        this.quotation = quotation;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryIsoCode() {
        return countryIsoCode;
    }

    public void setCountryIsoCode(String countryIsoCode) {
        this.countryIsoCode = countryIsoCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Map<String, BigDecimal> getQuotation() {
        return quotation;
    }

    public void setQuotation(Map<String, BigDecimal> quotation) {
        this.quotation = quotation;
    }

}