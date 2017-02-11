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


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Scanner scanner = new Scanner(System.in);
                    char[] chars = scanner.nextLine().toCharArray();
                    byte[] buffer = new byte[chars.length];
                    for (int i = 0; i < chars.length; i++) {
                        buffer[i] = (byte) chars[i];
                    }
                    try {
                        socket.getOutputStream().write(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    //System.out.println();
                    try {
                        //while(socket.getInputStream().available()>0) {
                        byte[] buffer = new byte[255];
                        int k = socket.getInputStream().read(buffer);


                        //}
                        if(socket.getInputStream().available()==0) {
                            //System.out.print((char)k);
                            System.out.print(new String(buffer));
                            System.out.println();
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }).start();


        /*while (true) {
            Scanner scanner = new Scanner(System.in);
            char[] chars = scanner.nextLine().toCharArray();
            byte[] buffer = new byte[chars.length];

            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < chars.length; i++) {
                        try {
                            socket.getOutputStream().write((byte) chars[i]);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        while (socket.getInputStream().available() > 0) {
                            System.out.print((char) socket.getInputStream().read());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


            /*new Thread(new Runnable() {
                @Override
                public void run() {
                    //buffer = scanner.nextLine();
                    //System.out.println("her");
                    for (int i = 0; i < chars.length; i++) {
                        buffer[i] = (byte) chars[i];
                        //System.out.println(chars[i] + "  " + buffer[i]);
                    }
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        socket.getOutputStream().write(buffer);
                        while (socket.getInputStream().available() > 0) {
                            int b = socket.getInputStream().read();
                            char ch = (char) b;
                            System.out.print(ch);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            /*while (socket.getInputStream().available() > 0) {
                int b = socket.getInputStream().read();
                char ch = (char) b;
                System.out.print(ch);
            }*/
        //System.out.println();
    }
}
