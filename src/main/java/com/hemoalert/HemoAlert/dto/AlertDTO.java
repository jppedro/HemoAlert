package com.hemoalert.HemoAlert.dto;

import com.hemoalert.HemoAlert.model.BloodType;
import java.util.UUID;

public class AlertDTO {

    private UUID alertUuid;
    private String centerName;
    private String city;
    private BloodType bloodType;
    private UUID centerUuid;

    public AlertDTO() {}

    public AlertDTO(UUID alertUuid, String centerName, String city, BloodType bloodType, UUID centerUuid) {
        this.alertUuid = alertUuid;
        this.centerName = centerName;
        this.city = city;
        this.bloodType = bloodType;
        this.centerUuid = centerUuid;
    }

    public UUID getAlertUuid() {
        return alertUuid;
    }

    public void setAlertUuid(UUID alertUuid) {
        this.alertUuid = alertUuid;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public UUID getCenterUuid() {
        return centerUuid;
    }

    public void setCenterUuid(UUID centerUuid) {
        this.centerUuid = centerUuid;
    }
}
