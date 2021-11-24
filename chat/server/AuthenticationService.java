package ru.alexanna.lesson_4.chat.server;

import java.sql.*;
import java.util.Objects;
import java.util.Optional;

public class AuthenticationService {

    public Optional<String> findUsernameByLoginAndPassword(String login, String password) {
        Connection connection = DatabaseConnector.getConnection();
        try {
            String usernameQuery = "SELECT username FROM users WHERE login = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(usernameQuery);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            String username = null;
            if (resultSet.next()) {
                username = resultSet.getString("username");
            }
            return Optional.ofNullable(username);
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong during a username lookup operation.", e);
        } finally {
            DatabaseConnector.close(connection);
        }
    }

    public int changeUsername(String oldUsername, String newUsername) {
        Connection connection = DatabaseConnector.getConnection();
        int result = 0;
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET username = ? WHERE username = ?");
            preparedStatement.setString(1, newUsername);
            preparedStatement.setString(2, oldUsername);
            result = preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            DatabaseConnector.rollback(connection);
            throw new RuntimeException("Something went wrong during the username change operation.", e);
        } finally {
            DatabaseConnector.close(connection);
        }
        return result;
    }

    private static class User {
        private String username;
        private String login;
        private String password;

        public User(String username, String login, String password) {
            this.username = username;
            this.login = login;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return Objects.equals(username, user.username) && Objects.equals(login, user.login) && Objects.equals(password, user.password);
        }

        @Override
        public int hashCode() {
            return Objects.hash(username, login, password);
        }
    }
}
