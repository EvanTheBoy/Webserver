package com.hw.myserver0428;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Webserver {
    public void createServer(int port) {
        try {
            ServerSocket ss = new ServerSocket(port);
            while (true) {
                Socket s = ss.accept();
                ServerThread st = new ServerThread(s);
                Thread t = new Thread(st);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Webserver().createServer(8080);
    }
}
