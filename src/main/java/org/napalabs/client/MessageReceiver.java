package org.napalabs.client;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MessageReceiver extends Thread {
    private BufferedReader in;

    public MessageReceiver(BufferedReader in) {
        this.in = in;
    }

    public void run() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                logMessage(message);
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logMessage(String message) {
        try (FileWriter fw = new FileWriter("client_log.log", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}