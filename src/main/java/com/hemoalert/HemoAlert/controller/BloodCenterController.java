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

    public void handleRequest(Socket clientSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

            String requestLine = reader.readLine();
            if (requestLine == null) return;

            String[] requestParts = requestLine.split(" ");
            String method = requestParts[0];
            String path = requestParts[1];

            if (method.equals("POST") && path.equals("/blood-centers")) {
                handleCreateBloodCenter(reader, writer);
            } else if (method.equals("GET") && path.startsWith("/blood-centers/")) {
                handleGetBloodCenter(writer, path);
            } else {
                sendResponse(writer, 404, "Not Found");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleCreateBloodCenter(BufferedReader reader, BufferedWriter writer) throws IOException {
        StringBuilder bodyBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            bodyBuilder.append(line);
        }

        BloodCenterDTO bloodCenterDTO = objectMapper.readValue(bodyBuilder.toString(), BloodCenterDTO.class);
        UUID bloodCenterId = bloodCenterService.createBloodCenter(bloodCenterDTO);

        String response = objectMapper.writeValueAsString(new CreateBloodCenterResponse(bloodCenterId, "Blood Center created successfully!"));
        sendResponse(writer, 201, response);
    }

    private void handleGetBloodCenter(BufferedWriter writer, String path) throws IOException {
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
        writer.write("HTTP/1.1 " + statusCode + " OK\r\n");
        writer.write("Content-Type: application/json\r\n");
        writer.write("Content-Length: " + body.length() + "\r\n");
        writer.write("\r\n");
        writer.write(body);
        writer.flush();
    }
}

