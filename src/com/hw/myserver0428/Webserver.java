package com.hw.myserver0428;

import java.net.ServerSocket;
import java.net.Socket;

public class Webserver {
    public void createServer(int port) {
        try {
            ServerSocket ss = new ServerSocket(port);
            while (true) {
                Socket s = ss.accept();
                //处理调用处理方法
                process(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void process(Socket s) {
        ServerThread st = new ServerThread(s);
        Thread t = new Thread(st);
        t.start();
    }

    public static void main(String[] args) {
        new Webserver().createServer(8080);
    }
}
