package com.example.app.models;

public class CountryInformation {

    private String name;
    private String isoCode;
    private String currency;
    private String quotation;

    public CountryInformation(String name, String isoCode, String currency, String quotation) {
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

    public String getQuotation() {
        return quotation;
    }

    public void setQuotation(String quotation) {
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