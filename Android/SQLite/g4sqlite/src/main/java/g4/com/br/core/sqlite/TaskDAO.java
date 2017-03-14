package g4.com.br.core.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by Thiago on 28/02/2017.
 */

public class TaskDAO extends SQLiteManager {
    private static final String TABLE = "task";

    protected static final String CREATE = String.format("CREATE TABLE IF NOT EXISTS %s (id  INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR(50), completed INTEGER, description VARCHAR(150));", TABLE);
    protected static final String DROP = String.format("DROP TABLE IF EXISTS %s", TABLE);

    public TaskDAO(Context context) {
        super(context);
    }

    public List<HashMap<String, String>> getTasks() {
        List<HashMap<String, String>> tasks = null;
        Cursor cursor =  search(TABLE, null, null, null, null, null, null, null);
        if (cursor != null) {
            tasks = new ArrayList<HashMap<String, String>>();
            do {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("id", cursor.getString(cursor.getColumnIndex("id")));
                hm.put("title", cursor.getString(cursor.getColumnIndex("title")));
                hm.put("completed", cursor.getString(cursor.getColumnIndex("completed")));
                hm.put("description", cursor.getString(cursor.getColumnIndex("description")));
                tasks.add(hm);
            } while (cursor.moveToNext());
        }
        return tasks;
    }

    public HashMap<String, String> getTask() {
        HashMap<String, String> task = null;
        Cursor cursor =  search("SELECT * FROM task");
        if (cursor != null) {
            task = new HashMap<String, String>();
            task.put("id", cursor.getString(cursor.getColumnIndex("id")));
            task.put("title", cursor.getString(cursor.getColumnIndex("title")));
            task.put("completed", cursor.getString(cursor.getColumnIndex("completed")));
            task.put("description", cursor.getString(cursor.getColumnIndex("description")));
        }
        return task;
    }

    public int saveTask(HashMap<String, String> task) {
        int uuid = -1;
        if (task != null && !task.isEmpty()) {
            ContentValues values = new ContentValues();
            Set<String> keys = task.keySet();
            for (String key: keys) {
                values.put(key, task.get(key));
            }
            uuid = (int) save(TABLE, values);
        }
        return uuid;
    }

    public boolean updateTask(HashMap<String, String> task) {
        boolean updated = false;
        if (task != null && !task.isEmpty()) {
            ContentValues values = new ContentValues();
            Set<String> keys = task.keySet();
            for (String key: keys) {
                values.put(key, task.get(key));
            }

            String id = null;
            if (values.containsKey("id")) {
                id = values.getAsString("id");
                values.remove("id");
            }

            updated = update(TABLE, values, "id=?", new String[] {id}) > 0;
        }
        return updated;
    }
}
