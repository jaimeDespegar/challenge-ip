package com.example.app.services;

import com.example.app.models.IpInformation;

public interface IpService {

    IpInformation getInformationUser(String ip);

}