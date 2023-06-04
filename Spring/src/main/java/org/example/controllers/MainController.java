package org.example.controllers;

import org.example.DAO.DAOTeamsImplPostgres;
import org.example.DAO.DaoAnalisTaskOnProjectImpl;
import org.example.models.Member;
import org.example.models.RoleOnSite;
import org.example.models.Custom.TaskForAnalis;
import org.example.models.Team;
import org.example.services.TeamService;
import org.example.services.TelegramBot.BotConfig;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = {"http://localhost"})
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
        Team team =new Team();
        team.convertToTeam(info);
        int id=new TeamService().createTeamByUser(team,getIdUserFromTokenInt(getToken2()));
        return new HashMap<String, String>(){
            {
                put("id",id+"");
            }
        };
    }

    @PostMapping("/updateTeam/{id}")
    public Map<String , String> updateTeam(@RequestBody Map<String,String> info,@PathVariable int id){
        Member member= new DAOTeamsImplPostgres().getMemberWithRoleTeam(getIdUserFromTokenInt(getToken2()));
        if(member.getRole()==RoleOnSite.ADMIN||member.getRole()==RoleOnSite.MANAGER){
            Team team =new Team();
            team.convertToTeam(info);
            team.setId(id);
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
        if(member.getRole()==RoleOnSite.ADMIN){
            new TeamService().deleteTeam(id);
        }

    }


    @PostMapping("/create_invite")
    public Map<String , String> createInvite(@RequestBody Map<String,String> info){
        Member user= new DAOTeamsImplPostgres().getMemberWithRoleTeam(getIdUserFromTokenInt(getToken2()));
        if(
                info.get("id_team")==null||
                info.get("id_role")==null||
                info.get("id_inv_id")==null
        )
            return new HashMap<String, String>(){
                {
                    put("error","value of data not found");
                }
            };
        if(user.getRole()==RoleOnSite.ADMIN||user.getRole()==RoleOnSite.MANAGER){
            String hashcode=new TeamService().createInvitation(
                    Integer.parseInt(info.get("id_team")),
                    Integer.parseInt(info.get("id_role")),
                    Integer.parseInt(info.get("id_inv_id"))
            );
            return new HashMap<String, String>(){
                {
                    put("hashcode",hashcode+"");
                }
            };
        }
        return new HashMap<String, String>(){
            {
                put("error","you not admin of manager");
            }
        };

    }

    @PostMapping("/invite/{hashcode}")
    public Map<String , String> inviteMember(@PathVariable String hashcode){
        return new HashMap<String, String>(){
            {
                put("error",new TeamService(). inviteMember(hashcode,getIdUserFromTokenInt(getToken2())));
            }
        };
    }

    @DeleteMapping("/kikMember")
    public void kickMember(@RequestBody Map<String,String> info){
        if(info.get("id")!=null){
            Member member= new DAOTeamsImplPostgres().getMemberWithRoleTeam(getIdUserFromTokenInt(getToken2()));
            if(member.getRole()==RoleOnSite.ADMIN||member.getRole()==RoleOnSite.MANAGER){
                new TeamService().kickMemberFromTeam(Integer.parseInt(info.get("id")));
            }
        }
    }

    @PostMapping("/updateMemberRole")
    public void updateMemberRole(@RequestBody Map<String,String> info){
        if(info.get("id")!=null&&info.get("role")!=null){
            Member member= new DAOTeamsImplPostgres().getMemberWithRoleTeam(getIdUserFromTokenInt(getToken2()));
            if(member.getRole()== RoleOnSite.ADMIN ||member.getRole()==RoleOnSite.MANAGER){
                new TeamService().updateMemberRole(
                        Integer.parseInt(info.get("id")),
                        Integer.parseInt(info.get("role"))
                );
            }
        }
    }


    @PostMapping("/statisticTasksEmail")
    public void sendAnalOnEmail(@RequestBody Map<String,String> info){
        if(info.get("id_project")!=null){
            new TeamService().sendEmailWithStatistic(
                    getIdUserFromTokenInt(getToken2()),
                    Integer.parseInt(info.get("id_project")));
        }
    }
    @PostMapping("/statisticTasksEmailTest")
    public Map<String , String> sendAnalOnEmailTest(@RequestBody Map<String,String> info){
        List<TaskForAnalis> list=new DaoAnalisTaskOnProjectImpl().getTaskWithUser(3);
        return new HashMap<String, String>(){
            {
                put("size",list.size()+"");
            }
        };
    }

    @PostMapping("/sendEmailAppointed")
    public void sendEmailAppointed(@RequestBody Map<String,String> info){
        if(info.get("id_task")!=null){
            new TeamService().sendEmailAppoined(
                    Integer.parseInt(info.get("id_task")));
        }

    }



}




