package org.example.controllers;

import org.example.controllers.JWTParse.User.CustomAuthenticationProvider;
import org.example.controllers.JWTParse.User.CustomAuthenticationToken;
import org.example.models.Team;
import org.example.services.TeamService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@CrossOrigin(maxAge = 3600, allowCredentials = "false")
@RestController
@RequestMapping("/teams")
public class MainController extends TokenFuncImpl{

    @GetMapping("/test")
    public Map<String , String> Test(){
        System.out.println("toke 2"+getToken2());
        System.out.println("user "+getIdUserFromToken(getToken2()));
        return new HashMap<String, String>(){
            {
                put("id",5+"");
            }
        };
    }

    @GetMapping("/{id}")
    public Map<String , String> getTeam(@PathVariable int id){
        Team team= new TeamService().getTeam(id);
        return team.convertToMap();
    }

    @PostMapping("/createTeam")
    public Map<String , String> createTeam(@RequestBody Map<String,String> info){
        //check role without role
        Team team =new Team();
        team.convertToTeam(info);
        int id=new TeamService().createTeamByUser(team,Integer.parseInt(getIdUserFromToken(getToken2())));
        return new HashMap<String, String>(){
            {
                put("id",id+"");
            }
        };
    }

    @PostMapping("/updateTeam/{id}")
    public Map<String , String> updateTeam(@RequestBody Map<String,String> info,@PathVariable int id){
        //check role on admin and moder
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
        //check role on admin
        new TeamService().deleteTeam(id);
    }


    @PostMapping("/create_invite")
    public Map<String , String> createInvite(@RequestBody Map<String,String> info){
        if(info.get("id_team")==null||info.get("id_role")==null)
            return new HashMap<String, String>(){
                {
                    put("error",123+"");
                }
            };

        String hashcode=new TeamService().createInvitation(
                Integer.parseInt(info.get("id_team")),
                Integer.parseInt(info.get("id_role"))
        );
        return new HashMap<String, String>(){
            {
                put("hashcode",hashcode+"");
            }
        };
    }

    @PostMapping("/invite/{hashcode}")
    public void inviteMember(@PathVariable String hashcode){
        new TeamService(). inviteMember(hashcode,Integer.parseInt(getIdUserFromToken(getToken2())));
    }

    @DeleteMapping("/kikMember")
    public void kickMember(@RequestBody Map<String,String> info){
        if(info.get("id")!=null){
            new TeamService().kickMemberFromTeam(Integer.parseInt(info.get("id")));
        }
    }

    @PostMapping("/updateMemberRole")
    public void updateMemberRole(@RequestBody Map<String,String> info){
        if(info.get("id")!=null&&info.get("role")!=null){
            new TeamService().updateMemberRole(
                    Integer.parseInt(info.get("id")),
                    Integer.parseInt(info.get("role"))
            );
        }
    }



}
