package ru.alexanna.lesson_4.chat.server;

import ru.alexanna.lesson_4.chat.history.ChattingHistory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientHandler {

    private final Socket socket;
    private final ChatServer server;
    private final DataInputStream in;
    private final DataOutputStream out;
    private String name;
    private AtomicBoolean isConnected;
    private ChattingHistory chattingHistory;
    private final Logger logger = LogManager.getLogger(ClientHandler.class);

    public ClientHandler(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
        isConnected = new AtomicBoolean(false);
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            throw new RuntimeException("Something went wring during a client connection establishing.", ex);
        }

        doAuthentication();
        listenMessages();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void doAuthentication() {
        try {
            socket.setSoTimeout(120_000);
            performAuthentication();
            socket.setSoTimeout(0);
        } catch (SocketTimeoutException socketTimeoutException) {
//            System.out.println("Authentication timeout expired");
            logger.warn("Authentication timeout expired");
            closeConnection();
        } catch (IOException ex) {
            throw new RuntimeException("Something went wrong during a client authentication.", ex);
        }
    }

    private void performAuthentication() throws IOException {
        while (true) {
            String inboundMessage = in.readUTF();
            if (inboundMessage.startsWith("-auth")) {
                // valid request sample: -auth l1 p1
                String[] credentials = inboundMessage.split("\\s");
                server.getAuthenticationService()
                        .findUsernameByLoginAndPassword(credentials[1], credentials[2])
                        .ifPresentOrElse(
                                username -> {
                                    if (!server.isUsernameOccupied(username)) {
                                        String message = String.format("User [%s] is logged in", username);
                                        server.broadcastMessage(message);
                                        logger.info(message);
                                        name = username;
                                        server.addClient(this);
                                        isConnected.set(true);
                                        loadHistory();
                                        sendMessage("You are registered in the chat");
                                    } else {
                                        sendMessage("Current username is already occupied.");
                                    }
                                },
                                () -> sendMessage("Bad credentials.")
                        );

                if (isConnected.get()) break;
            } else {
                sendMessage("You need to be logged-in.");
            }
        }
    }

    private void loadHistory() {
        chattingHistory = new ChattingHistory(getName());
        List<String> chattingHistoryList = chattingHistory.readFromFile();
        for (String line: chattingHistoryList) {
            sendMessage(line);
        }
    }

    public void sendMessage(String outboundMessage) {
        try {
            out.writeUTF(outboundMessage);
            chattingHistory.writeToFile(outboundMessage);
        } catch (IOException e) {
            closeConnection();
        }
    }

    public void readMessage() {
        try {
            String messageFromClient = in.readUTF();
            String message = String.format("[%s]: " + messageFromClient, getName());
            if (isUsernameChangeApplied(messageFromClient)) {
                if (changeUsername(messageFromClient)) {
                    String newName = getNewName(messageFromClient);
                    message = String.format("User [%s] changed his name to [%s]", name, newName);
                    setName(newName);
                }
            } else if (isPrivateMessage(messageFromClient)) {
                sendMessage(message);
                server.privateMessage(getPrivateMessage(messageFromClient), getPrivateUser(messageFromClient));
            } else {
                server.broadcastMessage(message);
            }

        } catch (IOException e) {
            closeConnection();
        }
    }

    private boolean changeUsername(String message) {
        boolean result = false;
        String newName = getNewName(message);
        if (!newName.isEmpty()) {
            result = server.getAuthenticationService().changeUsername(name, newName) > 0;
        }
        return  result;
    }

    private String getNewName(String message) {
        String[] strings = message.split("\\s");
        return strings.length > 0 ? strings[1] : "";
    }

    private boolean isUsernameChangeApplied(String message) {
        return message.startsWith("-chname");
    }

    public void listenMessages() {
        while (isConnected.get()) {
            readMessage();
        }
    }

    private boolean isPrivateMessage(String message) {
        return message.startsWith("-w");
    }

    private String getPrivateUser(String message) {
        String[] strings = message.split("\\s");
        return strings[1];
    }

    private String getPrivateMessage(String message) {
        String[] strings = message.split("\\s");
        String resultStr = "";
        for (int i = 2; i < strings.length; i++) {
            resultStr += strings[i] + " ";
        }
        return String.format("Private message from [%s]: %s", getName(), resultStr);
    }


    private void closeConnection() {
        isConnected.set(false);
        String message = String.format("[%s] disconnected", getName());
//        System.out.println(message);
        logger.info(message);
        server.removeClient(this);
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
