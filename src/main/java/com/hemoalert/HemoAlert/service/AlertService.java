package com.hemoalert.HemoAlert.service;

import com.hemoalert.HemoAlert.dto.AlertDTO;
import com.hemoalert.HemoAlert.exception.AlertNotFoundException;
import com.hemoalert.HemoAlert.model.Alert;
import com.hemoalert.HemoAlert.repository.AlertRepository;

import java.util.UUID;

public class AlertService {
    private final AlertRepository alertRepository;

    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public UUID createAlert(AlertDTO alertDTO) {
        Alert alert = new Alert(
                UUID.randomUUID(),
                alertDTO.getCenterName(),
                alertDTO.getCity(),
                alertDTO.getBloodType(),
                alertDTO.getCenterUuid()
        );
        return alertRepository.saveAlert(alert);
    }

    public AlertDTO getAlertById(UUID alertId) {
        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new AlertNotFoundException("Alert not found with id: " + alertId));

        return new AlertDTO(
                alert.getId(),
                alert.getCenterName(),
                alert.getCity(),
                alert.getBloodType(),
                alert.getCenterUuid()
        );
    }
}