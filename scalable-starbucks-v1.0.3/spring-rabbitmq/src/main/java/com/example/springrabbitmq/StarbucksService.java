package com.example.springrabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StarbucksService {

    @Autowired
    private StarbucksDrinkRepository drinksRepository;

    public String getDrinkStatus(Long drinkID) {
        Optional<StarbucksDrink> optionalDrink = drinksRepository.findById(drinkID);
        if (optionalDrink.isPresent()) {
            return optionalDrink.get().getStatus();
        } else {
            throw new ResourceNotFoundException("Drink", "ID", drinkID);
        }
    }
}
