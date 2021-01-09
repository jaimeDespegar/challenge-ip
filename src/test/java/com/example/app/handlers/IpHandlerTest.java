package com.example.app.handlers;

import com.example.app.clients.IpClient;
import com.example.app.clients.responses.IpInformationResponse;
import com.example.app.exceptions.HandlerException;
import com.example.app.models.IpInformation;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IpHandlerTest {

    private IpHandler handler, handlerWithNext;
    @Mock
    private CountryHandler countryHandler;
    @Mock
    private IpClient client;
    private static final String IP_VALID = "103.58.193.102";
    private static final String IP_NOT_VALID = "this.is.not.ip";

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(this.client.getIpInfo(IP_VALID)).thenReturn(IpInformationResponse.builder()
                                                             .countryCode3("ARG").countryName("Argentina")
                                                             .build());

        when(this.countryHandler.canHandle(anyString())).thenReturn(true);
        when(this.countryHandler.handle(any(), anyString()))
                .thenReturn(IpInformation.builder().countryName("Argentina").countryIsoCode("ARG").currency("ARS"));

        doCallRealMethod().when(this.countryHandler).doHandle(any(), anyString());

        this.handler = new IpHandler(this.client);
        this.handlerWithNext = new IpHandler(this.client);
        this.handlerWithNext.setNext(this.countryHandler);
    }

    @Test
    public void canHandle_whenIpIsOk_thenReturnTrue() {
        Assert.assertTrue(this.handler.canHandle(IP_VALID));
    }

    @Test
    public void canHandle_whenIpIsOk_thenReturnFalse() {
        Assert.assertFalse(this.handler.canHandle(IP_NOT_VALID));
    }

    @Test
    public void handle_whenHasNotNextHandler_thenNotSetValuesFromCurrency() {
        IpInformation response = this.handler.handle(IpInformation.builder(), IP_VALID).build();
        Assert.assertEquals(response.getCountryName(), "Argentina");
        Assert.assertEquals(response.getCountryIsoCode(), "ARG");
        Assert.assertNull(response.getCurrency());
        Assert.assertNull(response.getQuotation());
    }

    @Test
    public void handle_whenHasCountryHandlerNext_thenSetCurrencyValue() {
        IpInformation response = this.handlerWithNext.handle(IpInformation.builder(), IP_VALID).build();
        Assert.assertEquals(response.getCountryName(), "Argentina");
        Assert.assertEquals(response.getCountryIsoCode(), "ARG");
        Assert.assertEquals(response.getCurrency(), "ARS");
    }

    @Test(expectedExceptions = HandlerException.class)
    public void doHandle_whenIsNotNextOk_thenOk() {
        this.handler.doHandle(IpInformation.builder(), IP_NOT_VALID);
    }

    @Test
    public void doHandle_whenIsNextOk_thenOk() {
        IpInformation.IpInformationBuilder builder = this.handler.doHandle(IpInformation.builder(), IP_VALID);
        IpInformation response = builder.build();
        Assert.assertEquals(response.getCountryName(), "Argentina");
        Assert.assertEquals(response.getCountryIsoCode(), "ARG");
    }

}