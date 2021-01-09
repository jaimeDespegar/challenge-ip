package com.example.app.handlers;

import com.example.app.clients.CountryInfoClient;
import com.example.app.clients.responses.CountryInfoResponse;
import com.example.app.exceptions.HandlerException;
import com.example.app.models.IpInformation;
import com.google.common.collect.Lists;
import org.assertj.core.util.Maps;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class CountryHandlerTest {

    private CountryHandler handler, handlerWithNext;
    @Mock
    private CountryInfoClient client;
    @Mock
    private CurrencyHandler currencyHandler;
    private static final String COUNTRY_VALID = "ARG";
    private static final String COUNTRY_NOT_VALID = "AR";

    @BeforeClass
    public void setUp() {
        List<CountryInfoResponse.Currency> currencies = Lists.newArrayList(CountryInfoResponse.Currency.builder().code("ARS").build());
        CountryInfoResponse response = CountryInfoResponse.builder().currencies(currencies).build();
        Map<String, BigDecimal> values = Maps.newHashMap("ARS", BigDecimal.valueOf(50.5));

        MockitoAnnotations.initMocks(this);
        when(this.client.getCountryInfo("ARG")).thenReturn(response);
        when(this.currencyHandler.canHandle(anyString())).thenReturn(true);
        when(this.currencyHandler.handle(any(), anyString())).thenReturn(IpInformation.builder().currency("ARS").quotation(values));
        doCallRealMethod().when(this.currencyHandler).doHandle(any(), anyString());

        this.handler = new CountryHandler(this.client);
        this.handlerWithNext = new CountryHandler(this.client);
        this.handlerWithNext.setNext(this.currencyHandler);
    }

    @Test
    public void canHandle_whenCountryIsOk_thenReturnTrue() {
        Assert.assertTrue(this.handler.canHandle(COUNTRY_VALID));
    }

    @Test
    public void canHandle_whenCountryIsOk_thenReturnFalse() {
        Assert.assertFalse(this.handler.canHandle(COUNTRY_NOT_VALID));
    }

    @Test
    public void canHandle_whenCountryIsBlank_thenReturnFalse() {
        Assert.assertFalse(this.handler.canHandle(""));
        Assert.assertFalse(this.handler.canHandle(null));
    }

    @Test
    public void handle_whenHasNotNextHandler_thenNotSetValuesFromCurrency() {
        IpInformation response = this.handler.handle(IpInformation.builder(), COUNTRY_VALID).build();
        Assert.assertEquals(response.getCurrency(), "ARS");
        Assert.assertNull(response.getQuotation());
    }

    @Test
    public void handle_whenHasCurrencyHandlerNext_thenSetCurrencyValue() {
        IpInformation response = this.handlerWithNext.handle(IpInformation.builder(), COUNTRY_VALID).build();
        Assert.assertEquals(response.getCurrency(), "ARS");
        Assert.assertEquals(response.getQuotation().size(), 1);
    }

    @Test(expectedExceptions = HandlerException.class)
    public void doHandle_whenCannotHandle_thenReturnException() {
        this.handler.doHandle(IpInformation.builder(), COUNTRY_NOT_VALID);
    }

    @Test
    public void doHandle_whenIsNextOk_thenOk() {
        IpInformation.IpInformationBuilder builder = this.handler.doHandle(IpInformation.builder(), COUNTRY_VALID);
        IpInformation response = builder.build();
        Assert.assertEquals(response.getCurrency(), "ARS");
    }

}