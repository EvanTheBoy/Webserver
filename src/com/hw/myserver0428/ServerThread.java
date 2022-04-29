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

    //一下可生成全局唯一的session流水号
    private int count = 0;
    private String getSeq() {
        return "系统时间:" + System.currentTimeMillis() + (count++) + "miniWebServer";
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
            String html = "";
            //以下是处理流程，究竟是老朋友还是新朋友，用session判断，这个session应该是保存在浏览器内存的，浏览器关掉就没了
            if (message.indexOf("SessionminiWeb") > 0) {
                //如果是老朋友
                String head = "HTTP/1.0 200 OK\r\n"
                        + "Server:batMimiServer/1.0\r\n"
                        + "Content-Type:text/html\r\n"
                        + "\r\n";
                output.write(head.getBytes());
                int start = message.indexOf("SessionminiWeb");
                int end = message.indexOf("miniWebServer");
                String sessionStr = message.substring(start, end);
                html = "<html><h1><br>老朋友，我记得你的sid" + sessionStr + "</h1></html>";
            } else {
                //这里就是新朋友了
                String sid = getSeq();
                String head = "HTTP/1.0 200 OK\r\n"
                        +"Set-Cookie:SessionminiWeb=" + sid + "\r\n"
                        +"Server:batMimiServer/1.0\r\n"
                        +"Content-Type:text/html\r\n"
                        +"\r\n";
                output.write(head.getBytes());
                html = "<html><h1><br>第一次来的朋友，送你一个sid:" + sid + "</h1></html>";
            }
            output.write(html.getBytes(StandardCharsets.UTF_8));
            output.flush();
            output.close();
            socket.close();
            System.out.println("一个http请求结束!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
