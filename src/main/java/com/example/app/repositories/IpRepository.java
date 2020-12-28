package com.example.app.repositories;

import com.example.app.models.IpItem;
import org.springframework.data.repository.CrudRepository;

public interface IpRepository extends CrudRepository<IpItem, String> {
     
    IpItem findByIp(String ip);
    boolean removeByIp(String ip);
    boolean existsByIp(String ip);

}