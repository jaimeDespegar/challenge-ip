package com.example.app.clients.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CountryInfoResponse {

    private List<Currency> currencies;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Currency {
        private String code;
        private String name;
        private String symbol;
    }

}