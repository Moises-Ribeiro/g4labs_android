package g4.com.br.sqlite.model;

import java.util.HashMap;

/**
 * Created by Thiago on 28/02/2017.
 */

public class Task {

    private int id;
    private String title;
    private int completed;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Task toObject(HashMap<String, String> hm) {
        Task task = new Task();
        task.setId(Integer.parseInt(hm.get("id")));
        task.setTitle(hm.get("title"));
        task.setCompleted(Integer.parseInt(hm.get("completed")));
        task.setDescription(hm.get("description"));
        return task;
    }

    public HashMap<String, String> toHash() {
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("id", String.valueOf(getId()));
        hm.put("title", getTitle());
        hm.put("completed", String.valueOf(getCompleted()));
        hm.put("description", getDescription());
        return hm;
    }
}
