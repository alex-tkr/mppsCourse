package org.example.DAO;

import org.example.DBConnection.PostgresConnectionPool;
import org.example.models.Custom.TaskForAnalis;
import org.example.models.TaskAndProjectForEmail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoAnalisTaskOnProjectImpl extends UtilsForCon implements DaoAnalisTaskOnProject{


    String getTaskWithProjectForEmail="Select pt.id,pt.title,pt.description,pt.created_at,pt.update_at,pt.accountable_id,pp.name,pp.id,pt.update_user_id\n" +
            "from public.project_task pt\n" +
            "inner join project_project pp on pp.id = pt.project_id\n" +
            "where pt.id=?";
    String getTaskWithUser="Select pt.id,title,priority,project_id,update_user_id,status,uu.first_name,uu.last_name\n" +
            "from public.\"project_task\" pt\n" +
            "inner join \"project_project\" pp on pt.project_id = pp.id\n" +
            "inner join \"user_user\" uu on pt.accountable_id = uu.id\n" +
            "where project_id=?";

    @Override
    public List<TaskForAnalis> getTaskWithUser(int project_id) {
        List<TaskForAnalis> res= new ArrayList<>();
        TaskForAnalis tmp;
        Connection con= null;
        PreparedStatement prst=null;
        try {
            con = PostgresConnectionPool.getConnection();
            prst=con.prepareStatement(getTaskWithUser);
            prst.setInt(1,project_id);
            ResultSet resultSet = prst.executeQuery();
            while (resultSet.next()) {
                tmp=new TaskForAnalis();
                tmp.setId_task(resultSet.getInt("id"));
                tmp.setTitle(resultSet.getString("title"));
                tmp.setPriority(resultSet.getString("priority"));
                tmp.setProject_id(resultSet.getInt("project_id"));
                tmp.setUpdate_user_id(resultSet.getInt("update_user_id"));
                tmp.setStatus(resultSet.getString("status"));
                tmp.setFirst_name(resultSet.getString("first_name"));
                tmp.setLast_name(resultSet.getString("last_name"));
                res.add(tmp);
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
    public TaskAndProjectForEmail getTaskWithProjectForEmail(int task_id) {
        TaskAndProjectForEmail tmp=new TaskAndProjectForEmail();;
        Connection con= null;
        PreparedStatement prst=null;
        try {
            con = PostgresConnectionPool.getConnection();
            prst=con.prepareStatement(getTaskWithProjectForEmail);
            prst.setInt(1,task_id);
            ResultSet resultSet = prst.executeQuery();
            while (resultSet.next()) {
                tmp.setId_task(resultSet.getInt("id"));
                tmp.setTitle(resultSet.getString("title"));
                tmp.setDescription(resultSet.getString("description"));
                tmp.setCreated_at(resultSet.getString("created_at"));
                tmp.setUpdate_at(resultSet.getString("update_at"));
                tmp.setUpdate_user_id(resultSet.getInt("update_user_id"));
                tmp.setAccountable_id(resultSet.getInt("accountable_id"));
                tmp.setProject_id(resultSet.getInt("id"));
                tmp.setProject_name(resultSet.getString("name"));
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
        return tmp;
    }
}
