package com.example.app.handlers;

import com.example.app.clients.CurrencyClient;
import org.mockito.Mock;
import org.testng.annotations.BeforeClass;

public class CurrencyHandlerTest {

    private CurrencyHandler handler;
    @Mock
    private CurrencyClient client;

    @BeforeClass
    public void setUp() {
        this.handler = new CurrencyHandler(this.client);
    }

}