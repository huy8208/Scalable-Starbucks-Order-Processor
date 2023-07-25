package com.example.springrabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StarbucksDrinkController {

    @Autowired
    private StarbucksService service;

    @GetMapping("/drink/status/{drinkID}")
    public ResponseEntity<String> getDrinkStatus(@PathVariable Long drinkID) {
        try {
            String status = service.getDrinkStatus(drinkID);
            return new ResponseEntity<>("Status of drink ID " + drinkID + " is: " + status, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
