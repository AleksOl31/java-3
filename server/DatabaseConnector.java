package ru.alexanna.lesson_2.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnector {
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:/Users/oleg/IdeaProjects/GeekBrainsCourse/Java_3/lesson_3/chat.db");
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong during getting a connection.", e);
        }
    }

    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong during closing a connection.", e);
        }
    }

    public static void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong during a rollback operation.", e);
        }
    }
}
