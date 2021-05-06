package ru.fintech.qa.homework.db.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbService {
    private Connection connection = null;

    public final DbService establishNewConnection() {
        connection = new DbClient().getConnection();
        return this;
    }

    public final DbService closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Соединение закрыто");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при закрытии соединения");
            e.printStackTrace();
        }
        return this;
    }

    public final int executeQueryCountRows(final String sql) {
        int rowsCount = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                rowsCount++;
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsCount;
    }

    public final int executeQueryUpdatedRows(final String sql) {
        int updatedRows = 0;
        try {
            Statement statement = connection.createStatement();
            updatedRows = statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updatedRows;
    }
}
