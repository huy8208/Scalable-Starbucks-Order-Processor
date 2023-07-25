package com.example.springcashier.controller;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.springcashier.command.Command;
import com.example.springcashier.model.ApiResponse;
import com.example.springcashier.model.Order;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/starbucks")
public class StarbucksController {

    @Value("${starbucks.client.apikey}")
    String API_KEY;
    @Value("${starbucks.client.apihost}")
    String API_HOST;

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", API_KEY);
        return headers;
    }

    private double calculateTotal(String drink, String size) {
        // Set price based on drink and size
        double price = 0.0;
        switch (drink) {
            case "Caffe Latte":
                price = (size.equals("Tall") ? 2.95 : size.equals("Grande") ? 3.65 : 3.95);
                break;
            case "Caffe Americano":
                price = (size.equals("Tall") ? 2.25 : size.equals("Grande") ? 2.65 : 2.95);
                break;
            case "Caffe Mocha":
                price = (size.equals("Tall") ? 3.45 : size.equals("Grande") ? 4.15 : 4.45);
                break;
            case "Espresso":
                price = (size.equals("Short") ? 1.75 : 1.95);
                break;
            case "Cappuccino":
                price = (size.equals("Tall") ? 2.95 : size.equals("Grande") ? 3.65 : 3.95);
                break;
        }

        double tax = 0.0725;
        double total = price + (price * tax);
        double scale = Math.pow(10, 2);
        double rounded = Math.round(total * scale) / scale;

        return rounded;
    }

    @GetMapping
    public String getAction(@ModelAttribute("command") Command command,
            Model model, HttpSession session) {

        String message = "";

        command.setRegister("6498234");
        message = "Welcome to starbucks" + "\n\n" +
                "Register: " + command.getRegister() + "\n" +
                "Status:   " + "Ready for New Order" + "\n";

        String server_ip = "";
        String host_name = "";
        try {
            InetAddress ip = InetAddress.getLocalHost();
            server_ip = ip.getHostAddress();
            host_name = ip.getHostName();
        } catch (Exception e) {
        }

        model.addAttribute("message", message);
        model.addAttribute("server", host_name + "/" + server_ip);
        model.addAttribute("command", new Order());
        return "starbucks";

    }

    @ModelAttribute("milks")
    public List<String> getMilks() {
        List<String> milks = Arrays.asList("Whole Milk", "2% Milk", "Nonfat Milk", "Almond Milk", "Soy Milk");
        return milks;
    }

    @ModelAttribute("sizes")
    public List<String> getSizes() {
        List<String> sizes = Arrays.asList("Tall", "Grande", "Venti", "Your Own Cup");
        return sizes;
    }

    @ModelAttribute("drinks")
    public List<String> getDrinks() {
        List<String> drinks = Arrays.asList("Caffe Latte", "Caffe Americano", "Caffe Mocha", "Espresso", "Cappuccino");
        return drinks;
    }

    private Order generateRandomOrder() {
        String[] DRINK_OPTIONS = { "Caffe Latte", "Caffe Americano", "Caffe Mocha", "Espresso", "Cappuccino" };
        String[] MILK_OPTIONS = { "Whole Milk", "2% Milk", "Nonfat Milk", "Almond Milk", "Soy Milk" };
        String[] SIZE_OPTIONS = { "Short", "Tall", "Grande", "Venti", "Your Own Cup" };

        Random random = new Random();

        String randomDrink = DRINK_OPTIONS[random.nextInt(DRINK_OPTIONS.length)];
        String randomMilk = MILK_OPTIONS[random.nextInt(MILK_OPTIONS.length)];
        String randomSize = SIZE_OPTIONS[random.nextInt(SIZE_OPTIONS.length)];

        Order randomOrder = new Order();
        randomOrder.setDrink(randomDrink);
        randomOrder.setMilk(randomMilk);
        randomOrder.setSize(randomSize);
        randomOrder.setStatus("Ready for Payment");

        double rounded = calculateTotal(randomDrink, randomSize);

        randomOrder.setTotal(rounded);

        return randomOrder;
    }

    private ApiResponse createNewOrder(String regid, Order newOrder) {
        String baseUrl = "http://" + API_HOST;
        String url = baseUrl + "/order/register/" + regid;
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = createHeaders();
        HttpEntity<Order> requestEntity = new HttpEntity<>(newOrder, headers);

        try {
            restTemplate.postForObject(url, requestEntity, Order.class);
            return new ApiResponse(true, "Order created successfully!");
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return ApiResponse.fromJsonString(e.getResponseBodyAsString());
            }
            return new ApiResponse(false, "Error: " + e.getMessage());
        }
    }

    public ApiResponse clearActiveOrder(String regid) {
        String baseUrl = "http://" + API_HOST;
        String url = baseUrl + "/order/register/" + regid;
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = createHeaders();
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        try {
            restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class);
            return new ApiResponse(true, "Active order cleared successfully!");
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return ApiResponse.fromJsonString(e.getResponseBodyAsString());
            }
            return new ApiResponse(false, "Error: " + e.getMessage());
        }
    }

    public ApiResponse getActiveOrder(String regid) {
        String baseUrl = "http://" + API_HOST;
        String url = baseUrl + "/order/register/" + regid;
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = createHeaders();
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Order> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
                    Order.class);
            Order order = responseEntity.getBody();
            ApiResponse response = new ApiResponse(true, "Active order retrieved successfully!");
            response.setData(order);
            return response;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return ApiResponse.fromJsonString(e.getResponseBodyAsString());
            }
            return new ApiResponse(false, "Error: " + e.getMessage());
        }
    }

    @PostMapping
    public String postAction(@Valid @ModelAttribute("command") Command command,
            @ModelAttribute("order") Order customOrder,
            @RequestParam(value = "action", required = true) String action,
            Errors errors, Model model, HttpServletRequest request) {

        String message = "";

        log.info("Action: " + action);
        command.setRegister(command.getStores());
        log.info("Command: " + command);

        /* Process Post Action */
        if (action.equals("Get Order")) {
            ApiResponse result = getActiveOrder(command.getRegister());
            if (result.isSuccess()) {
                Order order = result.getData();
                message = "Starbucks Reserved Order" + "\n\n" +
                        "Drink: " + order.getDrink() + "\n" +
                        "Milk:  " + order.getMilk() + "\n" +
                        "Size:  " + order.getSize() + "\n" +
                        "Total: " + order.getTotal() + "\n" +
                        "\n" +
                        "Register: " + order.getRegister() + "\n" +
                        "Status:   " + order.getStatus() + "\n";
                System.out.println(result.getData());
            } else {
                String errorMessage = result.getMessage();
                if ("Order Not Found!".equals(errorMessage)) {
                    message = "There is no active order for this register." + command.getRegister();
                } else {
                    message = "Error: " + errorMessage;
                }
            }

        } else if (action.equals("Clear Order")) {
            String tempRegID = command.getRegister();
            ApiResponse response = clearActiveOrder(command.getRegister());
            if (response.isSuccess()) {
                message = "Starbucks Reserved Order" + "\n\n" + "Active order has been cleared. \n " +
                        "Register: " + command.getRegister() + "\n" +
                        "Status:   " + "Ready for New Order" + "\n";
            } else {
                String errorMessage = response.getMessage();
                if ("Order Not Found!".equals(errorMessage)) {
                    message = "There is no active order for this register." + command.getRegister();
                } else {
                    message = "Error: " + errorMessage;
                }
                message += "\n + Unable to clear order for" + command.getRegister();

            }

            log.info("Order has been cleared for register: ", tempRegID);
        } else if (action.equals("Place Order")) {

            ApiResponse result = createNewOrder(command.getRegister(), customOrder);

            log.info(null, action, request, message);
            customOrder.setStatus("Ready for Payment.");
            customOrder.setRegister(command.getRegister());

            double rounded = calculateTotal(customOrder.getDrink(), customOrder.getSize());
            customOrder.setTotal(rounded);

            if (result.isSuccess()) {
                log.info("The order was created successfully.");
                message = "Starbucks Reserved Order" + "\n\n" +
                        "Drink: " + customOrder.getDrink() + "\n" +
                        "Milk:  " + customOrder.getMilk() + "\n" +
                        "Size:  " + customOrder.getSize() + "\n" +
                        "Total: " + customOrder.getTotal() + "\n" +
                        "\n" +
                        "Register: " + customOrder.getRegister() + "\n" +
                        "Status: " + customOrder.getStatus() + "\n";
            } else {
                if ("Active Order Exists!".equals(result.getMessage())) {
                    message = "Error: Active order already exists for this register!";
                }
                log.error(result.getMessage());
            }
        }
        command.setMessage(message);

        String server_ip = "";
        String host_name = "";
        try {
            InetAddress ip = InetAddress.getLocalHost();
            server_ip = ip.getHostAddress();
            host_name = ip.getHostName();
        } catch (Exception e) {
        }

        model.addAttribute("message", message);
        model.addAttribute("server", host_name + "/" + server_ip);

        return "starbucks";

    }

}
