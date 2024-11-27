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

        try {
            Socket connection = new Socket(hostname, port);

            ObjectOutputStream transmissor = new ObjectOutputStream(connection.getOutputStream());
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

            Alert alertaCampinas = new Alert(UUID.randomUUID(), "HC-CAMPINAS", "19", BloodType.A_NEGATIVE, UUID.fromString("23502070-2213-42c4-8ecc-d43699fb1b2b"));

            BloodCenterDTO bloodCenterDTO = new BloodCenterDTO(UUID.randomUUID(), "Hemocentro Campinas", "Rua Perto de La", "100", "Hemocentro", "Parque dos Testes", "Campinas", "SP", "12000000");
            System.out.print("Pressione ENTER para buscar o hemocentro DTO...");
            teclado.readLine();
            transmissor.writeObject("GET /blood-centers/903fd59d-7302-42df-8f88-a3d113a82083");
            transmissor.flush();

            AlertDTO alertDTO = new AlertDTO(UUID.randomUUID(), "HC-CAMPINAS", "19", BloodType.AB_NEGATIVE, UUID.fromString("23502070-2213-42c4-8ecc-d43699fb1b2b"));

            /*System.out.print("Pressione ENTER para fazer uma requisicao...");
            teclado.readLine();
            transmissor.writeObject("GET /alerts/d425676a-ab34-4673-92cd-119521ac6bee");
            transmissor.writeObject(alertDTO);
            transmissor.flush();*/

            /*System.out.print("Pressione ENTER para enviar o primeiro alerta...");
            teclado.readLine();
            transmissor.writeObject(alertaCampinas);
            transmissor.flush();

            System.out.print("Pressione ENTER para enviar o segundo alerta...");
            teclado.readLine();
            transmissor.writeObject(alertaSP);
            transmissor.flush();

            System.out.print("Pressione ENTER para enviar o terceiro alerta...");
            teclado.readLine();
            transmissor.writeObject(alertaRio);
            transmissor.flush();*/

            System.out.print ("Pressione ENTER para terminar o programa");
            teclado.readLine ();
            transmissor.writeObject ("FIM");
            transmissor.flush();

            transmissor.close();
            connection.close();
            } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
