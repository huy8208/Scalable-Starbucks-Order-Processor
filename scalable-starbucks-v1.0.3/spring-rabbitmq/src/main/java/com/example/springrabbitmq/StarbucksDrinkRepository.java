package com.example.springrabbitmq;

/* https://docs.spring.io/spring-data/jpa/docs/2.4.6/reference/html/#repositories */

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StarbucksDrinkRepository extends JpaRepository<StarbucksDrink, Long> {
    List<StarbucksDrink> findByDrinkID(Long drinkID);

}
