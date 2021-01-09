package com.example.app.services;

import com.example.app.exceptions.IpNotAllowedToConsultException;
import com.example.app.models.IpInformation;
import com.example.app.repositories.IpRepository;
import org.assertj.core.util.Maps;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.math.BigDecimal;

public class BlackListIpServiceTest {

    private BlackListIpService instance;

    @Mock
    private SimpleIpIpService ipService;
    @Mock
    private IpRepository repository;

    @BeforeClass
    public void setUp() {
        IpInformation mockResponse = IpInformation.builder().countryName("Chile")
                                                            .countryIsoCode("CHL")
                                                            .currency("CLP")
                                                            .quotation(Maps.newHashMap("CLP", BigDecimal.ONE))
                                                            .build();

        MockitoAnnotations.initMocks(this);
        Mockito.when(this.ipService.getInformationUser("123.10.20.30")).thenReturn(mockResponse);
        Mockito.when(this.repository.existsByIp("103.20.40.10")).thenReturn(true);

        this.instance = new BlackListIpService(this.ipService, this.repository);
    }

    @Test(expectedExceptions = IpNotAllowedToConsultException.class)
    public void getInformationUser_whenIpIsInBlackList_thenReturnException() {
        this.instance.getInformationUser("103.20.40.10");
    }

    @Test
    public void getInformationUser_whenIpIsNotInBlackList_thenReturnResponse() {
        IpInformation response = this.instance.getInformationUser("123.10.20.30");
        Assert.assertEquals(response.getCountryName(), "Chile");
        Assert.assertEquals(response.getCountryIsoCode(), "CHL");
        Assert.assertEquals(response.getCurrency(), "CLP");
        Assert.assertEquals(response.getQuotation().size(), 1);
    }

}