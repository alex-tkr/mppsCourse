package org.example.DAO;

import org.example.DBConnection.PostgresConnectionPool;
import org.example.models.Member;
import org.example.models.Role;
import org.example.models.Team;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.List;

public class DAOTeamsImplPostgres extends UtilsForCon implements DAOTeams{

    private final String getTeam="Select * FROM public.\"API_team\" WHERE id=?";
    private final String createTeam="INSERT INTO public.\"API_team\" (name,description,created_at) VALUES (?,?,?) RETURNING id";
    private final String deleteTeam="DELETE FROM public.\"API_team\" WHERE id=?";

    private final String updateUserRole="Update public.\"user_user\" set role=? WHERE id=?";
    private final String updateUserTeam="Update public.\"user_user\" set team_id=? WHERE id=?";
    private final String updateTeam="Update public.\"API_team\" set name=?,description=? WHERE id=?";
    private final String getMemberWithRoleTeam="Select role,team_id FROM public.\"user_user\" WHERE id=?";

    @Override
    public int createTeam(String name, String description) {
        Connection con= null;
        PreparedStatement prst=null;
        int generatedKey = 0;
        try {
            con = PostgresConnectionPool.getConnection();
            prst=con.prepareStatement(createTeam, Statement.RETURN_GENERATED_KEYS);
            prst.setString(1,name);
            prst.setString(2,description);
            prst.setObject(3,OffsetDateTime.now());
            prst.executeUpdate();
            ResultSet rs = prst.getGeneratedKeys();
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            System.out.println(generatedKey);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                closePrepareState(prst);
                closeCon(con);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return generatedKey;
    }

    @Override
    public void deleteTeam(int id) {
        Connection con= null;
        PreparedStatement prst=null;
        try {
            con = PostgresConnectionPool.getConnection();
            prst=con.prepareStatement(deleteTeam);
            prst.setInt(1,id);
            prst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                closePrepareState(prst);
                closeCon(con);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public Team getTeam(int id) {
        Team res= null;
        Connection con= null;
        PreparedStatement prst=null;
        try {
            con = PostgresConnectionPool.getConnection();
            prst=con.prepareStatement(getTeam);
            prst.setInt(1,id);
            ResultSet resultSet = prst.executeQuery();
            while (resultSet.next()) {
                res= new Team(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
            }
        } catch (SQLException e) {
        } finally {
            try {
               closePrepareState(prst);
               closeCon(con);
            } catch (SQLException e) {
            }
        }
        return res;
    }

    @Override
    public void updateTeam(String name,String description, int id) {
        Connection con= null;
        PreparedStatement prst=null;
        try {
            con = PostgresConnectionPool.getConnection();
            prst=con.prepareStatement(updateTeam);
            prst.setString(1,name);
            prst.setString(2,description);
            prst.setInt(3,id);
            prst.executeUpdate();
        } catch (SQLException e) {

        } finally {
            try {
                closePrepareState(prst);
                closeCon(con);
            } catch (SQLException e) {

            }
        }
    }

    @Override
    public void updateTeamMember(int idMember, Integer idTeam) {
        Connection con= null;
        PreparedStatement prst=null;
        try {
            con = PostgresConnectionPool.getConnection();
            prst=con.prepareStatement(updateUserTeam);
            prst.setObject(1,idTeam);
            prst.setInt(2, idMember);
            prst.executeUpdate();

        } catch (SQLException e) {

        } finally {
            try {
                closePrepareState(prst);
                closeCon(con);
            } catch (SQLException e) {

            }
        }
    }
    @Override
    public void updateRoleToMember(int idMember, Integer idRole) {
        Connection con= null;
        PreparedStatement prst=null;
        try {
            con = PostgresConnectionPool.getConnection();
            prst=con.prepareStatement(updateUserRole);
            prst.setString(1,idRole==null?"":idRole.toString());
            prst.setInt(2, idMember);
            prst.executeUpdate();
        } catch (SQLException e) {

        } finally {
            try {
                closePrepareState(prst);
                closeCon(con);
            } catch (SQLException e) {

            }
        }
    }

    @Override
    public Member getMemberWithRoleTeam(int id_member) {
        Member res= null;
        Connection con= null;
        PreparedStatement prst=null;
        try {
            con = PostgresConnectionPool.getConnection();
            prst=con.prepareStatement(getMemberWithRoleTeam);
            prst.setInt(1,id_member);
            ResultSet resultSet = prst.executeQuery();
            while (resultSet.next()) {
                res= new Member();
                res.setId(id_member);
                res.setRole(resultSet.getInt("role"));
                res.setTeam(resultSet.getInt("team_id"));
            }
        } catch (SQLException e) {
        } finally {
            try {
                closePrepareState(prst);
                closeCon(con);
            } catch (SQLException e) {
            }
        }
        return res;
    }


}
