package com.example.springcashier.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class ApiResponse {
    private boolean success;
    private String message;
    private Order data;

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static ApiResponse fromJsonString(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            String message = jsonNode.get("message").asText();
            return new ApiResponse(false, message);
        } catch (Exception e) {
            return new ApiResponse(false, "Error parsing JSON response: " + e.getMessage());
        }
    }

    public void setData(Order data) {
        this.data = data;
    }
}
