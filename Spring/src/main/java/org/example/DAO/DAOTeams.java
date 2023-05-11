package org.example.DAO;

import org.example.models.Member;
import org.example.models.Team;

import java.util.List;

public interface DAOTeams {

     int createTeam(String name, String description);
     void deleteTeam(int id);
     Team getTeam(int id);
     void updateTeam(String name,String description, int id);

     void updateTeamMember(int idMember, Integer idTeam);
     void updateRoleToMember(int idMember, Integer idRole);

     List<Member> getAllMembers(int idTeam);
}
