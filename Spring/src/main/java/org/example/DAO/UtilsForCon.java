package org.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UtilsForCon {
    public void closeCon(Connection con) throws SQLException {
        if(con!=null){
            con.close();
        }
    }
    public void closePrepareState(PreparedStatement prst) throws SQLException {
        if(prst!=null){
            prst.close();
        }
    }
}
