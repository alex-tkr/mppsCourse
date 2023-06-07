package org.example.DAO;

import org.example.DBConnection.PostgresConnectionPool;
import org.example.models.Invitation;

import java.sql.*;

public class DaoInvitationImpl extends UtilsForCon implements DaoInvitation{
    private final String createInvite="INSERT INTO public.\"project_invite_team\" (id_team_id,hash_code,role,accept,id_inv_id) VALUES (?,?,?,?,?) RETURNING hash_code";
    private final String updateInviteStatus="Update public.\"project_invite_team\" set accept=? WHERE id=?";
    private final String updateInvitedInInvite="Update public.\"project_invite_team\" set id_inv_id=? WHERE id=?";
    private final String getInviteByHash="Select * FROM public.\"project_invite_team\" WHERE hash_code=?";
    private final String getInviteById="Select * FROM public.\"project_invite_team\" WHERE id=?";
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
            prst.setInt(5, invitation.getIdInv());
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
                res= new Invitation() ;
                res.setId(resultSet.getInt("id"));
                res.setHashcode(resultSet.getString("hash_code"));
                res.setTeam(resultSet.getInt("id_team_id"));
                res.setRole( resultSet.getInt("role"));
                res.setActive( resultSet.getBoolean("accept"));
                 res.setIdInv(resultSet.getInt("id_inv_id"));
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
                        resultSet.getString("hash_code"),
                        resultSet.getInt("id_team_id"),
                        resultSet.getInt("role"),
                        resultSet.getBoolean("accept")
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
