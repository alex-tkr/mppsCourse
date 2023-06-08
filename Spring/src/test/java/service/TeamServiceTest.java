package service;

import org.example.DAO.DAOTeamsImplPostgres;
import org.example.DAO.DaoInvitationImpl;
import org.example.models.Team;
import org.example.models.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamServiceTest {

    @Test
    void EqualsGetTeam() {
    Team team1= new Team();
    team1.setId(42);
    team1.setName("hjtrtytyj");
    team1.setDescription("descrip");
    team1.setDate_create("2023-06-01 17:14:26.436179+03");
    TeamService team_test= new TeamService();
    Team team2 = team_test.getTeam(42);
    assertEquals(team1.getId(),team2.getId());
    assertEquals(team1.getName(),team2.getName());
    assertEquals(team1.getDescription(),team2.getDescription());
    assertEquals(team1.getDate_create(),team2.getDate_create());
    }

    @Test
    void CheckTeamAfterUpdateTeamInfo() {
        Team team1 = new Team();
        team1.setId(42);
        team1.setDescription("Wow team");
        team1.setName("Team #42");
        TeamService test=new TeamService();
        test.updateTeamInfo(team1);
        Team team_test=test.getTeam(42);
        Team team2 = new Team();
        team2.setId(42);
        team2.setDescription("Wow team");
        team2.setName("Team #42");
        assertEquals(team2.getId(),team_test.getId());
        assertEquals(team2.getName(),team_test.getName());
        assertEquals(team2.getDescription(),team_test.getDescription());
    }


    @Test
    void EqualTeamAfterCreateTeamByUser() {
        Team team1_test = new Team();
        team1_test.setId(78);
        team1_test.setName("Team Best");
        team1_test.setDescription("Best of the best");
        TeamService test_service= new TeamService();
        test_service.createTeamByUser(team1_test,2);
        Team team2_test = test_service.getTeam(78);
        assertEquals(team1_test.getId(),team2_test.getId());
        assertEquals(team1_test.getDescription(),team2_test.getDescription());
        assertEquals(team1_test.getName(),team2_test.getName());
    }

    @Test
    void createInvitation() {
        Invitation invite_1 = new Invitation();
        invite_1.setId(2);
        invite_1.setTeam(77);
        invite_1.setRole(1);
        invite_1.setIdInv(1);
        invite_1.setActive(false);
        TeamService invite_test= new TeamService();
        invite_test.createInvitation(77,1,1);
        DaoInvitationImpl daocheck = new DaoInvitationImpl();
        Invitation test= daocheck.getInviteById(2);
        invite_1.setHashcode(test.getHashcode());
        assertEquals(invite_1.getId(), test.getId());
        assertEquals(invite_1.getTeam(), test.getTeam());
        assertEquals(invite_1.getHashcode(), test.getHashcode());
        assertEquals(invite_1.getRole(), test.getRole());

    }

    @Test
    void inviteMember() {
        DAOTeamsImplPostgres test2 = new DAOTeamsImplPostgres();
        Member test_member = test2.getMemberWithRoleTeam(3);
        TeamService invite_test= new TeamService();
        String id =invite_test.createInvitation(77,1,test_member.getId());
        DaoInvitationImpl test1 = new DaoInvitationImpl();
        Invitation test_inv = test1.getInviteByHashCode(id);
        TeamService test_service = new TeamService();
        test_service.inviteMember(test_inv.getHashcode(),test_member.getId());
        Member test_member2 = test2.getMemberWithRoleTeam(3);
        assertEquals(test_inv.getTeam(),test_member2.getTeam());
        assertEquals(test_inv.getRole(),test_member2.getRole());
    }


    @Test
    void updateMemberRole() {
        Member member_test = new Member();
        member_test.setId(3);
        member_test.setRole(2);
        TeamService service_test = new TeamService();
        service_test.updateMemberRole(member_test.getId(),2);
        DAOTeamsImplPostgres daocheck = new DAOTeamsImplPostgres();
        Member member_from_db = daocheck.getMemberWithRoleTeam(member_test.getId());
        assertEquals(member_test.getId(),member_from_db.getId());
        assertEquals(member_test.getRole(),member_from_db.getRole());
    }

    @Test
    void updateMemberTeam() {
        Member member_test = new Member();
        member_test.setId(3);
        member_test.setTeam(77);
        TeamService service_test = new TeamService();
        service_test.updateMemberTeam(member_test.getId(),77);
        DAOTeamsImplPostgres daocheck = new DAOTeamsImplPostgres();
        Member member_from_db = daocheck.getMemberWithRoleTeam(member_test.getId());
        assertEquals(member_test.getId(),member_from_db.getId());
        assertEquals(member_test.getTeam(),member_from_db.getTeam());
    }

    @Test
    void kickMemberFromTeam() {
         Member member_test = new Member();
         member_test.setId(3);
         member_test.setRole(0);
         member_test.setTeam(null);
         TeamService test_service = new TeamService();
         test_service.kickMemberFromTeam(member_test.getId());
         DAOTeamsImplPostgres daocheck = new DAOTeamsImplPostgres();
         Member member_from_db = daocheck.getMemberWithRoleTeam(member_test.getId());
         assertEquals(member_test.getId(),member_from_db.getId());
         assertEquals(member_test.getRole(),member_from_db.getRole());
         assertEquals(member_test.getTeam(),member_from_db.getTeam());
    }


}