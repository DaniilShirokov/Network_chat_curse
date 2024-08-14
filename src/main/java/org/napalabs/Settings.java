package org.napalabs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Settings {
    final static int DEFAULT_PORT = 42000;
    private int port;

    public Settings(String filename) {

        if (filename != null || filename.isEmpty()) {
            port = DEFAULT_PORT;
        } else {
            readSettings(filename);
        }
    }

    private void readSettings(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            if ((line = br.readLine()) != null) {
                String  array[] = line.split("=");
                port = Integer.parseInt(array[1]);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading settings: " + e.getMessage());
            port = DEFAULT_PORT;
        }
    }

    public int getPort() {
        return port;
    }
}