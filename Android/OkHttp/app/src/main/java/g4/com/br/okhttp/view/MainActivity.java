package g4.com.br.sqlite.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import g4.com.br.sqlite.R;
import g4.com.br.sqlite.controller.TaskController;
import g4.com.br.sqlite.model.Task;
import g4.com.br.sqlite.view.adapter.TaskAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rvTasks;
    private TaskAdapter taskAdapter;

    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rvTasks = (RecyclerView) findViewById(R.id.rv_tasks);
        rvTasks.setHasFixedSize(true);
        rvTasks.setLayoutManager(new LinearLayoutManager(this));

        taskAdapter = new TaskAdapter();
        rvTasks.setAdapter(taskAdapter);

        btnAdd = (Button) findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        searchTasks().execute();
    }

    @Override
    public void onClick(View v) {
        if (btnAdd.equals(v)) {
            Intent intent = new Intent(this, AddTaskActivity.class);
            startActivityForResult(intent, 99);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 99) {
            if (resultCode == RESULT_OK) {
                searchTasks().execute();
            }
        }
    }

    private AsyncTask<Void, Void, Boolean> searchTasks() {
        return new AsyncTask<Void,Void,Boolean>(){
            private List<Task> tasks;
            private Exception err;

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    tasks = new TaskController(getBaseContext()).getTasks();
                    return true;
                } catch (Exception e) {
                    err = e;
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if (success) {
                    taskAdapter.addAll(tasks);
                    Toast.makeText(getBaseContext(), "Achouuu...", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getBaseContext(), err.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        };
    }
}
