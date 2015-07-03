package com.quantium.web.test;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Server  {
    private ServerSocket serverSocket;
    private Socket lastClientSocket;
    private CopyOnWriteArrayList<PrintWriter> writers = new CopyOnWriteArrayList<PrintWriter>();
    private List<Socket> clientsSockets = new ArrayList<Socket>();
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);


    private void launch() {
        ConnectionJDBC connectionJDBC= new ConnectionJDBC();
        try {
            serverSocket = new ServerSocket(Client.PORT);
            lastClientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.out.println("Error server socket");
        }

        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //check if somebody connected
                if (lastClientSocket.isConnected()) {
                    PrintWriter out = null;
                    try {
                        out = new PrintWriter(lastClientSocket.getOutputStream(), true);
                        //TODO send message history
                        out.println("Welcome!");
                        writers.add(out);
//                        for (PrintWriter writer : writers) {
//                            connectionJDBC.addDate(writer.toString());
//                        }
                        clientsSockets.add(lastClientSocket);
                        lastClientSocket = serverSocket.accept();
                        System.out.println(out.toString());
                    } catch (IOException e) {
                        System.out.println("Error socket output");
                    }
                }
            }
        }, 100, 100, TimeUnit.MILLISECONDS);

    }

    private void close() {
        executor.shutdownNow();
        for (PrintWriter writer : writers) {
            writer.close();
        }
        for (Socket socket : clientsSockets) {
            if (socket != null && !socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Error close sockets");
                }
            }
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Error close socket");
        }
    }

    private void sendMes(String inputConcole) {
        for (PrintWriter printWriter : writers){
            printWriter.println(inputConcole);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.launch();
            Scanner scannerConsole = new Scanner(System.in);
            while (true) {
                String inputConcole = scannerConsole.nextLine();
                server.sendMes(inputConcole);
                if (inputConcole.equals("quit")) {
                    break;
                }

            }
        }finally {
            server.close();

        }
    }
}
