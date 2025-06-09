package com.drs.drs_enhanced;

import com.drs.drs_enhanced.model.Request;
import com.drs.drs_enhanced.model.User;
import com.drs.drs_enhanced.service.UserService;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try (
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        ) {
            Object input;
            while ((input = in.readObject()) != null) {

                if (input instanceof Request) {
                    Request request = (Request) input;
                    String action = request.getAction();
                    Object data = request.getData();
                    User user;
                    Object response;

                    switch (action) {
                        case "login":
                            user = (User) data;
                            System.out.println("Login attempt: " + user.getEmail());
                            response = UserService.login(user.getEmail(), 
                                    user.getPassword());
                            if (response instanceof User) {
                                System.out.println("Login Success!");
                            } else {
                                System.out.println("Login Failed!");
                            }
                            out.writeObject(response);
                            break;
                            
                        case "register":
                            user = (User) data;
                            System.out.println("Register attempt: " + user.getEmail());
                            boolean responseBoolean =UserService.createUser(user);
                            out.writeObject(responseBoolean);
                            if (responseBoolean) {
                                System.out.println("Login Success!");
                            } else {
                                System.out.println("Login Failed!");
                            }
                            break;

                        case "test":
                            out.writeObject("Server received test!");
                            break;

                        default:
                            out.writeObject("Unknown action: " + action);
                    }

                    out.flush();
                }
            }
        } catch (Exception e) {
            System.out.println("Client disconnected or error: " + e.getMessage());
        }
    }

}
