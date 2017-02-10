package com.company;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Created by User on 003 03.02.17.
 */
public class Client {
    Socket socket;
    Client client;

    public Client() throws IOException {
        socket = new Socket("85.90.209.113", 8888);

        while (true) {
            Scanner scanner = new Scanner(System.in);
            char[] chars = scanner.nextLine().toCharArray();
            byte[] buffer = new byte[chars.length];

            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < chars.length; i++) {
                        try {
                            socket.getOutputStream().write((byte) chars[i]);
                            System.out.print((char) socket.getInputStream().read());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println();
                }
            }).start();
        }
    }
}
