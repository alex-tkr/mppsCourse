package org.example.DBConnection;

import java.sql.*;
import java.util.Properties;

public class PostgressSimplCon {
    private final String url = "jdbc:postgresql://localhost:port/test1";
    private final String user = "postgres";
    private final String password = "<add your password>";

    public static Connection getConnection(){
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/test1";
            Properties authorization=new Properties();
            authorization.put("user","postgres");
            authorization.put("password","root");

            Connection con= DriverManager.getConnection(url,authorization);
            return con;


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
