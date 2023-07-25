package com.example.springrabbitmq;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StarbucksOrderRepository extends JpaRepository<StarbucksOrder, Long> {
    @Query(value = "SELECT * FROM starbucks_order WHERE register = ?1 ORDER BY id ASC", nativeQuery = true)
    List<StarbucksOrder> findByRegister(String register);

    @Modifying
    @Transactional
    @Query("DELETE FROM StarbucksOrder o WHERE o.register = ?1")
    void deleteByRegister(String register);

}
