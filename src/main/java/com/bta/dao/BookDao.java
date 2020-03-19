package com.bta.dao;

import com.bta.Main;
import com.bta.domain.Book;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class BookDao implements Dao<Book> {

    private Properties connectionProperties;

    public BookDao() { // Konstuktor s metodom kotor6j podgruzzhaet dann6e dlja soedinenija

        connectionProperties = new Properties();
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (inputStream == null) {
                throw new RuntimeException(" Problem with properties file ");
            }
            connectionProperties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book findById(Long id) {

        final String sql = "select * from book where id = ?";

        try (Connection connection = DriverManager.getConnection(
                connectionProperties.getProperty("db.url"),
                connectionProperties.getProperty("db.username"),
                connectionProperties.getProperty("db.password")); ) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long bookId = resultSet.getLong("id");
                String bookName = resultSet.getString("name");
                String bookDescr = resultSet.getString("description");
                Integer bookReleasYear = resultSet.getInt("releas_year");
                return new Book(bookId, bookName, bookDescr, bookReleasYear);
            }

            resultSet.close();
            preparedStatement.close(); // zakr6t' tjazhel6 operacii

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Integer update(Book bookUpd) {

       /* UPDATE table_name
        SET column1 = value1, column2 = value2, ...
        WHERE condition;*/

        final String sql = "update book set name =?, description =?, releas_year =? where id =?";
        Integer affectedRows = 0;

        try (Connection connection = DriverManager.getConnection(
                connectionProperties.getProperty("db.url"),
                connectionProperties.getProperty("db.username"),
                connectionProperties.getProperty("db.password"));
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, bookUpd.getName());
            preparedStatement.setString(2, bookUpd.getDescription());
            preparedStatement.setInt(3, bookUpd.getReleasYear());
            preparedStatement.setLong(4, bookUpd.getId());
            affectedRows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return affectedRows;
    }

    @Override
    public Integer delete(Long id) {

        final String sql = "delete from book where id =?";
        Integer affectedRows = 0;

        try (Connection connection = DriverManager.getConnection(
                connectionProperties.getProperty("db.url"),
                connectionProperties.getProperty("db.username"),
                connectionProperties.getProperty("db.password"))) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            affectedRows = preparedStatement.executeUpdate();

            preparedStatement.close(); // zakr6t' tjazhel6 operacii

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return affectedRows;
    }

    @Override
    public Integer save(Book bookSave) {
        return null;
    }

    @Override
    public List<Book> findAll() {
        return null;
    }

}

