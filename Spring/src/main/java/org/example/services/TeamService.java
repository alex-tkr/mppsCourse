package org.example.services;

import org.example.DAO.DAOTeams;
import org.example.DAO.DAOTeamsImplPostgres;
import org.example.DBConnection.PostgresConnectionPool;
import org.example.models.Team;

import java.sql.Connection;
import java.sql.SQLException;

public class TeamService {

    public Team getTeam(int idTeam){
        DAOTeams db= new DAOTeamsImplPostgres();
        return db.getTeam(idTeam);
    }

    public void updateTeamInfo(Team team){
        DAOTeams db= new DAOTeamsImplPostgres();
        db.updateTeam(team.getName(),team.getDescription(),team.getId());
    }
    public void deleteTeam(int idTeam){
        DAOTeams db= new DAOTeamsImplPostgres();
        db.deleteTeam(idTeam);
    }


    public int createTeamByUser(Team team,int idMember, int role)  {
        DAOTeams db= new DAOTeamsImplPostgres();
        int idTeam=db.createTeam(team.getName(),team.getDescription());
        return idTeam;
    }


    public void addNewMemberInTeam(int idMember,int idTeam,int idRole){
        DAOTeams db= new DAOTeamsImplPostgres();
        db.updateTeamMember(idMember,idTeam);
        db.updateTeamMember(idMember,idRole);
    }
    public void updateMemberRole(int idMember,int idRole){
        DAOTeams db= new DAOTeamsImplPostgres();
        db.updateTeamMember(idMember,idRole);
    }
    public void updateTeam(int idMember,int idTeam){
        DAOTeams db= new DAOTeamsImplPostgres();
        db.updateTeamMember(idMember,idTeam);
    }
    public void kickMemberFromTeam(int idMember,int idRole){
        DAOTeams db= new DAOTeamsImplPostgres();
        db.updateTeamMember(idMember,null);
        db.updateRoleToMember(idMember,null);
    }


}
