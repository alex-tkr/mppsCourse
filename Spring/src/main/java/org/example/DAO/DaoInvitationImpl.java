package org.example.DAO;

import org.example.DBConnection.PostgresConnectionPool;
import org.example.models.Invitation;
import org.example.models.Team;

import java.sql.*;
import java.time.OffsetDateTime;

public class DaoInvitationImpl extends UtilsForCon implements DaoInvitation{
    private final String createInvite="INSERT INTO public.\"Invitations_teams\" (id_team,hash_invite,role,active) VALUES (?,?,?,?) RETURNING hash_invite";
    private final String updateInviteStatus="Update public.\"Invitations_teams\" set active=? WHERE id=?";
    private final String updateInvitedInInvite="Update public.\"Invitations_teams\" set id_inv_id=? WHERE id=?";
    private final String getInviteByHash="Select * FROM public.\"Invitations_teams\" WHERE hash_invite=?";
    private final String getInviteById="Select * FROM public.\"Invitations_teams\" WHERE id=?";
    @Override
    public String createInvite(Invitation invitation) {
        Connection con= null;
        PreparedStatement prst=null;
        String generatedKey = null;
        try {
            con = PostgresConnectionPool.getConnection();
            prst=con.prepareStatement(createInvite, Statement.RETURN_GENERATED_KEYS);
            prst.setInt(1,invitation.getTeam());
            prst.setString(2, invitation.getHashcode());
            prst.setInt(3, invitation.getRole());
            prst.setBoolean(4, invitation.isActive());
            prst.executeUpdate();
            ResultSet rs = prst.getGeneratedKeys();
            if (rs.next()) {
                generatedKey = rs.getString(1);
            }
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
    public void changeStatusInvite(int id_invite, boolean status) {
        Connection con= null;
        PreparedStatement prst=null;
        try {
            con = PostgresConnectionPool.getConnection();
            prst=con.prepareStatement(updateInviteStatus);
            prst.setBoolean(1,status);
            prst.setInt(2,id_invite);
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
    public void changeInvitedInInvite(int id_invite, int id_member) {
        Connection con= null;
        PreparedStatement prst=null;
        try {
            con = PostgresConnectionPool.getConnection();
            prst=con.prepareStatement(updateInvitedInInvite);
            prst.setInt(1,id_member);
            prst.setInt(2,id_invite);
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
    public Invitation getInviteByHashCode(String hashcode) {
        Invitation res= null;
        Connection con= null;
        PreparedStatement prst=null;
        try {
            con = PostgresConnectionPool.getConnection();
            prst=con.prepareStatement(getInviteByHash);
            prst.setString(1,hashcode);
            ResultSet resultSet = prst.executeQuery();
            while (resultSet.next()) {
                res= new Invitation(resultSet.getInt("id"),
                        resultSet.getString("hash_invite"),
                        resultSet.getInt("id_team"),
                        resultSet.getInt("role"),
                        resultSet.getBoolean("active")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
    public Invitation getInviteById(int id) {
        Invitation res= null;
        Connection con= null;
        PreparedStatement prst=null;
        try {
            con = PostgresConnectionPool.getConnection();
            prst=con.prepareStatement(getInviteById);
            prst.setInt(1,id);
            ResultSet resultSet = prst.executeQuery();
            while (resultSet.next()) {
                res= new Invitation(resultSet.getInt("id"),
                        resultSet.getString("hash_invite"),
                        resultSet.getInt("id_team"),
                        resultSet.getInt("role"),
                        resultSet.getBoolean("active")
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


}
