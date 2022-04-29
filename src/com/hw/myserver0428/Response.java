package com.hw.myserver0428;

import java.io.IOException;
import java.io.OutputStream;

public class Response {
    private OutputStream output;
    public Response(OutputStream output) {
        this.output = output;
    }

    public void write(String msg) {
        try {
            output.write(msg.getBytes());
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
