package com.huy.appnoithat.Handler;

import com.huy.appnoithat.Work.OpenFileWork;
import com.huy.appnoithat.Work.WorkFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MultipleInstanceHandler {
    final static Logger LOGGER = LogManager.getLogger(MultipleInstanceHandler.class);
    private static final int port = 12675;
    public static boolean isSingleInstance(String[] args) {
        try {
            InetAddress host = InetAddress.getLocalHost();
            try (Socket clientSocket = new Socket(host.getHostName(), port)) {
                LOGGER.info("Another instance is running.");
                if (args.length != 0) {
                    ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                    // send the file to others instance
                    LOGGER.info("Send file to another instance: " + args[0]);
                    out.writeObject(args[0]);
                }
            }
            return false;
        } catch (IOException e) {
            LOGGER.info("First instance");
            return true;
        }

    }
    public static void startHandleMultipleInstance() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                // Try to open a server socket on a specific port
                while (true) {
                    // Wait for a connection attempt from another instance
                    Socket socket = serverSocket.accept();
                    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    String message = (String) objectInputStream.readObject();
                    LOGGER.info("Receive file from other instance: " + message);
                    WorkFactory.addNewOpenFileWork(new OpenFileWork(message));
                    // Handle the file path sent from the second instance
                    // For example, you can process the file associated with the second instance here
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
