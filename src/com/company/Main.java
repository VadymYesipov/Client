package com.company;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.getMessage();
        client.sendMessage();
    }
}
