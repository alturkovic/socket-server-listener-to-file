package com.github.alturkovic.socket.file.server;

import com.github.alturkovic.socket.file.client.FileSaveThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.UUID;

public class SocketListener {

    public static void main(String args[]) throws IOException {
        final String port = args[0];
        final String directory = args[1];

        ServerSocket serverSocket = new ServerSocket(Integer.valueOf(port));

        Socket socket;
        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            final Calendar cal = Calendar.getInstance();
            new FileSaveThread(socket, String.format("%s/%s-%s/%s.dat", directory, cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH), UUID.randomUUID().toString())).start();
        }
    }
}
