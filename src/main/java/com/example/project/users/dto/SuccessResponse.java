
package com.example.project.users.dto;

public class SuccessResponse {
    private String message;

    public SuccessResponse(String message) {
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
