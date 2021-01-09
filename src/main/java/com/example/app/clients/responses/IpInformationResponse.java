package com.example.app.clients.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IpInformationResponse {

    private String countryCode;
    private String countryCode3;
    private String countryName;

}