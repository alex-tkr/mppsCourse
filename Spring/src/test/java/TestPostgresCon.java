
import org.example.DAO.DAOTeamsImplPostgres;
import org.example.DBConnection.PostgresConnectionPool;
import org.example.models.Member;
import org.example.models.Role;
import org.example.models.Team;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.*;


import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestPostgresCon {

    @Test
    public void testGetTems() throws SQLException {

        Connection connection= PostgresConnectionPool.getConnection();
        Statement sta= null;
        try {
            sta = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet table=sta.executeQuery("SELECT * FROM public.\"API_team\"");
            table.beforeFirst();
            while(table.next()){
                for(int i=1;i<=table.getMetaData().getColumnCount();i++){
                    System.out.print(table.getString(i)+"\t\t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testCreateTeam() throws SQLException {
        new DAOTeamsImplPostgres().createTeam("hjtrtytyj","descrip");
        //testGetTems();
    }



    @Test
    public void testDeleteTeam() throws SQLException {
        new DAOTeamsImplPostgres().deleteTeam(2);
    }
    @Test
    public void testgetTeam() throws SQLException {
        Team teamExpexted=new Team(4,"test1","rtjtyjk","2023-04-03 22:59:24.122993+03");
        Team team = new DAOTeamsImplPostgres().getTeam(4);

    }

    @Test
    public void test1(){

    }

    @Test
    public void testUpdateUserTeam(){
        Member mem= new Member();
        mem.setId(5);
        new DAOTeamsImplPostgres().updateTeamMember(5,10);
        mem.setId(5);
        new DAOTeamsImplPostgres().updateTeamMember(5,null);
    }





}
