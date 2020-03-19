package com.bta;

import com.bta.dao.BookDao;
import com.bta.dao.Dao;
import com.bta.domain.Book;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class Main {

    private static Properties getConnectionProperties () throws IOException {
        Properties properties = null;
        // Soedinenie s config.properties soderzhashij dann6e s dostupom k serveru bz6 dann6h
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("config.properties")){
            properties = new Properties();
            if (inputStream == null) {
                System.out.println(" Problem with properties file ");
            }
            properties.load( inputStream);
        }
        return properties;
    }

    public static void selectAll () throws SQLException, IOException {

        Properties properties = getConnectionProperties();

        try (Connection connection = DriverManager.getConnection(properties.getProperty("db.url")
                ,properties.getProperty("db.username")
                , properties.getProperty("db.password"))) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from book");
            while (resultSet.next()) {
                String bookName = resultSet.getString("name");
                String bookDescr = resultSet.getString("description");
                System.out.println(bookName + " | " + bookDescr);
            }
        }
    }

    public static void selectAllByName (String name) throws SQLException, IOException {
        Properties properties = getConnectionProperties();

        try (Connection connection = DriverManager.getConnection(
                properties.getProperty("db.url"),
                properties.getProperty("db.username"),
                properties.getProperty("db.password"))) {

            final String sql = "select * from book where name=?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String bookName = resultSet.getString("name");
                String bookDescr = resultSet.getString("description");
                System.out.println(bookName + " | " + bookDescr);
            }
        }
    }

    public static void save () throws IOException, SQLException {
        Properties properties = getConnectionProperties();

        try (Connection connection = DriverManager.getConnection(
                properties.getProperty("db.url"),
                properties.getProperty("db.username"),
                properties.getProperty("db.password"))) {

            final String sql = "insert into  book (description, name, releas_year) values (?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, "Programming");
            preparedStatement.setString(2, "JavaBook");
            preparedStatement.setInt(3, 2008);

            Integer affectedRows = preparedStatement.executeUpdate();
            System.out.println("Was inserted " + affectedRows );

        }
    }

    public static void main(String[] args) throws IOException, SQLException {

       selectAll();
       System.out.println("*************************");

       selectAllByName("JJJJJ");
       System.out.println("*************************");

       //save();
       System.out.println("*************************");

       selectAll();
       System.out.println("*************************");

       Dao<Book> bookDao = new BookDao();
       Book book =  bookDao.findById(2L);
       System.out.println(book);

       book.setDescription("description after uptade");
       bookDao.update(book);

       System.out.println(bookDao.findById(2L));

       bookDao.delete(2L);


    }
}
