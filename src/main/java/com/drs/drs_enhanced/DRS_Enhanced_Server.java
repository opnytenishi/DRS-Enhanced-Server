package com.drs.drs_enhanced;

import com.drs.drs_enhanced.service.UserService;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class DRS_Enhanced_Server {

    private final static int SERVER_PORT = 8888;
    
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("DRS Server running on port " + SERVER_PORT);

            if (JPAUtil.checkConnection()) {
                System.out.println("DRS Database Connected Successfully");
            } else {
                System.out.println("Failed to connect to the database : / \nRunning in test mode.");
            }
            
            UserService.getOrCreateResponder();
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);
                new Thread(new ClientHandler(clientSocket)).start();
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
