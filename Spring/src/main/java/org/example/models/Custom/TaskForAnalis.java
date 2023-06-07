package org.example.models.Custom;

public class TaskForAnalis {
    int id_task;
    String title;
    String priority;
    int project_id;
    int update_user_id;
    String status;
    Boolean comleted;
    String last_name;
    String first_name;


    public int getId_task() {
        return id_task;
    }

    public void setId_task(int id_task) {
        this.id_task = id_task;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public int getUpdate_user_id() {
        return update_user_id;
    }

    public void setUpdate_user_id(int update_user_id) {
        this.update_user_id = update_user_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getComleted() {
        return comleted;
    }

    public void setComleted(Boolean comleted) {
        this.comleted = comleted;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    @Override
    public String toString() {
        return "TaskForAnalis{" +
                "id_task=" + id_task +
                ", title='" + title + '\'' +
                ", priority='" + priority + '\'' +
                ", project_id=" + project_id +
                ", update_user_id=" + update_user_id +
                ", status='" + status + '\'' +
                ", comleted=" + comleted +
                ", last_name='" + last_name + '\'' +
                ", first_name='" + first_name + '\'' +
                '}';
    }


}
