package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            PhoneBookDataBase dataBase = new PhoneBookDataBase("phone_book.db");

            while (true) {
                System.out.println("1. Додати контакт");
                System.out.println("2. Переглянути контакти");
                System.out.println("3. Знайти контак");
                System.out.println("4. Видаленя контакту");
                System.out.println("5. Редагування контакту");
                System.out.println("6. Зберегти та вийти");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.println("Введіть ім'я: ");
                        String name = scanner.nextLine();
                        System.out.println("Введіть номер телефону: ");
                        int phoneNumber = scanner.nextInt();
                        dataBase.addContact(name, phoneNumber);
                        System.out.println("Контакт додано");
                        break;
                    case 2:
                        ResultSet resultSet1 = dataBase.searchContactDefault();
                        while (resultSet1.next()) {
                            System.out.println(
                                    "ID: " + resultSet1.getInt("id") + "\n" +
                                            "Ім'я: " + resultSet1.getString("name") + "\n" +
                                            "Номер телефону: "
                                    + resultSet1.getString("phone_number"));
                        }
                        break;
                    case 3:
                        System.out.println("Введіть ім'я контакту: ");
                        String findhName = scanner.nextLine();
                        ResultSet resultSet = dataBase.searchContact(findhName);
                        while (resultSet.next()) {
                            System.out.println("Ім'я: " + resultSet.getString("name") + ", Номер телефону: "
                                    + resultSet.getString("phone_number"));
                        }
                        break;
                    case 4:
                        System.out.println("Введіть ім'я для видалення: ");
                        String deleteName = scanner.nextLine();
                        dataBase.deleteContact(deleteName);
                        System.out.println("Контакт успішно видалено!");
                        break;
                    case 5:
                        System.out.print("Введіть ім'я для редагування: ");
                        String editName = scanner.nextLine();
                        System.out.print("Введіть новий номер телефону: ");
                        int editNumber = scanner.nextInt();
                        dataBase.updateContact(editName, editNumber);
                        System.out.println("Контакт успішно відредаговано!");
                        break;
                    case 6:
                        System.out.println("Програма закриваєтся");
                        return;
                    default:
                        System.out.println("Неправильний вибір. Спробуйте ще раз.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Can't find db");
        }
    }
}