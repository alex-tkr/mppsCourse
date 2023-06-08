package DAO;

import org.example.DAO.DaoAnalisTaskOnProjectImpl;
import org.example.models.Custom.TaskForAnalis;
import org.example.models.Task;
import org.example.models.TaskAndProjectForEmail;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DaoAnalisTaskOnProjectImplTest {

    @Test
    void getTaskWithUser() {
        DaoAnalisTaskOnProjectImpl daocheck = new DaoAnalisTaskOnProjectImpl();
        System.out.println(daocheck.getTaskWithUser(1));

    }
    @Test
    void EqualsgetTaskWithUser() {
        List<TaskForAnalis> task2 =new ArrayList<TaskForAnalis>();
        task2.add(new TaskForAnalis(1,"Task #2","1",1,1,"1","arte","ate"));
        DaoAnalisTaskOnProjectImpl daocheck = new DaoAnalisTaskOnProjectImpl();
        List<TaskForAnalis> task3= daocheck.getTaskWithUser(1);
        assertEquals(task2,task3);
    }


    @Test
    void EqualsgetTaskWithProjectForEmail() {
        Task task1=new Task();
        task1.setId_task(1);
        task1.setProject_id(1);
        task1.setTitle("Task #2");
        task1.setAccountable_id(1);
        task1.setDescription("Do...");
        task1.setCreated_at("2022-03-05 00:00:00+02");
        task1.setUpdate_at("2022-05-20 00:00:00+03");
        task1.setUpdate_user_id(1);
        DaoAnalisTaskOnProjectImpl daocheck=new DaoAnalisTaskOnProjectImpl();
        TaskAndProjectForEmail task2= daocheck.getTaskWithProjectForEmail(1);
        assertEquals(task1.getId_task(),task2.getId_task());
        assertEquals(task1.getProject_id(),task2.getProject_id());
        assertEquals(task1.getTitle(),task2.getTitle());
        assertEquals(task1.getAccountable_id(),task2.getAccountable_id());
        assertEquals(task1.getDescription(),task2.getDescription());
        assertEquals(task1.getCreated_at(),task2.getCreated_at());
        assertEquals(task1.getUpdate_at(),task2.getUpdate_at());
        assertEquals(task1.getUpdate_user_id(),task2.getUpdate_user_id());
    }
}