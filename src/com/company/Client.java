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
        socket = new Socket("192.168.0.101", 8888);

        while (true) {
            Scanner scanner = new Scanner(System.in);
            char[] chars = scanner.nextLine().toCharArray();
            byte[] buffer = new byte[chars.length];
            /*BufferedInputStream bufferedInputStream = new BufferedInputStream(System.in);
            int i = 0;
            for (int j = 0; j < buffer.length; j++) {
                buffer[j]= (byte) bufferedInputStream.read();
            }
            for (int j = 0; j < buffer.length; j++) {
                System.out.println(buffer[j]);
            }*/

            new Thread(new Runnable() {
                @Override
                public void run() {
                    //buffer = scanner.nextLine();
                    System.out.println("her");
                    for (int i = 0; i < chars.length; i++) {
                        buffer[i] = (byte) chars[i];
                        System.out.println(chars[i] + "  " + buffer[i]);
                    }
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        socket.getOutputStream().write(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            while (socket.getInputStream().available() > 0) {
                int b = socket.getInputStream().read();
                char ch = (char) b;
                System.out.print(ch);
            }
            System.out.println();
        }
    }
}
