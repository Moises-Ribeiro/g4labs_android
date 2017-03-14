package g4.com.br.sqlite.view.holder;

import android.content.Context;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import g4.com.br.sqlite.R;
import g4.com.br.sqlite.model.Task;

/**
 * Created by Thiago on 28/02/2017.
 */

public class TaskHolder extends RecyclerView.ViewHolder {

    private Context context;

    private TextView nameView;
    private ProgressBar completedView;

    public TaskHolder(View view) {
        super(view);

        context = view.getContext();
        nameView = (TextView) view.findViewById(R.id.tv_name);
        completedView = (ProgressBar) view.findViewById(R.id.pb_completed);
    }

    public void onBindViewHolder(Task entity) {
        nameView.setText(entity.getName());
        completedView.setProgress(entity.getCompleted());
    }
}
