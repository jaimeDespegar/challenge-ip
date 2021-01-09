package com.example.app.models;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
public class IpInformation {

    private String countryName;
    private String countryIsoCode;
    private String currency;
    private Map<String, BigDecimal> quotation;

}