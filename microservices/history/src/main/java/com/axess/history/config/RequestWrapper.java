package com.axess.history.config;


import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class RequestWrapper extends HttpServletRequestWrapper {
    private byte[] body;

    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        int contentLength = request.getContentLength();
        byte[] buffer = new byte[contentLength];
        ServletInputStream in = null;
            in = request.getInputStream();
            in.read(buffer, 0, contentLength);
            in.close();
            this.body = buffer;
//            int read;
//            char[] buf = new char[1024 * 8];
//            while ((read = reader.read(buf)) != -1) {
//                writer.write(buf, 0, read);
//            }
//            this.body = writer.getBuffer().toString().getBytes();
    }

    public String getBody() {
        return new String(body, StandardCharsets.UTF_8);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }

            @Override
            public void setReadListener(ReadListener listener) {
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public boolean isFinished() {
                return false;
            }
        };
    }
}