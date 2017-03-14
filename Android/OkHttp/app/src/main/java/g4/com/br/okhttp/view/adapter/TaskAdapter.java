package g4.com.br.sqlite.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import g4.com.br.sqlite.R;
import g4.com.br.sqlite.model.Task;
import g4.com.br.sqlite.view.holder.TaskHolder;

/**
 * Created by Thiago on 28/02/2017.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

    private final List<Task> tasks;

    public TaskAdapter() {
        this.tasks = new ArrayList<Task>();
    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_tasks, parent, false);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskHolder holder, int position) {
        holder.onBindViewHolder(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks != null ? tasks.size() : 0;
    }

    public void addAll(List<Task> tasks) {
        for (Task task:tasks) {
            this.tasks.add(task);
        }
        notifyDataSetChanged();
    }
}
