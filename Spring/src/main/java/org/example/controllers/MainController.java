package org.example.controllers;

import org.example.DAO.DAOTeamsImplPostgres;
import org.example.controllers.JWTParse.User.CustomAuthenticationProvider;
import org.example.controllers.JWTParse.User.CustomAuthenticationToken;
import org.example.models.Member;
import org.example.models.Team;
import org.example.services.TeamService;
import org.example.services.TelegramBot.BotConfig;
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
        // https://api.telegram.org/botTOKEN/sendMessage?chat_id=CHAT_ID&text=TEXT
        // return "redirect:/getUser.do";
        return new HashMap<String, String>(){
            {
                put("id",5+"");
            }
        };
    }

    @PostMapping("/test_telegram")
    public String TestTelegram(BotConfig botConfig,@RequestBody Map<String,String> info){
       String chatId= info.get("chatId");
       System.out.println(botConfig.getToken());
        return "redirect:https://api.telegram.org/"+botConfig.getToken()+"/sendMessage?chat_id="+chatId+"&text=TEXT ";
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
        Member member= new DAOTeamsImplPostgres().getMemberWithRoleTeam(getIdUserFromTokenInt(getToken2()));
        if(member.getRole()==1){
            Team team =new Team();
            team.convertToTeam(info);
            new TeamService().updateTeamInfo(team);
            return new HashMap<String, String>(){
                {
                    put("id",id+"");
                }
            };
        }
        return new HashMap<String, String>(){
            {
                put("error","error");
            }
        };
    }

    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable int id){
        Member member= new DAOTeamsImplPostgres().getMemberWithRoleTeam(getIdUserFromTokenInt(getToken2()));
        if(member.getRole()==1){
            new TeamService().deleteTeam(id);
        }

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
        if(info.get("id")!=null&&getIdUserFromTokenInt(getToken2())<=2){
            Member member= new DAOTeamsImplPostgres().getMemberWithRoleTeam(getIdUserFromTokenInt(getToken2()));
            if(member.getRole()==1||member.getRole()==2){
                new TeamService().kickMemberFromTeam(Integer.parseInt(info.get("id")));
            }
        }
    }

    @PostMapping("/updateMemberRole")
    public void updateMemberRole(@RequestBody Map<String,String> info){
        if(info.get("id")!=null&&info.get("role")!=null){
            Member member= new DAOTeamsImplPostgres().getMemberWithRoleTeam(getIdUserFromTokenInt(getToken2()));
            if(member.getRole()==1||member.getRole()==2){
                new TeamService().updateMemberRole(
                        Integer.parseInt(info.get("id")),
                        Integer.parseInt(info.get("role"))
                );
            }
        }
    }



}
