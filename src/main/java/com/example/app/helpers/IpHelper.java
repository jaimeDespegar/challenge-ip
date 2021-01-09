package com.example.app.helpers;

import org.springframework.util.StringUtils;

public abstract class IpHelper {

    private static final String zeroTo255 = "([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])";
    private static final String FORMAT_IP = zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255;

    public static boolean isValid(String ip) {
        return !StringUtils.isEmpty(ip) && ip.matches(FORMAT_IP);
    }

}