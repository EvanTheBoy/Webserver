package com.hw.myserver0428;

import java.io.*;
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
            InputStreamReader input = new InputStreamReader(socket.getInputStream());
            BufferedReader bReader = new BufferedReader(input); //将字符流包装成缓冲流
            //解释该请求以确定所请求的特定文件
            String line = bReader.readLine();
            String[] lineArr = line.split(" "); //根据空格切割
            String fileName = lineArr[1].substring(1); //下面这个fileName就是index.html
            //根据文件名从服务器的文件系统获取文件，其实也就是从磁盘读取文件。
            File file = new File(fileName);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(fileName);
                BufferedInputStream bis = new BufferedInputStream(fis); //我们还是转换成缓冲流以提高效率
                byte[] bytes = new byte[1024];
                BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
                int length = 0;
                //由请求文件组成的HTTP响应报文，报文前面有首部行
                String head = "HTTP/1.0 200 OK \r\n"
                        + "Content-Type:text/html \r\n"
                        + "\r\n";
                bos.write(head.getBytes()); //先把首部行发出去，再发数据
                //现在发数据,向请求浏览器发送响应
                while ((length = bis.read(bytes)) != -1) {
                    bos.write(bytes, 0, length);
                }
                bos.close();
                bis.close();
            } else {
                //请求文件不存在，就产生404页面
                BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
                String notFound = "<html><h1>404 Not Found!</h1></html>";
                String head = "HTTP/1.0 404 no found \r\n"
                        + "Content-Type:text/html \r\n"
                        + "\r\n";
                bos.write(head.getBytes());
                bos.write(notFound.getBytes(StandardCharsets.UTF_8));
                bos.close();
            }
            bReader.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
