package com.hemoalert.HemoAlert.model;

import java.io.Serializable;
import java.util.UUID;

public class Alert implements Serializable {

    private UUID alertUuid;
    private String centerName;
    private String city;
    private BloodType bloodType;
    private UUID centerUuid;

    public Alert() {}

    public Alert(final UUID alertUuid, final String centerName, final String city, final BloodType bloodType, final UUID centerUuid) {
        this.alertUuid = alertUuid;
        this.centerName = centerName;
        this.city = city;
        this.bloodType = bloodType;
        this.centerUuid = centerUuid;
    }

    /*public static Alert of(AlertDTO alertDTO) {
        return new Alert(
                alertDTO.getAlertUUID(),
                alertDTO.getCenterName(),
                alertDTO.getCity(),
                alertDTO.getBloodType(),
                alertDTO.getCenterUUID()
        );
    }*/

    public UUID getId() {
        return alertUuid;
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

    @Override
    public String toString() {
        return "Alert{" +
                "alertUuid=" + alertUuid +
                ", centerName='" + centerName + '\'' +
                ", city='" + city + '\'' +
                ", bloodType=" + bloodType +
                ", centerUuid=" + centerUuid +
                '}';
    }
}