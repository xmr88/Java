package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Thread.*;

public class Main {

    public static final int COUNT_BREAK = 5;
    public static final int EXIT_CODE = 10;
    public static final int COMMON_CODE = 9;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1525);
        System.out.println("Server start");


        Socket connectionSocket = serverSocket.accept();
        System.out.println("Waiting for connection");


        //request
        InputStream request = connectionSocket.getInputStream();
        OutputStream response = connectionSocket.getOutputStream();


       int iterationCount = 0;


        while(connectionSocket.isConnected()) {

            //What I receive from the client
            int responseToken = request.read();
            System.out.println("Response token: " + responseToken);

            if(iterationCount == COUNT_BREAK) {
                response.write(EXIT_CODE); // These are status codes
                break;
            }else{
                response.write(COMMON_CODE);
            }

            iterationCount++;

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }




        request.close();
        response.close();
        serverSocket.close();
    }
}