package org.example.models;

public class Member {
    int id;
    String password;
    String last_login;
    boolean is_superuser;
    String username;
    String first_name;
    String last_name;
    String email;
    boolean is_staff;
    boolean is_active;
    String date_joined;
    int role;
    int team;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Member() {
    }

    public Member(int id, int role, int team) {
        this.id = id;
        this.role = role;
        this.team = team;
    }

    public int getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role=role==null?0:role;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(Integer team) {
        this.team=team==null?0:team;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}
