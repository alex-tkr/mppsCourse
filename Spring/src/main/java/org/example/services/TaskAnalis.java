package org.example.services;

import org.example.models.Custom.TaskForAnalis;
import org.example.services.Diagrams.PieChart;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class TaskAnalis {
    private List<TaskForAnalis> list;
    private String  projectName;


    private Map<String,Integer> convertInMapTaskAndUserAndStatus(List<TaskForAnalis> list){
        Map<String,Integer> result=new HashMap<>();
        for(TaskForAnalis e:list){
            String status;
            if(e.getComleted()){
                status="done";
            }else{
                status="in work";
            }
           String name= e.getLast_name()+" "+e.getFirst_name()+":"+status;
           if(result.containsKey(name)==false){
               result.put(name,1);
           }else{
              int a= result.get(name);
              result.remove(name);
              result.put(name,a+1);
           }
        }


        return result;
    }
    private String convertInTableHtmlTaskAndUserAndStatus(List<TaskForAnalis> list){
        String result="";
        result+="<table>";
        result+="<tr>" +
                "<th>Task</th>\n" +
                "<th>Worker</th>\n" +
                "<th>Status</th>\n" +
                " </tr>\n";
        for(TaskForAnalis e:list){
            result+="<tr>";
            result+="<td>"+e.getTitle()+"</td>";
            result+="<td>"+e.getLast_name()+" "+e.getFirst_name()+"</td>";
            if(Integer.parseInt(e.getStatus())==5){
                result+="<td>"+"Done"+"</td>";
            }else{
                result+="<td>"+"In work"+"</td>";
            }
            result+="</tr>";
        }
        result+="</table>";
        return result;
    }

    public String getAnalByDoneNotDoneTaskInHtmlTable(int id_project){
        String res="<h1>Tasks for "+projectName+"</h1>";
        List<TaskForAnalis> list1= new ArrayList<>(list);
        List<TaskForAnalis> result = list1.stream()
                .filter(e -> e.getProject_id()==id_project)
                .sorted(new TaskComparatorByStatus())
                .collect(Collectors.toList());
        res+=convertInTableHtmlTaskAndUserAndStatus(result);
        return res;
    }


    public String createDiagram(){
        try {
            new PieChart(projectName).createPngFile(
                    convertInMapTaskAndUserAndStatus(list),
                    projectName
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        new PieChart(projectName,convertInMapTaskAndUserAndStatus(list));
        return projectName+".jpg";
    }
    public String getHeaderForProject(int id_project)  {
        String res="<h1>Tasks for "+projectName+"</h1>";

        //res+="<img src=\"/"+projectName+".jpeg\">";
        return res;
    }

    public TaskAnalis() {
    }


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public TaskAnalis(List<TaskForAnalis> list) {
        this.list = list;
    }

    public List<TaskForAnalis> getList() {
        return list;
    }

    public void setList(List<TaskForAnalis> list) {
        this.list = list;
    }

    public class TaskComparatorByTittle implements Comparator<TaskForAnalis> {
        public int compare(TaskForAnalis a, TaskForAnalis b){
            return a.getTitle().toUpperCase().compareTo(b.getTitle().toUpperCase());
        }
    }
    public class TaskComparatorByStatus implements Comparator<TaskForAnalis> {
        public int compare(TaskForAnalis a, TaskForAnalis b){
            return b.getStatus().toUpperCase().compareTo(a.getStatus().toUpperCase());
        }
    }
}
