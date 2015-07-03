package com.quantium.web.test;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static final int PORT = 9889;
    private static List<MessageFromServer> messageFromServerList;
    private static JTextArea textArea;
    private static JScrollPane jScrollPane ;

    public static void main(String[] args) {
        Socket socketClient = null;


        try {
            socketClient = new Socket("localhost", PORT);
            Scanner in = new Scanner(new BufferedReader(new InputStreamReader(socketClient.getInputStream())));
            messageFromServerList = new ArrayList<MessageFromServer>();

            while (true) {
                String incoming = in.nextLine();
                messageFromServerList.add(new MessageFromServer(incoming));
                if (incoming.equals("quit")) {
                    break;
                }
                repain(messageFromServerList);
            }
        } catch (UnknownHostException e) {
            System.out.println("Error client socket");
        } catch (IOException e) {
            System.out.println("Error client IO");
        } finally {
            if (socketClient != null) {
                try {
                    socketClient.close();
                } catch (IOException e) {
                    System.out.println("Error closed socket");
                }
            }
        }
    }

    private static void repain(List<MessageFromServer> messageFromServerList) {
        JFrame frame = new JFrame("le Chat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setResizable(false);
        textArea = new JTextArea(10, 30);
        textArea.setLineWrap(true);
        for (MessageFromServer mes : messageFromServerList) {
            textArea.append(mes.getDate() + "    " + mes.getContext()+"\n");
        }
        jScrollPane = new JScrollPane(textArea);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jScrollPane.setEnabled(false);
        jScrollPane.repaint();

        frame.add(jScrollPane);
        frame.setVisible(true);
    }
}
