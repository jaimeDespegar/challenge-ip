package com.example.app.services;

import com.example.app.exceptions.InvalidFormatIpException;
import com.example.app.models.IpInformation;
import org.assertj.core.util.Lists;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;

public class SimpleIpIpServiceTest {

    private IpService ipService;
    private List<String> invalidIps;

    @BeforeClass
    public void setUp() {
        this.ipService = null;//new IpService();
        this.invalidIps = Lists.newArrayList("", null,
                "12.146.162.205.85",
                ".127.146.176.185.",
                ".127.146.176.85",
                "127.146.176.185.",
                "127.69.jsq.185",
                "555.111.222.666",
                "193.1.106.50",
                "Im.not.IP.address");
    }

    @Test
    public void getInfoUser_whenIpIsInvalidFormat_thenThrowException() {
        for (String ip : this.invalidIps) {
            try {
                this.ipService.getInformationUser(ip);
                Assert.fail("Ip should not be valid");
            } catch (InvalidFormatIpException ife) {
                Assert.assertEquals(ife.getMessage(), "The format ip is not valid");
            }
        }
    }

    @Test
    public void getInfoUser_whenIpHasValidFormat_thenReturnInfo() {
        IpInformation response = this.ipService.getInformationUser("127.0.0.1");
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getCountryName(), "Argentina");
        Assert.assertEquals(response.getCountryIsoCode(), "AR");
        Assert.assertEquals(response.getCurrency(), "ARS");
        Assert.assertEquals(response.getQuotation(), "82.7");
    }

}