package com.company;

import com.sun.corba.se.spi.orbutil.threadpool.ThreadPoolManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.*;

public class JavaSocketServer {

    private static final int SERVER_PORT = 8080;
    private static ServerSocket serverSocket;
    private static ExecutorService executorService;


    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(SERVER_PORT);
        executorService = Executors.newFixedThreadPool(5);

        int count = 0;
        while (true) {
            Socket socket = serverSocket.accept();
            if (count >= 5) {
                socket.close();
                continue;
            }
            executorService.submit(new Server(socket));
            count++;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        executorService.shutdownNow();
        serverSocket.close();
    }

    public static class Server implements Runnable {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public Server(Socket socket) throws IOException {
            this.socket = socket;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        }

        @Override
        public void run() {
            String request, response;

            System.out.printf("[%s] CONNECTED %s:%s%n%n", Thread.currentThread(), socket.getInetAddress(), socket.getPort());
            while (true) {

                try {
                    request = in.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }

                if ("Done".equalsIgnoreCase(request) || Objects.isNull(request)) {
                    break;
                }

                System.out.printf("[%s] Request Received... %s%n", Thread.currentThread(), request);

                response = process(request);
                out.println(response);

                System.out.printf("[%s] Response Send... %s%n", Thread.currentThread(), request);
            }

            System.out.printf("[%s] DISCONNECTED %n", Thread.currentThread());
        }
    }

    private static String process(String request) {
        return request + "?!";
    }
}
