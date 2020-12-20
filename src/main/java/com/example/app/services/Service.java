package com.example.app.services;

import com.example.app.models.CountryInformation;

public interface Service {

    CountryInformation getInfoUser(String ip);

}