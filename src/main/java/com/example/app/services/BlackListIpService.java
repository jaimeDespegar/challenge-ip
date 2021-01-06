package com.example.app.services;

import com.example.app.repositories.IpRepository;
import com.example.app.exceptions.IpNotAllowedToConsultException;
import com.example.app.models.IpInformation;
import com.example.app.models.IpItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BlackListIpService implements IpService {

    private final IpService delegate;
    private final IpRepository repository;
    private static final String MESSAGE_IP_IN_BLACKLIST = "Ip %s not allowed to consult information";

    public BlackListIpService(SimpleIpIpService ipService, IpRepository repository) {
        this.delegate = ipService;
        this.repository = repository;
    }

    @Override
    public IpInformation getInformationUser(String ip) {
        if(this.isInBlackList(ip)) {
            throw new IpNotAllowedToConsultException(String.format(MESSAGE_IP_IN_BLACKLIST, ip));
        }
        return this.delegate.getInformationUser(ip);
    }

    public boolean addIp(String ip) {
        this.repository.save(new IpItem(ip));
        return true;
    }

    public boolean removeIp(String ip) {
        this.repository.removeByIp(ip);
        return true;
    }

    private boolean isInBlackList(String ip) {
        return this.repository.existsByIp(ip);
    }

}