package org.example.DBConnection;

import org.apache.commons.dbcp.BasicDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class PostgresConnectionPool {

    private static  BasicDataSource ds;


    public synchronized static BasicDataSource getDataSource(){
        if (ds==null){
            ds=new BasicDataSource();
            setProperties();
        }
        return ds;
    }
    public static void setProperties(){
        String profile="";
        try (InputStream input = PostgresConnectionPool.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            profile=prop.getProperty("spring.profiles.active");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try (InputStream input =PostgresConnectionPool.class.getClassLoader().getResourceAsStream("application-"+profile+".properties")) {
            Properties prop = new Properties();
            prop.load(input);
            ds.setUrl(prop.getProperty("db.url"));
            ds.setUsername(prop.getProperty("db.username"));
            ds.setPassword(prop.getProperty("db.password"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
            ds.setMinIdle(5);
            ds.setMaxIdle(10);
            ds.setMaxOpenPreparedStatements(100);
            ds.setConnectionProperties("useUnicode=yes;characterEncoding=utf8;");
    };

    public synchronized static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    private PostgresConnectionPool(){ }
}
