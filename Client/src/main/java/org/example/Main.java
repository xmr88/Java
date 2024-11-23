package org.example;

import java.io.IOException;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;

import static java.lang.Thread.*;


//Client
public class Main {

    private static final int REQUEST_TOKEN = 8;
    public static final int EXIT_CODE = 404;

    public static void main(String[] args) throws IOException{

        Socket clientSocket = new Socket("localhost", 1525);

        InputStream response = clientSocket.getInputStream();
        OutputStream request = clientSocket.getOutputStream();

        while(clientSocket.isConnected()){

            //sending request to the Server
            request.write(REQUEST_TOKEN);

            int responseToken = response.read();

            //awaiting response from the Server
            System.out.println("Server responded with:" + responseToken);

            if(responseToken == EXIT_CODE){
                break;
            }

            try{
                sleep(1000);
            }catch(InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        response.close();
        request.close();
        clientSocket.close();

    }
}