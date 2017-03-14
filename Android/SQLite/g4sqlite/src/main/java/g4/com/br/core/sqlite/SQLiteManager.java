package g4.com.br.core.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Arrays;

/**
 * Created by Thiago on 28/02/2017.
 */

class SQLiteManager extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Tasks.db";

    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String SQL: Arrays.asList(TaskDAO.CREATE)){
            db.execSQL(SQL);
        }
    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        for (String SQL: Arrays.asList(TaskDAO.DROP)){
            db.execSQL(SQL);
        }
        onCreate(db);
    }

    @Override
    public void onDowngrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    protected Cursor search(String table, String[] columns, String selection,
                            String[] selectionArgs, String groupBy, String having,
                            String orderBy, String limit) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    protected Cursor search(String query, String... args) {
        Cursor cursor = null;
        SQLiteDatabase database = getReadableDatabase();
        cursor = database.rawQuery(query, args);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        database.close();
        return cursor;
    }

    protected long save(String table, ContentValues values) {
        SQLiteDatabase database = getReadableDatabase();
        long uuid = database.insert(table, null, values);
        database.close();
        return uuid;
    }

    protected long update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase database = getReadableDatabase();
        long rows = database.update(table, values, whereClause, whereArgs);
        database.close();
        return rows;
    }
}
