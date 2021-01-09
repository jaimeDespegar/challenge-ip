package com.example.app.handlers;

import com.example.app.clients.CurrencyClient;
import com.example.app.clients.responses.CurrencyResponse;
import com.example.app.exceptions.HandlerException;
import com.example.app.models.IpInformation;
import org.assertj.core.util.Maps;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.math.BigDecimal;

public class CurrencyHandlerTest {

    private CurrencyHandler handler;
    @Mock
    private CurrencyClient client;
    private static final String CURRENCY_VALID = "ARS";
    private static final String CURRENCY_NOT_VALID = "";

    @BeforeClass
    public void setUp() {
        CurrencyResponse response = CurrencyResponse.builder()
                                                    .rates(Maps.newHashMap("ARS", BigDecimal.valueOf(48.5)))
                                                    .build();
        MockitoAnnotations.initMocks(this);
        when(this.client.getCurrencyInfo(anyString())).thenReturn(response);
        this.handler = new CurrencyHandler(this.client);
    }

    @Test
    public void canHandle_whenCurrencyIsOk_thenReturnTrue() {
        Assert.assertTrue(this.handler.canHandle(CURRENCY_VALID));
    }

    @Test
    public void canHandle_whenCurrencyIsBlank_thenReturnFalse() {
        Assert.assertFalse(this.handler.canHandle(""));
        Assert.assertFalse(this.handler.canHandle(null));
    }

    @Test
    public void handle_whenHasNotNextHandler_thenNotSetValuesFromCurrency() {
        IpInformation response = this.handler.handle(IpInformation.builder(), CURRENCY_VALID).build();
        Assert.assertNotNull(response.getQuotation());
        Assert.assertEquals(response.getQuotation().size(), 1);
    }

    @Test(expectedExceptions = HandlerException.class)
    public void doHandle_whenCannotHandle_thenReturnException() {
        this.handler.doHandle(IpInformation.builder(), CURRENCY_NOT_VALID);
    }

    @Test
    public void doHandle_whenIsNextOk_thenOk() {
        IpInformation.IpInformationBuilder builder = this.handler.doHandle(IpInformation.builder(), CURRENCY_VALID);
        IpInformation response = builder.build();
        Assert.assertNotNull(response.getQuotation());
        Assert.assertEquals(response.getQuotation().size(), 1);
    }

}