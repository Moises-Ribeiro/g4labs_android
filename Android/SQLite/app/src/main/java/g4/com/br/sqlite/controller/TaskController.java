package g4.com.br.sqlite.controller;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import g4.com.br.core.sqlite.TaskDAO;
import g4.com.br.sqlite.model.Task;

/**
 * Created by Thiago on 28/02/2017.
 */

public class TaskController {

    private TaskDAO dao;

    public TaskController(Context context) {
        dao = new TaskDAO(context);
    }

    public List<Task> getTasks() throws Exception {
        List<Task> tasks = null;
        List<HashMap<String, String>> ts = dao.getTasks();
        if (ts != null && !ts.isEmpty()) {
            tasks = new ArrayList<Task>();
            for (HashMap<String, String> hm : ts) {
                tasks.add(Task.toObject(hm));
            }
        }
        return tasks;
    }

    public Task getTask() throws Exception {
        Task task = null;
        HashMap<String, String> hm = dao.getTask();
        if (hm != null && !hm.isEmpty()) {
            task = Task.toObject(hm);
        }
        return task;
    }

    public Task save(Task task) throws Exception {
        if (task != null) {
            int uuid = dao.saveTask(task.toHash());
            task.setId(uuid);
        } else {
            throw new IllegalArgumentException("task is null");
        }
        return task;
    }

    public boolean update(Task task) throws Exception {
        if (task != null) {
            dao.updateTask(task.toHash());
        } else {
            throw new IllegalArgumentException("task is null");
        }
        return true;
    }
}
