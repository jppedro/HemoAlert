package com.hemoalert.HemoAlert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hemoalert.HemoAlert.dto.AlertDTO;
import com.hemoalert.HemoAlert.response.CreateAlertResponse;
import com.hemoalert.HemoAlert.service.AlertService;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

public class AlertController {
    private final AlertService alertService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    public void handleCreateAlert(BufferedWriter writer, AlertDTO alertDTO) throws IOException {
        UUID alertId = alertService.createAlert(alertDTO);

        String response = objectMapper.writeValueAsString(new CreateAlertResponse("Alert created successfully!", alertId));
        sendResponse(writer, 201, response);
    }

    public void handleGetAlert(BufferedWriter writer, String path) throws IOException {
        String[] pathParts = path.split("/");
        if (pathParts.length < 3) {
            sendResponse(writer, 400, "Invalid URL");
            return;
        }

        UUID alertId = UUID.fromString(pathParts[2]);
        try {
            AlertDTO alert = alertService.getAlertById(alertId);
            String response = objectMapper.writeValueAsString(alert);
            System.out.println(response);
            sendResponse(writer, 200, response);
        } catch (Exception e) {
            sendResponse(writer, 404, e.getMessage());
        }
    }

    private void sendResponse(BufferedWriter writer, int statusCode, String body) throws IOException {
        writer.write("HTTP/1.1 " + statusCode + " " + getStatusMessage(statusCode) + "\r\n");
        writer.write("Content-Type: application/json\r\n");
        writer.write("Content-Length: " + body.length() + "\r\n");
        writer.write("\r\n");
        writer.write(body);
        writer.flush();
    }

    private String getStatusMessage(int statusCode) {
        switch (statusCode) {
            case 200: return "OK";
            case 201: return "Created";
            case 400: return "Bad Request";
            case 404: return "Not Found";
            default: return "Internal Server Error";
        }
    }
}

