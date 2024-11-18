package com.hemoalert.HemoAlert.response;

import java.util.UUID;

public class CreateAlertResponse {

    private String message;
    private UUID alertId;

    public CreateAlertResponse(String message, UUID alertId) {
        this.message = message;
        this.alertId = alertId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UUID getAlertId() {
        return alertId;
    }

    public void setAlertId(UUID alertId) {
        this.alertId = alertId;
    }
}