package org.example.services;

import org.example.DAO.DAOTeams;
import org.example.DAO.DAOTeamsImplPostgres;
import org.example.DAO.DaoInvitation;
import org.example.DAO.DaoInvitationImpl;
import org.example.models.Invitation;
import org.example.models.Team;

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
    public int createTeamByUser(Team team)  {
        DAOTeams db= new DAOTeamsImplPostgres();
        int idTeam=db.createTeam(team.getName(),team.getDescription());
        return idTeam;
    }

    public String createInvitation(int id_team,int id_role){
        Invitation invitation =new Invitation();
        invitation.setTeam(id_team);
        invitation.setRole(id_role);
        invitation.setActive(true);
        invitation.setHashcode(Invitation.generatHashcode());
        return new DaoInvitationImpl().createInvite(invitation);
    };

    public void inviteMember(String hashcode,int member){
        //check member on team
        DaoInvitation daoInv=new DaoInvitationImpl();
        Invitation invitation =daoInv.getInviteByHashCode(hashcode);

        updateMemberTeam(member,invitation.getTeam());
        updateMemberRole(member,invitation.getRole());
        daoInv.changeStatusInvite(invitation.getId(),false);
    };

    public void getInvitationById(int id){};
    public void addNewMemberInTeam(int idMember,int idTeam,int idRole){
        DAOTeams db= new DAOTeamsImplPostgres();
        db.updateRoleToMember(idMember,idTeam);
        db.updateTeamMember(idMember,idRole);
    }
    public void updateMemberRole(int idMember,int idRole){
        DAOTeams db= new DAOTeamsImplPostgres();
        db.updateRoleToMember(idMember,idRole);
    }
    public void updateMemberTeam(int idMember,int idTeam){
        DAOTeams db= new DAOTeamsImplPostgres();
        db.updateTeamMember(idMember,idTeam);
    }
    public void kickMemberFromTeam(int idMember){
        DAOTeams db= new DAOTeamsImplPostgres();
        db.updateTeamMember(idMember,null);
        db.updateRoleToMember(idMember,null);
    }

}
