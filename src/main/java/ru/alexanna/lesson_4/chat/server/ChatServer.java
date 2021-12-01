package ru.alexanna.lesson_4.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChatServer {

    private final ServerSocket socket;
    private final AuthenticationService authenticationService;
    private final Set<ClientHandler> loggedClients;

    private final Logger logger = LogManager.getLogger(ChatServer.class);

    public ChatServer() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            authenticationService = new AuthenticationService();
            loggedClients = new HashSet<>();
            this.socket = new ServerSocket(8888);


            while (true) {
//                System.out.println("Waiting for a new connection...");
                logger.info("Waiting for a new connection...");
                Socket client = socket.accept();
//                System.out.println("Client accepted.");
                logger.info("Client accepted.");
//                new Thread(() -> new ClientHandler(client, this)).start();
                executorService.execute(() -> {
                    new ClientHandler(client, this);
                });
            }
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong during connection establishing.", e);
        } finally {
            executorService.shutdown();
        }
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public synchronized void addClient(ClientHandler client) {
        loggedClients.add(client);
    }

    public synchronized void removeClient(ClientHandler client) {
        loggedClients.remove(client);
    }

    public synchronized boolean isUsernameOccupied(String username) {
        return loggedClients.stream()
                .anyMatch(c -> c.getName().equals(username));
    }

    public synchronized void broadcastMessage(String message) {
        loggedClients.forEach(ch -> ch.sendMessage(message));
    }

    public synchronized void privateMessage(String message, String username) {
        loggedClients.forEach(clientHandler -> {
            if (clientHandler.getName().equals(username)) {
                clientHandler.sendMessage(message);
            }
        });
    }
}
