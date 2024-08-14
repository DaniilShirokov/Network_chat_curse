package org.napalabs.server;
import org.napalabs.Settings;

import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.Set;

public class Server {
    private static Set<PrintWriter> clientWriters = new HashSet<>();
    private static final String LOG_FILE = "server_log.log";
    final static String FILE_NAME = "settings";

    public static void main(String[] args) throws IOException {
        Settings settings = new Settings(FILE_NAME);
        int port = settings.getPort();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Chat server is running on port: " + port);
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        }
    }

    public static void broadcast(String message) {
        logMessage(message);
        for (PrintWriter writer : clientWriters) {
            writer.println(message);
        }
    }

    private static void logMessage(String message) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addClient(PrintWriter writer) {
        synchronized (clientWriters) {
            clientWriters.add(writer);
        }
    }

    public static void removeClient(PrintWriter writer) {
        synchronized (clientWriters) {
            clientWriters.remove(writer);
        }
    }
}


