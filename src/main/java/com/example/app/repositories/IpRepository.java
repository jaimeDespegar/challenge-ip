package com.example.app.repositories;

import com.example.app.models.IpItem;
import org.springframework.data.repository.CrudRepository;

public interface IpRepository extends CrudRepository<IpItem, String> {
     
    IpItem findByIp(String ip);
    boolean removeByIp(String ip);
    boolean existsByIp(String ip);
    // @Query("SELECT e FROM Expense e WHERE e.amount >= :amount")
    // public List<Expense> listItemsWithPriceOver(@Param("amount") float amount);
}