
package com.example.project.users.dto;

public class GeneralResponse {
    private String message;

    public GeneralResponse(String message) {
        this.message = message;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
