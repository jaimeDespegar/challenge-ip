package com.example.app.models;

import javax.persistence.*;

@Entity
@Table(name = "ipitem")
public class IpItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ip;

    public IpItem() {}

    public IpItem(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}