package DAO;

import org.apache.tomcat.jni.User;
import org.example.DAO.DAOTeamsImplPostgres;
import org.example.DBConnection.PostgresConnectionPool;
import org.example.models.Member;
import org.example.models.Team;
import org.glassfish.grizzly.memory.MemoryManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.example.DBConnection.PostgresConnectionPool;
import org.example.models.Member;
import org.example.models.Team;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.List;
class DAOTeamsImplPostgresTest {

    @Test
    void checkCreateTeam() {
        DAOTeamsImplPostgres daocheck = new DAOTeamsImplPostgres();
        int id= daocheck.createTeam("dsadasdsa", "dsadsa");
        System.out.println(daocheck.getTeam(id));
        daocheck.deleteTeam(id);
    }

    @Test
    void equalsOfCreatedTeam() {
    DAOTeamsImplPostgres daocheck = new DAOTeamsImplPostgres();
    Team a = daocheck.getTeam(8);
    Team team1 = new Team(8,"aguzok","dsadasdsa","2023-05-20 00:30:23.925578+03");
    Assertions.assertEquals(team1.getId(), a.getId());
    Assertions.assertEquals(team1.getName(), a.getName());
    Assertions.assertEquals(team1.getDescription(), a.getDescription());
    Assertions.assertEquals(team1.getDate_create(), a.getDate_create());
}

    @Test
    void deleteTeam() {
        DAOTeamsImplPostgres daocheck = new DAOTeamsImplPostgres();
        daocheck.deleteTeam(6);
        assertNull(daocheck.getTeam(6));
    }

    @Test
    void getTeam() {
        DAOTeamsImplPostgres daocheck = new DAOTeamsImplPostgres();
        System.out.println(daocheck.getTeam(37));
    }

    @Test
    void updateTeam() {
        Team team1 = new Team(12,"Team12","Cool","2023-05-20 00:49:00.012524+03");
        DAOTeamsImplPostgres daocheck = new DAOTeamsImplPostgres();
        daocheck.updateTeam("Team12","Cool",12);
        Team a = daocheck.getTeam(12);
        Assertions.assertEquals(team1.getId(), a.getId());
        Assertions.assertEquals(team1.getName(), a.getName());
        Assertions.assertEquals(team1.getDescription(), a.getDescription());
        Assertions.assertEquals(team1.getDate_create(), a.getDate_create());
    }


    @Test
    void NotEqualsMemberAfterUpdateTeamMember() {
        Member user1 = new Member(1,1,1);
        DAOTeamsImplPostgres daocheck= new DAOTeamsImplPostgres();
        daocheck.updateTeamMember(1,37);
        Member user2 = daocheck.getMemberWithRoleTeam(1);
        assertNotEquals(user1.getTeam(), user2.getTeam());
    }
    @Test
    void EqualsMemberAfterUpdateTeamMember() {
        Member user1 = new Member(1,1,8);
        DAOTeamsImplPostgres daocheck= new DAOTeamsImplPostgres();
        daocheck.updateTeamMember(1,8);
        Member user2 = daocheck.getMemberWithRoleTeam(1);
        assertEquals(user1.getTeam(), user2.getTeam());
    }

    @Test
    void NotEqualsMemberAfterUpdateRoleToMember() {
        Member user1 = new Member(1,1,1);
        DAOTeamsImplPostgres daocheck= new DAOTeamsImplPostgres();
        daocheck.updateRoleToMember(1,3);
        Member user2 = daocheck.getMemberWithRoleTeam(1);
        assertNotEquals(user1.getRole(),user2.getRole());

    }

    @Test
    void EqualsGetMemberWithRoleTeam() {
        Member user1 = new Member (1,3,8);
        DAOTeamsImplPostgres daocheck = new DAOTeamsImplPostgres();
        Member user2 = daocheck.getMemberWithRoleTeam(1);
        assertEquals(user1.getId(),user2.getId());
        assertEquals(user1.getRole(),user2.getRole());
        assertEquals(user1.getTeam(),user2.getTeam());

    }

    @Test
    void NotEqualsGetMemberWithRoleTeam() {
        Member user1 = new Member (2,2,7);
        DAOTeamsImplPostgres daocheck = new DAOTeamsImplPostgres();
        Member user2 = daocheck.getMemberWithRoleTeam(1);
        assertNotEquals(user1.getId(),user2.getId());
        assertNotEquals(user1.getRole(),user2.getRole());
        assertNotEquals(user1.getTeam(),user2.getTeam());
    }

    @Test
    void EqualsGetEmailOfMember() {
        Member user1 = new Member();
        user1.setId(1);
        user1.setEmail("emelyazemelya2003@gmail.com");
        user1.setRole(2);
        DAOTeamsImplPostgres daocheck = new DAOTeamsImplPostgres();
        assertEquals(user1.getEmail(),daocheck.getEmailOfMember(1));
    }

    @Test
    void getMemberWithName() {
        Member user1= new Member();
        user1.setId(1);
        user1.setUsername("arte");
        DAOTeamsImplPostgres daocheck =new DAOTeamsImplPostgres();
        Member user2 =daocheck.getMemberWithName(1);
        assertEquals(user1.getId(), user2.getId());
        assertEquals(user1.getUsername(), user2.getUsername());

    }
}