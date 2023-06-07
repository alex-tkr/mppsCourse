package org.example.services;

import org.example.DAO.*;
import org.example.models.*;
import org.example.models.Custom.TaskForAnalis;
import org.example.services.EmailSend.EmailSend;
import org.example.services.EmailSend.EmailSendNotification;


import java.util.List;

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


    public void sendEmailAppoined(int id_task){
        //get task
        TaskAndProjectForEmail task=new DaoAnalisTaskOnProjectImpl().getTaskWithProjectForEmail(id_task);
        //get address to
        String email=new DAOTeamsImplPostgres().getEmailOfMember(task.getAccountable_id());
        //send email
        new EmailSend().sendEmail(
                email,
                "HEY MAN",
                new EmailSendNotification().ConvertInMessageYouAssigned(
                        task.getProject_name(),
                        task.getTitle(),
                        task.getUpdate_at()
                )
        );
    }
    public void sendEmailTaskUpdate(int id_task,
                                    String old_status,
                                    String new_status,int id_user){
        TaskAndProjectForEmail task=new DaoAnalisTaskOnProjectImpl().getTaskWithProjectForEmail(id_task);
        if(task.getUpdate_user_id()!=id_user){
            String email=new DAOTeamsImplPostgres().getEmailOfMember(task.getAccountable_id());
            Member member=new DAOTeamsImplPostgres().getMemberWithName(task.getUpdate_user_id());
            //send email
            new EmailSend().sendEmail(
                    email,
                    "HEY MAN",
                    new EmailSendNotification().ConvertInMessageTaskChanged(
                            task.getProject_name(),
                            task.getTitle(),
                            task.getUpdate_at(),
                            old_status,
                            new_status,
                            member.getUsername()
                    )
            );
        }
    }
    public void sendEmailWithStatistic(int id_user,int id_project){
        //take email to
        String email=new DAOTeamsImplPostgres().getEmailOfMember(id_user);
        //take info about statistic from DAO
        List<TaskForAnalis> list=new DaoAnalisTaskOnProjectImpl().getTaskWithUser(id_project);
        Project project =new DaoAnalisTaskOnProjectImpl().getProjectById(id_project);
        TaskAnalis tasks=new TaskAnalis(list);
        tasks.setProjectName(project.getName());
        //use this method->
        new EmailSend().sendEmail(email,"Task Hub analysis tasks",tasks.getAnalByDoneNotDoneTaskInHtmlTable(id_project));
    }

    public void sendEmailWithStatisticInDiagram(int id_user,int id_project) {
        //take email to
        String email = new DAOTeamsImplPostgres().getEmailOfMember(id_user);
        //take info about statistic from DAO
        List<TaskForAnalis> list = new DaoAnalisTaskOnProjectImpl().getTaskWithUser(id_project);
        Project project = new DaoAnalisTaskOnProjectImpl().getProjectById(id_project);
        TaskAnalis tasks = new TaskAnalis(list);
        tasks.setProjectName(project.getName());
        //use this method->
        System.out.println(email);
        tasks.createDiagram();

        new EmailSend().sendEmailWithImage2(
                email,
                "Task Hub analysis tasks",
                tasks.getHeaderForProject(id_project),
                tasks.createDiagram());
    }

    }

