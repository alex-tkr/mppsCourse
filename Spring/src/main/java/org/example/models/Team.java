package org.example.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Team {
    public Team(int id, String name, String description, String date_create) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date_create = date_create;
    }

    int id;
    String name;
    String description;
    String date_create;
    boolean status;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_create() {
        return date_create;
    }

    public void setDate_create(String date_create) {
        this.date_create = date_create;
    }

    public Team() {
    }


    public Team(String name, String description, String date_create) {
        this.name = name;
        this.description = description;
        this.date_create = date_create;
    }

    public boolean equals(Team obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Team other = (Team) obj;
        return id == other.id &&
                Objects.equals(name, other.name) &&
                Objects.equals(description, other.description) &&
                Objects.equals(date_create, other.date_create);
    }

    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ", name:'" + name + '\'' +
                ", description:'" + description + '\'' +
                ", date_create:'" + date_create + '\'' +
                '}';
    }

    public Map<String,String> convertToMap(){
        Map<String , String> result =new HashMap<>();
        result.put("id",this.getId()+"");
        result.put("name",this.getName()+"");
        result.put("description",this.getDescription()+"");
        result.put("date",this.getDate_create()+"");
        return result;
    }

    public Team convertToTeam( Map<String , String> data){
        this.id=data.get("id")==null?0:Integer.parseInt(data.get("id"));
        this.name=data.get("name")==null?"":data.get("name");
        this.description=data.get("description")==null?"":data.get("description");
        this.date_create=data.get("date_create")==null?"":data.get("date_create");
        return this;
    }

}
