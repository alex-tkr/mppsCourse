package org.example.DAO;

import org.example.models.TaskForAnalis;

import java.util.List;

public interface DaoAnalisTaskOnProject {

    List<TaskForAnalis> getTaskWithUser(int project_id);
}
