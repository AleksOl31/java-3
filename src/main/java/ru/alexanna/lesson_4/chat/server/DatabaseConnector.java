package ru.alexanna.lesson_4.chat.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnector {
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:C:\\Users\\proc.LGOK\\Desktop\\sqlite-tools-win32-x86-3360000\\chat.db");
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
