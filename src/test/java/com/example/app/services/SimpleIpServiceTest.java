package com.example.app.services;

import com.example.app.exceptions.HandlerException;
import com.example.app.handlers.*;
import com.example.app.models.IpInformation;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Maps;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doCallRealMethod;

public class SimpleIpServiceTest {

    private IpService ipService;
    private List<String> invalidIps;

    @Mock
    private IpHandler ipHandler;
    @Mock
    private CountryHandler countryHandler;
    @Mock
    private CurrencyHandler currencyHandler;

    @BeforeClass
    public void setUp() {
        this.initMocks();
        this.ipService = new SimpleIpService(this.ipHandler, this.countryHandler, this.currencyHandler);
        this.invalidIps = Lists.newArrayList("",
                null,
                "12.146.162.205.85",
                ".127.146.176.185.",
                ".127.146.176.85",
                "127.146.176.185.",
                "127.69.jsq.185",
                "555.111.222.666",
                "193.1.106.1150",
                "Im.not.IP.address");
    }

    @Test
    public void getInformationUser_whenIpIsInvalidFormat_thenThrowException() {
        for (String ip : this.invalidIps) {
            try {
                this.ipService.getInformationUser(ip);
                Assert.fail("Ip " + ip + " should not be valid.");
            } catch (HandlerException he) {
                Assert.assertEquals(he.getMessage(), this.ipHandler.getClass().getSimpleName()+" can't handle with parameter " + ip);
            }
        }
    }

    @Test
    public void getInformationUser_whenIpHasValidFormat_thenReturnInfo() {
        IpInformation response = this.ipService.getInformationUser("127.0.0.1");
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getCountryName(), "Argentina");
        Assert.assertEquals(response.getCountryIsoCode(), "ARG");
        Assert.assertEquals(response.getCurrency(), "ARS");
        Assert.assertEquals(response.getQuotation().get("ARS"), BigDecimal.valueOf(82.7));
    }

    private void initMocks() {
        MockitoAnnotations.initMocks(this);
        doCallRealMethod().when(this.ipHandler).setNext(any(Handler.class));
        doCallRealMethod().when(this.ipHandler).canHandle(any());
        doCallRealMethod().when(this.ipHandler).doHandle(any(), any());
        doCallRealMethod().when(this.countryHandler).setNext(any(Handler.class));

        IpInformation.IpInformationBuilder builder = IpInformation.builder();
        Map<String, BigDecimal> values = Maps.newHashMap("ARS", BigDecimal.valueOf(82.7));
        when(this.ipHandler.handle(any(), anyString())).thenReturn(builder.countryIsoCode("ARG").countryName("Argentina"));
        when(this.countryHandler.handle(any(), anyString())).thenReturn(builder.currency("ARS"));
        when(this.currencyHandler.handle(any(), anyString())).thenReturn(builder.quotation(values));
    }

}