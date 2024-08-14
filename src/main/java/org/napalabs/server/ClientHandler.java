package org.napalabs.server;


import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            Server.addClient(out);
            out.println("Welcome to the chat!");
            out.println("To exit, enter '/exit'");
            username = in.readLine();

            String welcomeMessage = username + " has joined the chat.";
            Server.broadcast(welcomeMessage);

            String message;
            while ((message = in.readLine()) != null) {
                if (message.equalsIgnoreCase("/exit")) {
                    break;
                }
                Server.broadcast(username + ": " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Server.removeClient(out);
            Server.broadcast(username + " has left the chat.");
        }
    }
}