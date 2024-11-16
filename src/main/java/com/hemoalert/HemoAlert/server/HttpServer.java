package com.hemoalert.HemoAlert.server;

import com.hemoalert.HemoAlert.model.Alert;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class HttpServer {
    private final int port;

    public HttpServer(final int port) {
        this.port = port;
    }

    public void start(){
        try (Connection connection = connectToDatabase()){
            if (connection != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
                return;
            }

            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started on port: " + port);

            Socket clientSocket = serverSocket.accept();

            ObjectInputStream receptor = new ObjectInputStream(clientSocket.getInputStream());

            Object obj = null;
            do{
                obj = receptor.readObject();
                Alert alert = (Alert) obj;
                System.out.println("Received Alert: " + alert.toString());

                insertAlert(connection, alert);
            } while (!(obj instanceof String) || !((String)obj).toUpperCase().equals("FIM"));

            receptor.close();
            clientSocket.close();
            serverSocket.close();
        } catch (Exception e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Connection connectToDatabase() {
        String dbUrl = "jdbc:postgresql://localhost:5432/hemoalert";
        String dbUser = "postgres";
        String dbPassword = "jp280104";

        try {
            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (Exception e) {
            System.out.println("Database connection exception: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private void insertAlert(Connection connection, Alert alert) throws Exception {
        String insertSql = "INSERT INTO alerta (id, ddd, tipo_sanguineo, hemocentro_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            preparedStatement.setObject(1, alert.getId());
            preparedStatement.setString(2, alert.getCity());
            preparedStatement.setString(3, alert.getBloodType().getDisplayName());
            preparedStatement.setObject(4, alert.getCenterUuid());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Alert inserted successfully! Rows affected: " + rowsAffected);
            } else {
                System.out.println("Failed to insert alert.");
            }
        } catch (Exception e) {
            System.out.println("Database insert exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HttpServer server = new HttpServer(8080);
        server.start();
    }
}
