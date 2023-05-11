package org.example.controllers;

import org.example.DAO.DAOTeamsImplPostgres;
import org.example.DBConnection.PostgresConnectionPool;
import org.example.models.Team;
import org.example.services.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("teams")
public class MainController {


    @GetMapping("/{id}")
    public Map<String , String> getTeam(@PathVariable int id){
        Team team= new TeamService().getTeam(id);
        return team.convertToMap();
    }


    @PostMapping("/createTeam")
    public Map<String , String> createTeam(@RequestBody Map<String,String> info){
        Team team =new Team();
        team.convertToTeam(info);
        int id=new TeamService().createTeamByUser(team,Integer.parseInt(info.get("idMember")),1);
        return new HashMap<String, String>(){
            {
                put("id",id+"");
            }
        };
    }
    @PostMapping("/updateTeam/{id}")
    public Map<String , String> updateTeam(@RequestBody Map<String,String> info,@PathVariable int id){
        Team team =new Team();
        team.convertToTeam(info);
        new TeamService().updateTeamInfo(team);
        return new HashMap<String, String>(){
            {
                put("id",id+"");
            }
        };
    }


    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable int id){
        new TeamService().deleteTeam(id);
    }

}
