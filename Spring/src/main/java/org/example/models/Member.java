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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
