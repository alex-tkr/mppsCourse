package org.example.DBConnection;

import org.apache.commons.dbcp.BasicDataSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
            ds.setMinIdle(5);
            ds.setMaxIdle(10);
            ds.setMaxOpenPreparedStatements(100);
            ds.setUrl("jdbc:postgresql://localhost:5432/test1");
            ds.setUsername("postgres");
            ds.setPassword("root");
            ds.setConnectionProperties("useUnicode=yes;characterEncoding=utf8;");
    };

    public synchronized static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    private PostgresConnectionPool(){ }
}
