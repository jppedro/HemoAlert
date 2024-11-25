package com.hemoalert.HemoAlert.server;

import com.hemoalert.HemoAlert.controller.AlertController;
import com.hemoalert.HemoAlert.controller.BloodCenterController;
import com.hemoalert.HemoAlert.dto.AlertDTO;
import com.hemoalert.HemoAlert.dto.BloodCenterDTO;
import com.hemoalert.HemoAlert.service.AlertService;
import com.hemoalert.HemoAlert.service.BloodCenterService;
import com.hemoalert.HemoAlert.repository.AlertRepository;
import com.hemoalert.HemoAlert.repository.BloodCenterRepository;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HttpServer {
    private final int port;
    private final AlertController alertController;
    private final BloodCenterController bloodCenterController;

    public HttpServer(final int port, AlertController alertController, BloodCenterController bloodCenterController) {
        this.port = port;
        this.alertController = alertController;
        this.bloodCenterController = bloodCenterController;
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started on port: " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleRequest(clientSocket)).start();
            }

        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleRequest(Socket clientSocket) {
        try (ObjectInputStream reader = new ObjectInputStream(clientSocket.getInputStream());
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

            Object requestLine = reader.readObject();
            if (requestLine == null) return;

            String method, path;

            String request = (String)requestLine;
            String[] requestParts = request.split(" ");
            method = requestParts[0];
            path = requestParts[1];

            if (method.equals("POST") && path.equals("/alerts")) {
                AlertDTO alertDTO = (AlertDTO) reader.readObject();
                alertController.handleCreateAlert(writer, alertDTO);
            } else if (method.equals("GET") && path.startsWith("/alerts/")) {
                alertController.handleGetAlert(writer, path);
            } else if (method.equals("GET") && path.startsWith("/blood-centers/")) {
                bloodCenterController.handleGetBloodCenter(writer, path);
            } else if (method.equals("POST") && path.equals("/blood-centers")){
                BloodCenterDTO bloodCenterDTO = (BloodCenterDTO) reader.readObject();
                bloodCenterController.handleCreateBloodCenter(writer, bloodCenterDTO);
            } else {
                sendResponse(writer, 404, "Not Found");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
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

    private static Connection connectToDatabase() throws SQLException {
        String dbUrl = "jdbc:postgresql://localhost:5432/hemoalert";
        String dbUser = "postgres";
        String dbPassword = "jp280104";
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public static void main(String[] args) {
        try {
            Connection connection = connectToDatabase();

            AlertRepository alertRepository = new AlertRepository(connection);
            BloodCenterRepository bloodCenterRepository = new BloodCenterRepository(connection);

            AlertService alertService = new AlertService(alertRepository);
            BloodCenterService bloodCenterService = new BloodCenterService(bloodCenterRepository);

            AlertController alertController = new AlertController(alertService);
            BloodCenterController bloodCenterController = new BloodCenterController(bloodCenterService);

            HttpServer server = new HttpServer(8080, alertController, bloodCenterController);
            server.start();
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
