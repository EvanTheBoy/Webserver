package com.hw.myserver0428;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Webserver {
    public void createServer(int port) {
        try {
            ServerSocket ss = new ServerSocket(port);
            Socket s = ss.accept();
            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

    }
}
