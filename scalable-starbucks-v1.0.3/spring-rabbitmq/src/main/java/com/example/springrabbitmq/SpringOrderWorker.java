package com.example.springrabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Component
@RabbitListener(queues = "starbucks-queue")
public class SpringOrderWorker {

    private static final Logger log = LoggerFactory.getLogger(SpringOrderWorker.class);

    @Autowired
    private StarbucksDrinkRepository drinksRepository;

    @Autowired
    private StarbucksOrderRepository orderRepository;

    @Autowired
    private Queue starbucksQueue;

    @RabbitHandler
    public void processDrinkOrders(String drinkID) {
        log.info("Received  Drink ID #: " + drinkID);
        List<StarbucksDrink> list = drinksRepository.findByDrinkID(Long.parseLong(drinkID));
        if (!list.isEmpty()) {
            StarbucksDrink drink = list.get(0);
            StarbucksOrder order = drink.getOrder();

            drink.setStatus("PENDING");
            drinksRepository.save(drink);

            // If order is null, set drink status to "Drink Fulfilled | Order discarded" and
            // return
            if (order == null) {
                drink.setStatus("Drink Fulfilled | Order discarded");
                drinksRepository.save(drink);
                log.info("Processed drinkID: " + drinkID + " with discarded Order.");
                return;
            }

            // Sleeping to simulate busy work
            try {
                log.info("Start making " + order.getDrink() + " for drink id: " + drinkID);
                Thread.sleep(45000); // 45 seconds
            } catch (Exception e) {
            }

            order.setStatus("FULFILLED");
            drink.setStatus("READY");
            orderRepository.save(order);
            drinksRepository.save(drink);
            log.info("Processed drinkID: " + drinkID + " with orderID #: " + order.getId());
        } else {
            log.info("Drink Order # " + drinkID + " Not Found!");
        }
    }

}
