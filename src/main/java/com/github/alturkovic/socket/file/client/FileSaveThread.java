package com.github.alturkovic.socket.file.client;

import java.io.*;
import java.net.Socket;

public class FileSaveThread extends Thread {
    private Socket socket;
    private String filename;

    public FileSaveThread(final Socket socket, final String filename) {
        this.socket = socket;
        this.filename = filename;
    }

    @Override
    public void run() {
        final InputStream inputStream;
        final FileOutputStream fileOutputStream;

        try {
            new File(filename).getParentFile().mkdirs();
            fileOutputStream = new FileOutputStream(filename);

            inputStream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        byte[] buffer = new byte[4096];
        try {
            copyLarge(inputStream, fileOutputStream, buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long copyLarge(InputStream input, OutputStream output, byte[] buffer) throws IOException {
        long count = 0;
        int n;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
}
