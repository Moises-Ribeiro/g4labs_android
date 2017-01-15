package g4.com.br.devandroid1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thiago on 15/01/2017.
 */

public class PessoaPersistence extends PersistenceHelper {

    public PessoaPersistence(Context context) {
        super(context);
    }

    @Override
    public List<String> onCreateScript() {
        ArrayList<String> tables = new ArrayList<>();
        tables.add("CREATE TABLE PESSOA (_id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, idade INTEGER)");
        return tables;
    }

    @Override
    public List<String> onUpgradeScript() {
        ArrayList<String> drops = new ArrayList<>();
        drops.add("DROP TABLE IF EXISTS PESSOA");
        return drops;
    }

    public List<Pessoa> getPessoas() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM PESSOA", null);

        List<Pessoa> result = new ArrayList<Pessoa>();
        if (cursor.moveToFirst()) {
            do {

                int indexID = cursor.getColumnIndex("_id");
                int indexMarca = cursor.getColumnIndex("nome");
                int indexModelo = cursor.getColumnIndex("idade");

                int id = cursor.getInt(indexID);
                String nome = cursor.getString(indexMarca);
                int idade = cursor.getInt(indexModelo);

                Pessoa pessoa = new Pessoa();
                pessoa.setId(id);
                pessoa.setNome(nome);
                pessoa.setIdade(idade);
                result.add(pessoa);

            } while (cursor.moveToNext());
        }
        return result;
    }

    public boolean salvar(Pessoa pessoa) {
        SQLiteDatabase database = getWritableDatabase();

        String TABELA = "PESSOA";

        ContentValues values = new ContentValues();
        values.put("nome", pessoa.getNome());
        values.put("idade", pessoa.getIdade());

        long result = database.insert(TABELA, null, values);
        return result != -1;
    }

    public boolean editar(Pessoa pessoa) {
        SQLiteDatabase database = getWritableDatabase();

        String TABELA = "PESSOA";

        ContentValues values = new ContentValues();
        values.put("nome", pessoa.getNome());
        values.put("idade", pessoa.getIdade());

        long result = database.update(TABELA, values, "_id=?", new String[] {String.valueOf(pessoa.getId())});
        return result > 0;
    }

    public boolean deletar(Pessoa pessoa) {
        SQLiteDatabase database = getWritableDatabase();

        String TABELA = "PESSOA";
        long result = database.delete(TABELA, "_id=?", new String[] {String.valueOf(pessoa.getId())});
        return result > 0;
    }

    public List<Pessoa> getPessoasByFilter(Pessoa pessoa) {
        SQLiteDatabase database = getReadableDatabase();
        String TABELA = "PESSOA";

        Cursor cursor = database.query(false, TABELA, new String[] {"_id", "nome", "idade"},
                "nome like ?", new String[] {pessoa.getNome()}, null, null, null, null);

        List<Pessoa> result = new ArrayList<Pessoa>();
        if (cursor.moveToFirst()) {
            do {

                int indexID = cursor.getColumnIndex("_id");
                int indexMarca = cursor.getColumnIndex("nome");
                int indexModelo = cursor.getColumnIndex("idade");

                int id = cursor.getInt(indexID);
                String nome = cursor.getString(indexMarca);
                int idade = cursor.getInt(indexModelo);

                Pessoa pes = new Pessoa();
                pes.setId(id);
                pes.setNome(nome);
                pes.setIdade(idade);
                result.add(pes);

            } while (cursor.moveToNext());
        }
        return result;
    }
}
