package com.hemoalert.HemoAlert.response;

import java.util.UUID;

public class CreateBloodCenterResponse {

    private UUID bloodCenterUuid;
    private String message;

    public CreateBloodCenterResponse(UUID bloodCenterUuid, String message) {
        this.bloodCenterUuid = bloodCenterUuid;
        this.message = message;
    }

    public UUID getBloodCenterUuid() {
        return bloodCenterUuid;
    }

    public void setBloodCenterUuid(UUID bloodCenterUuid) {
        this.bloodCenterUuid = bloodCenterUuid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}