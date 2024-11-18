package com.hemoalert.HemoAlert.server;

import com.hemoalert.HemoAlert.controller.AlertController;
import com.hemoalert.HemoAlert.controller.BloodCenterController;
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
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

            String requestLine = reader.readLine();
            if (requestLine == null) return;

            String[] requestParts = requestLine.split(" ");
            String method = requestParts[0];
            String path = requestParts[1];

            if (method.equals("POST") && path.equals("/alerts")) {
                alertController.handleRequest(clientSocket);
            } else if (method.equals("GET") && path.startsWith("/alerts/")) {
                alertController.handleRequest(clientSocket);
            } else if (method.equals("GET") && path.startsWith("/bloodcenters/")) {
                bloodCenterController.handleRequest(clientSocket);
            } else {
                sendResponse(writer, 404, "Not Found");
            }

        } catch (IOException e) {
            e.printStackTrace();
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
        String dbPassword = "your_password";
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public static void main(String[] args) {
        try {
            Connection connection = connectToDatabase();

            AlertRepository alertRepository = new AlertRepository(connection);
            BloodCenterRepository bloodCenterRepository = new BloodCenterRepository(connection);

            AlertService alertService = new AlertService(alertRepository);
            BloodCenterService bloodCenterService = new BloodCenterService(bloodCenterRepository);

            // Crie os controladores, passando os serviços
            AlertController alertController = new AlertController(alertService);
            BloodCenterController bloodCenterController = new BloodCenterController(bloodCenterService);

            // Inicie o servidor HTTP
            HttpServer server = new HttpServer(8080, alertController, bloodCenterController);
            server.start();
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
