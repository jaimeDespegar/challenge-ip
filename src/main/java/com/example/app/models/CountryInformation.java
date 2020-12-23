package com.example.app.models;

import java.math.BigDecimal;
import java.util.Map;

public class CountryInformation {

    private String name;
    private String isoCode;
    private String currency;
    private Map<String, BigDecimal> quotation;

    public CountryInformation(String name, String isoCode, String currency, Map<String, BigDecimal> quotation) {
        this.name = name;
        this.isoCode = isoCode;
        this.currency = currency;
        this.quotation = quotation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
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

    @Override
    public String toString() {
        return "CountryInformation{" +
                "name='" + name + '\'' +
                ", isoCode='" + isoCode + '\'' +
                ", currency='" + currency + '\'' +
                ", quotation='" + quotation + '\'' +
                '}';
    }

}