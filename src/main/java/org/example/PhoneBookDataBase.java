package org.example;

import java.sql.*;

public class PhoneBookDataBase {
    private final Connection connection;
    private final Statement statement;

    public PhoneBookDataBase(String dbName) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
            statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS phone_book (id INT PRIMARY KEY, name TEXT, phone_number INT)");
        } catch (SQLException e) {
            throw new RuntimeException("Can't create database");
        }
    }

    public void addContact(String name, int phoneNumber) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO phone_book (name, phone_number) VALUES (?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, phoneNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can't add new contact");
        }
    }

    public static void showContact(ResultSet resultSet) {
        try {
            while (resultSet.next()) {
                System.out.println("id" + resultSet.getInt("id") + "Ім'я: " + resultSet.getString("name")
                        + ", номер телефону: " + resultSet.getInt("phone_number"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't show contacts");
        }
    }

    public ResultSet searchContactDefault() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM phone_book");
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Can't search contact");
        }
    }

    public ResultSet searchContact(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM phone_book WHERE name = ?");
            preparedStatement.setString(1, name);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Can't search contact");
        }
    }

    public void updateContact(String name,  int newPhoneNumber) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE phone_book SET phone_number = ? WHERE name = ?");
            preparedStatement.setInt(1, newPhoneNumber);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can't update contact");
        }
    }

    public void deleteContact(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM phone_book WHERE name = ?");
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete contact");
        }
    }

}
