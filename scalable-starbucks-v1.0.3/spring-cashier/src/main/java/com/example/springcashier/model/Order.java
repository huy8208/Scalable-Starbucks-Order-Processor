package com.example.springcashier.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Order {

    private String drink;
    private String milk;
    private String size;
    private double total;
    private String status;
    private String register;
    private double price;
    private double tax;
}

/*
 * 
 * https://priceqube.com/menu-prices/%E2%98%95-starbucks
 * 
 * var DRINK_OPTIONS = [ "Caffe Latte", "Caffe Americano", "Caffe Mocha",
 * "Espresso", "Cappuccino" ];
 * var MILK_OPTIONS = [ "Whole Milk", "2% Milk", "Nonfat Milk", "Almond Milk",
 * "Soy Milk" ];
 * var SIZE_OPTIONS = [ "Short", "Tall", "Grande", "Venti", "Your Own Cup" ];
 * 
 * Caffè Latte
 * =============
 * tall $2.95
 * grande $3.65
 * venti $3.95 (Your Own Cup)
 * 
 * Caffè Americano
 * ===============
 * tall $2.25
 * grande $2.65
 * venti $2.95 (Your Own Cup)
 * 
 * Caffè Mocha
 * =============
 * tall $3.45
 * grande $4.15
 * venti $4.45 (Your Own Cup)
 * 
 * Cappuccino
 * ==========
 * tall $2.95
 * grande $3.65
 * venti $3.95 (Your Own Cup)
 * 
 * Espresso
 * ========
 * short $1.75
 * tall $1.95
 * 
 */
