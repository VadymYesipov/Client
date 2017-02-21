package com.company;

import sun.net.util.IPAddressUtil;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Created by User on 003 03.02.17.
 */
public class Client {
    private static final int PORT = 8887;
    private static final String IP = "85.90.209.113";//192.168.0.101
    private Socket socket;
    //InetAddress ipAddress = InetAddress.getByName(localhost);
    //socket = new Socket(ipAddress, serverPort);

    public Client() throws IOException {
        socket = new Socket(IP, PORT);
        sendInfo();
        getInfo();
    }

    private void sendInfo(){
        System.out.print("Напишите свой ник: ");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Scanner scanner = new Scanner(System.in);
                char[] chars = scanner.nextLine().toCharArray();
                byte[] buffer = new byte[chars.length];
                byte[] mode = new byte[chars.length];
                for (int i = 0; i < chars.length; i++) {
                    if ((int) chars[i] > 255) {
                        mode[i] = 1;
                    } else {
                        mode[i] = 0;
                    }
                    buffer[i] = (byte) chars[i];
                }
                try {
                    socket.getOutputStream().write(buffer);
                    socket.getOutputStream().write(mode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void getInfo() {
        try {
            int k = 0;
            byte[] buffer = new byte[255];
            byte[] mode = new byte[255];
            byte[] buffer1 = new byte[255];
            byte[] mode1 = new byte[255];
            byte[] buffer2 = new byte[255];
            byte[] mode2 = new byte[255];
            socket.getInputStream().read(buffer);
            socket.getInputStream().read(mode);
            socket.getInputStream().read(buffer1);
            socket.getInputStream().read(mode1);
            socket.getInputStream().read(buffer2);
            socket.getInputStream().read(mode2);
            for (int i = 0; i < buffer.length; i++) {
                if (mode[i] == 1 && (buffer[i] + 1024) >= 1025 && (buffer[i] + 1024) <= 1105) {
                    System.out.print((char) (buffer[i] + 1024));
                } else {
                    System.out.print((char) buffer[i]);
                }
            }
            for (int i = 0; i < buffer1.length; i++) {
                if (mode1[i] == 1 && (buffer1[i] + 1024) >= 1025 && (buffer1[i] + 1024) <= 1105) {
                    System.out.print((char) (buffer1[i] + 1024));
                } else {
                    System.out.print((char) buffer1[i]);
                }
            }
            for (int i = 0; i < buffer2.length; i++) {
                char ch = (char) buffer2[i];
                if (ch == ' ') {
                    System.out.print(ch);
                } else if (((byte) ch + 1024) > 1025 && ((byte) ch + 1024) < 1105) {
                    ch = (char) ((byte) ch + 1024);
                    System.out.print(ch);
                }
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void sendMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    senderBusiness();
                }
            }
        }).start();
    }

    protected void getMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    getterBusiness();
                }
            }
        }).start();
    }

    private void senderBusiness() {
        Scanner scanner = new Scanner(System.in);
        char[] chars = scanner.nextLine().toCharArray();
        byte[] buffer = new byte[chars.length];
        byte[] mode = new byte[chars.length];
        for (int i = 0; i < chars.length; i++) {
            if ((int) chars[i] > 255) {
                mode[i] = 1;
            } else {
                mode[i] = 0;
            }
            buffer[i] = (byte) chars[i];
        }
        try {
            socket.getOutputStream().write(buffer);
            socket.getOutputStream().write(mode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getterBusiness(){
        try {
            while (socket.getInputStream().available() > 0) {
                byte[] buffer = new byte[255];
                byte[] mode = new byte[255];
                socket.getInputStream().read(buffer);
                socket.getInputStream().read(mode);
                byte[] buffer1 = new byte[255];
                byte[] mode1 = new byte[255];
                socket.getInputStream().read(buffer1);
                socket.getInputStream().read(mode1);
                for (int i = 0; i < buffer1.length; i++) {
                    if (mode1[i] == 1) {
                        System.out.print((char) (buffer1[i] + 1024));
                    } else {
                        System.out.print((char) buffer1[i]);
                    }
                }
                System.out.print(": ");
                for (int i = 0; i < buffer.length; i++) {
                    if (mode[i] == 1) {
                        System.out.print((char) (buffer[i] + 1024));
                    } else {
                        System.out.print((char) buffer[i]);
                    }
                }
                System.out.println();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
