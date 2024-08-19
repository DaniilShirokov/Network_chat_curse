package org.napalabs.client;
import org.napalabs.Settings;

import java.io.*;
import java.net.*;

public class ChatClient {

    private PrintWriter out;
    private BufferedReader in;

    public static void main(String[] args) {
        new ChatClient().start();
    }

    public void start() {
        Settings settings = new Settings("settings.txt");
        int port = settings.getPort();

        try(Socket socket = new Socket("localhost", port)) {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter your name:");
            String username = consoleIn.readLine();
            out.println(username);

            new MessageReceiver(in).start();

            String message;
            while ((message = consoleIn.readLine()) != null) {
                if (message.equalsIgnoreCase("/exit")) {
                    break;
                }
                out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}