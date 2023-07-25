package com.example.springstarbucksapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.springstarbucksapi.model.StarbucksOrder;
import com.example.springstarbucksapi.repository.StarbucksOrderRepository;

@SpringBootTest
class SpringStarbucksApiApplicationTests {
    @Autowired
    private StarbucksOrderRepository ordersRepository;

    @Test
    void contextLoads() {
    }

    // @Test
    // public void testSaveOrder() {
    // StarbucksOrder order = new StarbucksOrder();
    // order.setRegister("123");
    // order.setDrink("Caffe Latte");
    // order.setMilk("Whole Milk");
    // order.setSize("Grande");
    // order.setStatus("Ready for Payment.");
    // try {
    // StarbucksOrder savedOrder = ordersRepository.save(order);
    // System.out.println(savedOrder);
    // System.out.println("SAVINGGGGGGGGGGGGGGGGG SUCCESSFULLY");
    // } catch (Exception e) {
    // System.err.println("Error saving order: " + e.getMessage());
    // e.printStackTrace();
    // }

    // }

}
