package org.example.DAO;

import org.example.models.Custom.TaskForAnalis;
import org.example.models.TaskAndProjectForEmail;

import java.util.List;

public interface DaoAnalisTaskOnProject {

    List<TaskForAnalis> getTaskWithUser(int project_id);

    TaskAndProjectForEmail getTaskWithProjectForEmail(int task_id);
}
