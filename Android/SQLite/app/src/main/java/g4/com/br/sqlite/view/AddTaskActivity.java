package g4.com.br.sqlite.view;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import g4.com.br.sqlite.R;
import g4.com.br.sqlite.controller.TaskController;
import g4.com.br.sqlite.model.Task;

public class AddTaskActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etTaskID;
    private EditText etTitle;
    private SeekBar sbCompleted;
    private EditText etDescription;

    private Button btnConfirm;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        etTaskID = (EditText) findViewById(R.id.et_id);
        etTitle = (EditText) findViewById(R.id.et_title);
        sbCompleted = (SeekBar) findViewById(R.id.sb_completed);
        etDescription = (EditText) findViewById(R.id.et_description);

        btnConfirm = (Button) findViewById(R.id.btn_confirm);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        btnConfirm.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        //sbCompleted.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        if (btnConfirm.equals(v)) {
            submitTask();
        } else if (btnCancel.equals(v)){
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        this.setResult(RESULT_CANCELED, getIntent());
        finish();
    }

    private void submitTask() {
        Task task = new Task();
        String taskID = etTaskID.getText().toString();
        if (taskID != null && !"".equals(taskID)) {
            task.setId(Integer.parseInt(taskID));
        }

        task.setTitle(etTitle.getText().toString());
        task.setCompleted(sbCompleted.getProgress());
        task.setDescription(etDescription.getText().toString());

        persisteTask().execute(task);
    }

    private AsyncTask<Task, Void, Boolean> persisteTask() {
        return new AsyncTask<Task, Void, Boolean>() {

            private ProgressBar bar;

            private Task task;
            private Exception err;

            @Override
            protected void onPreExecute() {
                bar = (ProgressBar) findViewById(R.id.pb_progress);
                bar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Boolean doInBackground(Task... params) {
                try {
                    task = new TaskController(getBaseContext()).save(params[0]);
                    return true;
                } catch (Exception e) {
                    err = e;
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                bar.setVisibility(View.GONE);

                if (success) {
                    AddTaskActivity.this.setResult(RESULT_OK, getIntent());
                    finish();
                    Toast.makeText(getBaseContext(), "Salvo com sucesso!!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getBaseContext(), "Falhouuu", Toast.LENGTH_LONG).show();
                }
            }
        };
    }
}
