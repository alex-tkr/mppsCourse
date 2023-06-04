package org.example.services;

import org.example.models.Custom.TaskForAnalis;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TaskAnalis {
    private List<TaskForAnalis> list;

    private String ConvertInTableHtmlTaskAndUserAndStatus(List<TaskForAnalis> list){
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
        String res="<h1>Tasks</h1>";
        List<TaskForAnalis> list1= new ArrayList<>(list);
        List<TaskForAnalis> result = list1.stream()
                .filter(e -> e.getProject_id()==id_project)
                .sorted(new TaskComparatorByStatus())
                .collect(Collectors.toList());
        res+=ConvertInTableHtmlTaskAndUserAndStatus(result);
        return res;
    }

    public TaskAnalis() {
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
