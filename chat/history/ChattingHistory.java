package ru.alexanna.lesson_4.chat.history;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ChattingHistory {
    private final String fileName;

    public ChattingHistory(String username) {
        fileName = String.format("C:\\Users\\proc.LGOK\\IdeaProjects\\lesson_3\\history_[%s].txt", username);
    }

    public void writeToFile(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,true))) {
            writer.write(message + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> readFromFile() {
        List<String> list = new ArrayList<>();
        File file = new File(fileName);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    list.add(line);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (list.size() >= 100) {
            return list.subList(list.size()-100, list.size());
        }
        return list;
    }
}
