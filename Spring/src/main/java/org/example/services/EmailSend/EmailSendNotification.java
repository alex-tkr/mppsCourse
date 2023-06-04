package org.example.services.EmailSend;

public class EmailSendNotification {

    public String ConvertInMessageYouAssigned(String project_name, String task_name,String date){
        String result="";
        result+="<h1>You were assigned to "+project_name+" task: "+task_name+"</h1>";
        result+="<h3>Date change:<b>"+date+"</b></h3>";
        return result;
    }
    public String ConvertInMessageTaskChanged(String project_name,
                                               String task_name,
                                               String date,
                                               String last_status,
                                               String new_status,String byWHy) {
        String result="";
        result+="<h1>The task to which you have assigned has been changed by "+byWHy+ "</h1>";
        result+="<h3>Project name: <b>"+project_name+"</b></h3>";
        result+="<h3>Task name: <b>"+task_name+"</b></h3>";
        result+="<h3>Old status: <b>"+last_status+"</b></h3>";
        result+="<h3>New status: <b>"+new_status+"</b></h3>";
        result+="<h3>Date change:<b>"+date+"</b></h3>";
        return result;
    }


}
