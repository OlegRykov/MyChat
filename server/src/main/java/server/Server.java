package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    static ServerSocket server;
    static Socket socket;
    static final int PORT = 8189;

    public static void main(String[] args) {
        try {
            server = new ServerSocket(PORT);
            System.out.println("Server started");
            socket = server.accept();
            System.out.println("Client connected");

            Scanner sc = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Scanner scMsg = new Scanner(System.in);

            new Thread(() -> {
                while (true) {
                    String str = sc.nextLine();
                    if (str.equals("/end")) {
                        System.out.println("client disconnected");
                        break;
                    }
                    System.out.println("Client: " + str);
                }
            }).start();

            while (true) {
                String serverMsg = scMsg.nextLine();
                System.out.println("You: " + serverMsg);

                if (serverMsg.equals("/end")) {
                    break;
                }

                out.println(serverMsg);
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
