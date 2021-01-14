package com.example.app.services;

import com.beust.jcommander.internal.Lists;
import com.example.app.clients.*;
import com.example.app.clients.responses.*;
import com.example.app.exceptions.ClientException;
import com.example.app.exceptions.HandlerException;
import com.example.app.handlers.*;
import com.example.app.models.IpInformation;
import org.assertj.core.util.Maps;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.client.RestClientException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
public class SimpleIpServiceIntegrationTest extends AbstractTestNGSpringContextTests {

    @Mock
    private IpClient ipClient;
    @Mock
    private CountryClient countryClient;
    @Mock
    private CurrencyClient currencyClient;
    @InjectMocks
    private IpHandler ipHandler;
    @InjectMocks
    private CountryHandler countryHandler;
    @InjectMocks
    private CurrencyHandler currencyHandler;
    private SimpleIpService simpleIpService;

    private static final String IP_VALID = "193.50.1.103";
    private static final String IP_NOT_VALID = ".193.50.1.103.";
    private static final String IP_WITHOUT_COUNTRY = "111.222.111.222";
    private static final String IP_WITHOUT_CURRENCY = "10.20.30.40";
    private static final String COUNTRY_VALID = "COL";
    private static final String CURRENCY = "COP";

    @BeforeClass
    public void setUp() {
        this.setUpMocks();
        this.simpleIpService = new SimpleIpService(this.ipHandler, this.countryHandler, this.currencyHandler);
    }

    @Test(expectedExceptions = ClientException.class)
    public void getInformationUser_whenIpClientFail_thenThrowClientException() {
        doCallRealMethod().when(this.ipClient).getIpInfo("1.101.153.103");
        when(this.ipClient.get(any(), eq("1.101.153.103"), any())).thenThrow(RestClientException.class);
        this.simpleIpService.getInformationUser("1.101.153.103");
    }

    @Test(expectedExceptions = ClientException.class)
    public void getInformationUser_whenCountryClientFail_thenThrowClientException() {
        doCallRealMethod().when(this.ipClient).getIpInfo("1.101.153.203");
        doCallRealMethod().when(this.countryClient).getCountryInfo("ARG");
        when(this.ipClient.get(any(), eq("1.101.153.203"), any())).thenReturn(IpInformationResponse.builder().countryCode3("ARG").build());
        when(this.countryClient.get(any(), eq("ARG"), any())).thenThrow(RestClientException.class);
        this.simpleIpService.getInformationUser("1.101.153.203");
    }

    @Test(expectedExceptions = ClientException.class)
    public void getInformationUser_whenCurrencyClientFail_thenThrowClientException() {
        CountryInfoResponse.Currency currency = CountryInfoResponse.Currency.builder().code("ARS").build();
        List<CountryInfoResponse.Currency> currencies = Lists.newArrayList(currency);

        doCallRealMethod().when(this.ipClient).getIpInfo("2.101.153.203");
        doCallRealMethod().when(this.countryClient).getCountryInfo("ARG");
        doCallRealMethod().when(this.currencyClient).getCurrencyInfo("ARS");

        when(this.ipClient.get(any(), eq("2.101.153.203"), any()))
            .thenReturn(IpInformationResponse.builder().countryName("Argentina").countryCode3("ARG").build());
        when(this.countryClient.get(any(), eq("ARG"), any()))
            .thenReturn(CountryInfoResponse.builder().currencies(currencies).build());
        when(this.currencyClient.get(any(), eq("ARS"), any()))
            .thenThrow(RestClientException.class);

        this.simpleIpService.getInformationUser("2.101.153.203");
    }

    @Test(expectedExceptions = HandlerException.class)
    public void getInformationUser_whenIpHandlerCanNotHandle_thenThrowException() {
        this.simpleIpService.getInformationUser(IP_NOT_VALID);
    }

    @Test(expectedExceptions = HandlerException.class)
    public void getInformationUser_whenCountryHandlerCanNotHandle_thenThrowException() {
        this.simpleIpService.getInformationUser(IP_WITHOUT_COUNTRY);
    }

    @Test(expectedExceptions = HandlerException.class)
    public void getInformationUser_whenCurrencyHandlerCanNotHandle_thenThrowException() {
        this.simpleIpService.getInformationUser(IP_WITHOUT_CURRENCY);
    }

    @Test
    public void getInformationUser_whenAllValuesAreOk_thenReturnResponseOk() {
        IpInformation response = this.simpleIpService.getInformationUser(IP_VALID);
        Assert.assertEquals(response.getCountryIsoCode(), "COL");
        Assert.assertEquals(response.getCountryName(), "Colombia");
        Assert.assertEquals(response.getCurrency(), "COP");
        Assert.assertNotNull(response.getQuotation());
        Assert.assertEquals(response.getQuotation().get("COP"), BigDecimal.valueOf(67.8));
    }

    private void setUpMocks() {
        MockitoAnnotations.initMocks(this);
        // Mock Response OK
        CountryInfoResponse.Currency currency = CountryInfoResponse.Currency.builder().code("COP").build();
        List<CountryInfoResponse.Currency> currencies = Lists.newArrayList(currency);

        IpInformationResponse response = IpInformationResponse.builder().countryCode3("COL").countryName("Colombia").build();
        CountryInfoResponse countryResponse = CountryInfoResponse.builder().currencies(currencies).build();
        CurrencyResponse currencyResponse = CurrencyResponse.builder().rates(Maps.newHashMap("COP", BigDecimal.valueOf(67.8))).build();

        when(this.ipClient.getIpInfo(IP_VALID)).thenReturn(response);
        when(this.countryClient.getCountryInfo(COUNTRY_VALID)).thenReturn(countryResponse);
        when(this.currencyClient.getCurrencyInfo(CURRENCY)).thenReturn(currencyResponse);

        // Mock Country Response Empty
        when(this.ipClient.getIpInfo(IP_WITHOUT_COUNTRY)).thenReturn(IpInformationResponse.builder().build());

        // Mock Currency Response Empty
        when(this.ipClient.getIpInfo(IP_WITHOUT_CURRENCY)).thenReturn(IpInformationResponse.builder().countryCode3("PER").build());
        when(this.countryClient.getCountryInfo("PER")).thenReturn(CountryInfoResponse.builder().currencies(Lists.newArrayList()).build());
    }

}