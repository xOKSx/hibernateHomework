package ru.fintech.qa.homework.db.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbClient {
    private Connection connection = null;

    public final Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.h2.Driver");
                connection = DriverManager.getConnection(
                        "jdbc:h2:mem:myDb;DB_CLOSE_DELAY=-1",
                        "sa",
                        "sa");
                System.out.println("\nСоединение установлено");
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println("Ошибка при установлении соединения");
                e.printStackTrace();
            }
        }
        return connection;
    }
}
