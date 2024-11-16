package com.hemoalert.HemoAlert.server;

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
            Alert alertaSP = new Alert(UUID.randomUUID(), "HC-SP", "11", BloodType.O_NEGATIVE, UUID.fromString("23502070-2213-42c4-8ecc-d43699fb1b2b"));
            Alert alertaRio = new Alert(UUID.randomUUID(), "HC-RIO", "21", BloodType.B_NEGATIVE, UUID.fromString("1c2a0638-fd0a-44e1-9701-99e9c88dd998"));

            System.out.print("Pressione ENTER para enviar o primeiro alerta...");
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
            transmissor.flush();


            System.out.print ("Pressione ENTER para terminar o programa");
            teclado.readLine ();
            transmissor.writeObject ("FIM");
            transmissor.flush ();

            transmissor.close();
            connection.close();
            } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
