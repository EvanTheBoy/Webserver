package com.hw.myserver0428;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServerThread implements Runnable {
    private Socket socket;
    public ServerThread(Socket s) {
        this.socket = s;
    }

    @Override
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            byte[] data = new byte[1024];
            input.read(data);
            String message = new String(data);
            System.out.println("http客户端请求内容:" + message);
            String head = "HTTP/1.0 200 OK\r\n"
                    + "Server:batMimiServer/1.0\r\n"
                    + "Content-Type:text/html\r\n"
                    + "\r\n";
            output.write(head.getBytes());
            String s = "<html><h1>我收到了你的请求:" + "系统时间:" + System.currentTimeMillis() + "</h1></html>";
            output.write(s.getBytes(StandardCharsets.UTF_8));
            output.flush();
            socket.close();
            System.out.println("一个http请求结束!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
