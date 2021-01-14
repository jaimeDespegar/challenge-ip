package com.example.app.services;

import com.example.app.Application;
import com.example.app.exceptions.IpNotAllowedToConsultException;
import com.example.app.models.*;
import com.example.app.repositories.IpRepository;
import org.assertj.core.util.Maps;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
public class BlackListIpServiceIntegrationTest {

    @Autowired
    private IpRepository ipRepository;
    @Mock
    private SimpleIpService simpleIpService;
    private BlackListIpService blackListIpService;
    private static final String IP_BLOCKED = "1.103.155.194";

    @Before
    public void setUp() {
        this.setUpMocks();
        this.ipRepository.save(IpItem.builder().ip(IP_BLOCKED).build());
        this.blackListIpService = new BlackListIpService(this.simpleIpService, this.ipRepository);
    }

    @Test(expected = IpNotAllowedToConsultException.class)
    public void getInformationUser_whenIpIsBlocked_thenThrowException() {
        this.blackListIpService.getInformationUser(IP_BLOCKED);
    }

    @Test(expected = IpNotAllowedToConsultException.class)
    public void getInformationUser_whenAddIpToBlackList_thenThrowException() {
        this.blackListIpService.addIp("1.155.122.101");
        this.blackListIpService.getInformationUser("1.155.122.101");
    }

    @Test
    public void getInformationUser_whenIpIsUnlocked_thenOK() {
        this.ipRepository.removeByIp(IP_BLOCKED);
        IpInformation response = this.blackListIpService.getInformationUser(IP_BLOCKED);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getCountryName(), "Argentina");
        Assert.assertEquals(response.getCountryIsoCode(), "ARG");
        Assert.assertEquals(response.getCurrency(), "ARS");
        Assert.assertEquals(response.getQuotation().size(), 1);
    }

    private void setUpMocks() {
        IpInformation response = IpInformation.builder().countryName("Argentina")
                .countryIsoCode("ARG").currency("ARS")
                .quotation(Maps.newHashMap("ARS", BigDecimal.ONE))
                .build();
        MockitoAnnotations.initMocks(this);
        when(simpleIpService.getInformationUser(anyString())).thenReturn(response);
    }

}