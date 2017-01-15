package g4.com.br.devandroid1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by Thiago on 15/01/2017.
 */

public abstract class PersistenceHelper extends SQLiteOpenHelper {

    private static final String BANCO = "G4LAB";
    private static final int VERSAO = 1;

    public PersistenceHelper(Context context){
        super(context, BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        List<String> scripts = onCreateScript();
        if (scripts != null) {
            for (String script : scripts) {
                db.execSQL(script);
            }
        }
    }

    public abstract List<String> onCreateScript();

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            List<String> scripts = onUpgradeScript();
            if (scripts != null) {
                for (String script : scripts) {
                    db.execSQL(script);
                }
            }
            onCreate(db);
        }
    }

    public abstract List<String> onUpgradeScript();
}
