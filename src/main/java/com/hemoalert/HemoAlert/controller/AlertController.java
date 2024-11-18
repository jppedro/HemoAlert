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

    public void handleRequest(Socket clientSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

            String requestLine = reader.readLine();
            if (requestLine == null) return;

            String[] requestParts = requestLine.split(" ");
            String method = requestParts[0];
            String path = requestParts[1];

            if (method.equals("POST") && path.equals("/alerts")) {
                handleCreateAlert(reader, writer);
            } else if (method.equals("GET") && path.startsWith("/alerts/")) {
                handleGetAlert(writer, path);
            } else {
                sendResponse(writer, 404, "Not Found");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleCreateAlert(BufferedReader reader, BufferedWriter writer) throws IOException {
        StringBuilder bodyBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            bodyBuilder.append(line);
        }

        AlertDTO alertDTO = objectMapper.readValue(bodyBuilder.toString(), AlertDTO.class);
        UUID alertId = alertService.createAlert(alertDTO);

        String response = objectMapper.writeValueAsString(new CreateAlertResponse("Alert created successfully!", alertId));
        sendResponse(writer, 201, response);
    }

    private void handleGetAlert(BufferedWriter writer, String path) throws IOException {
        String[] pathParts = path.split("/");
        if (pathParts.length < 3) {
            sendResponse(writer, 400, "Invalid URL");
            return;
        }

        UUID alertId = UUID.fromString(pathParts[2]);
        try {
            AlertDTO alert = alertService.getAlertById(alertId);
            String response = objectMapper.writeValueAsString(alert);
            sendResponse(writer, 200, response);
        } catch (Exception e) {
            sendResponse(writer, 404, e.getMessage());
        }
    }

    private void sendResponse(BufferedWriter writer, int statusCode, String body) throws IOException {
        writer.write("HTTP/1.1 " + statusCode + " OK\r\n");
        writer.write("Content-Type: application/json\r\n");
        writer.write("Content-Length: " + body.length() + "\r\n");
        writer.write("\r\n");
        writer.write(body);
        writer.flush();
    }
}

