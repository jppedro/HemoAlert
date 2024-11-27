package com.hemoalert.HemoAlert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hemoalert.HemoAlert.dto.BloodCenterDTO;
import com.hemoalert.HemoAlert.response.CreateBloodCenterResponse;
import com.hemoalert.HemoAlert.service.BloodCenterService;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

public class BloodCenterController {
    private final BloodCenterService bloodCenterService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public BloodCenterController(BloodCenterService bloodCenterService) {
        this.bloodCenterService = bloodCenterService;
    }

    public void handleCreateBloodCenter(BufferedWriter writer, BloodCenterDTO bloodCenterDTO) throws IOException {
        UUID bloodCenterId = bloodCenterService.createBloodCenter(bloodCenterDTO);

        String response = objectMapper.writeValueAsString(new CreateBloodCenterResponse(bloodCenterId, "Blood Center created successfully!"));
        sendResponse(writer, 201, response);
    }

    public void handleGetBloodCenter(BufferedWriter writer, String path) throws IOException {
        String[] pathParts = path.split("/");
        if (pathParts.length < 3) {
            sendResponse(writer, 400, "Invalid URL");
            return;
        }

        UUID bloodCenterId = UUID.fromString(pathParts[2]);
        try {
            BloodCenterDTO bloodCenter = bloodCenterService.getBloodCenterById(bloodCenterId);
            String response = objectMapper.writeValueAsString(bloodCenter);
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

