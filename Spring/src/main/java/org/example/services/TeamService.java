package org.example.services;

import org.example.DAO.DAOTeams;
import org.example.DAO.DAOTeamsImplPostgres;
import org.example.DAO.DaoInvitation;
import org.example.DAO.DaoInvitationImpl;
import org.example.models.Invitation;
import org.example.models.Member;
import org.example.models.RoleOnSite;
import org.example.models.Team;

public class TeamService {

    public Team getTeam(int idTeam){
        DAOTeams db= new DAOTeamsImplPostgres();
        return db.getTeam(idTeam);
    }
    public void updateTeamInfo(Team team){
        DAOTeams db= new DAOTeamsImplPostgres();
        System.out.println(team.toString());
        db.updateTeam(team.getName(),team.getDescription(),team.getId());
    }
    public void deleteTeam(int idTeam){
        DAOTeams db= new DAOTeamsImplPostgres();
        db.deleteTeam(idTeam);
    }
    public int createTeamByUser(Team team, int id_user)  {
        DAOTeams db= new DAOTeamsImplPostgres();
        int idTeam=db.createTeam(team.getName(),team.getDescription());
        updateMemberRole(id_user, RoleOnSite.ADMIN);
        updateMemberTeam(id_user,idTeam);
        return idTeam;
    }

    public String createInvitation(int id_team,int id_role,int id_inv){
        Invitation invitation =new Invitation();
        invitation.setTeam(id_team);
        invitation.setRole(id_role);
        invitation.setIdInv(id_inv);
        invitation.setActive(false);
        invitation.setHashcode(Invitation.generatHashcode());
        return new DaoInvitationImpl().createInvite(invitation);
    };

    public String inviteMember(String hashcode,int member){
        //check member on team
        DAOTeams daoTeams =new DAOTeamsImplPostgres();
        Member member1=daoTeams.getMemberWithRoleTeam(member);
        Invitation invitation=null;
        if(member1.getTeam()==0) {
            DaoInvitation daoInv = new DaoInvitationImpl();
             invitation = daoInv.getInviteByHashCode(hashcode);
            if(invitation!=null&&invitation.getIdInv()==member1.getId())
            {
                updateMemberTeam(member, invitation.getTeam());
                updateMemberRole(member, invitation.getRole());
                daoInv.changeStatusInvite(invitation.getId(), true);
            }
        }
        Member member2=daoTeams.getMemberWithRoleTeam(member);
        if(invitation==null){
            return "cant get invit;";
        }
        if(member2.getTeam()!=invitation.getTeam()){
            return "member not added;";
        }
        return "all is good;";
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
        db.updateRoleToMember(idMember,0);
    }

}
