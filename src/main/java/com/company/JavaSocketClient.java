package com.company;

import jdk.internal.util.xml.impl.Input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class JavaSocketClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8080);

        System.out.printf("CONNECTED %s %s%n%n", socket.getInetAddress(), socket.getLocalSocketAddress());

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String request, response;
        while (true) {

            request = br.readLine();
            if ("Done".equalsIgnoreCase(request) || Objects.isNull(request)) {
                break;
            }

            out.println(request);
            response = in.readLine();
            System.out.println("[SERVER Response] " + response);
        }

    }
}
