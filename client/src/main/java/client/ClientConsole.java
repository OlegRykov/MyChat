package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientConsole {
    private Socket socket;
    private final String IP_ADDRESS = "localhost";
    private final int PORT = 8189;

    public ClientConsole() {
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            Scanner sc = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Scanner scMsg = new Scanner(System.in);

            new Thread(() -> {
                while (true) {
                    String str = sc.nextLine();

                    if (str.equals("/end")) {
                        break;
                    }
                    System.out.println("Server: " + str);
                }
            }).start();

            while (true) {
                String clientMsg = scMsg.nextLine();
                System.out.println("You: " + clientMsg);

                if (clientMsg.equals("/end")) {
                    break;
                }
                out.println(clientMsg);
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
