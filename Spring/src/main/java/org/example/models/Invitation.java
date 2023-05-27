package org.example.models;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Invitation {
    int id;
    String hashcode;
    int team;
    int role;
    boolean active;
    int idInv;

    public int getIdInv() {
        return idInv;
    }

    public void setIdInv(int idInv) {
        this.idInv = idInv;
    }

    public Invitation() {
    }

    public static String generatHashcode(){
        char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new SecureRandom();
        for (int i = 0; i < 10; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        return output ;
    }
    public Invitation(int id, String hashcode, int team, int role,boolean active) {
        this.id = id;
        this.hashcode = hashcode;
        this.team = team;
        this.role = role;
        this.active=active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHashcode() {
        return hashcode;
    }

    public void setHashcode(String hashcode) {
        this.hashcode = hashcode;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Map<String,String> convertToMap(){
        Map<String , String> result =new HashMap<>();
        result.put("id",this.getId()+"");
        result.put("hashcode",this.getHashcode()+"");
        result.put("team",this.getTeam()+"");
        result.put("role",this.getRole()+"");
        result.put("active",this.isActive()+"");
        return result;
    }

    public Invitation convertToInvitation( Map<String , String> data){
        this.id=data.get("id")==null?0:Integer.parseInt(data.get("id"));
        this.hashcode=data.get("hashcode")==null?"":data.get("hashcode");
        this.team=data.get("team")==null?0:Integer.parseInt(data.get("team"));
        this.role=data.get("role")==null?0:Integer.parseInt(data.get("role"));
        this.active=data.get("isActive")==null?false:Boolean.parseBoolean(data.get("isActive"));
        return this;
    }
}
