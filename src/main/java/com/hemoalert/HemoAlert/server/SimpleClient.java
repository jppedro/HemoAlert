package com.hemoalert.HemoAlert.server;

import com.hemoalert.HemoAlert.dto.AlertDTO;
import com.hemoalert.HemoAlert.dto.BloodCenterDTO;
import com.hemoalert.HemoAlert.model.Alert;
import com.hemoalert.HemoAlert.model.BloodType;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

public class SimpleClient {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 8080;

        try (Socket connection = new Socket(hostname, port);
             ObjectOutputStream transmissor = new ObjectOutputStream(connection.getOutputStream());
             BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader servidorResposta = new BufferedReader(new InputStreamReader(connection.getInputStream()))){

            connection.setSoTimeout(30000);

            String responseLine;

            /*AlertDTO alertDTO = new AlertDTO(UUID.randomUUID(), "HC-PUCSP", "11", BloodType.O_NEGATIVE, UUID.fromString("2764fed1-7b01-4a89-a4c1-c654699bcb22"));

            System.out.print("Pressione ENTER para criar um alerta...");
            teclado.readLine();
            if(connection.isConnected()){
                transmissor.writeObject("POST /alerts");
                transmissor.writeObject(alertDTO);
                transmissor.flush();
                while ((responseLine = servidorResposta.readLine()) != null) {
                    System.out.println("Servidor: " + responseLine);
                }
            }

            /*System.out.print("Pressione ENTER para buscar o alerta com uuid b5206890-da82-4221-99b7-3acfd5d6a941...");
            teclado.readLine();
            if(connection.isConnected()){
                transmissor.writeObject("GET /alerts/b5206890-da82-4221-99b7-3acfd5d6a941");
                transmissor.flush();
                while ((responseLine = servidorResposta.readLine()) != null) {
                    System.out.println("Servidor: " + responseLine);
                }
            }*/

            BloodCenterDTO bloodCenterDTO = new BloodCenterDTO(UUID.randomUUID(), "HC-PUCSP", "Rua PUC São Paulo", "200", "Hemocentro", "Parque Ibirapuera", "São Paulo", "SP", "13111000");

            System.out.print("Pressione ENTER para criar um hemocentro...");
            teclado.readLine();
            if(connection.isConnected()) {
                transmissor.writeObject("POST /blood-centers");
                transmissor.writeObject(bloodCenterDTO);
                transmissor.flush();
                while ((responseLine = servidorResposta.readLine()) != null) {
                    System.out.println("Servidor: " + responseLine);
                }
            }

            /*System.out.print("Pressione ENTER para buscar o hemocentro DTO...");
            teclado.readLine();
            if(connection.isConnected()) {
                transmissor.writeObject("GET /blood-centers/d3a1a69d-800b-42aa-a228-40688a471e7a");
                transmissor.flush();
                while ((responseLine = servidorResposta.readLine()) != null) {
                    System.out.println("Servidor: " + responseLine);
                }
            }*/

            System.out.print ("Pressione ENTER para terminar o programa");
            teclado.readLine ();
            transmissor.writeObject ("FIM");
            transmissor.flush();

            connection.close();
            transmissor.close();
        } catch (IOException e) {
            System.out.println("Erro de IO: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}