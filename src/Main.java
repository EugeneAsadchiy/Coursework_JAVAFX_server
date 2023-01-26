//import ServerFiles.ServerSettings;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static ServerSettings server;

    public static void main(String[] args) {
        try {
            server = new ServerSettings(new ServerSocket(1234));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error creating server");
        }
        server.Server_proslushka();
    }
}