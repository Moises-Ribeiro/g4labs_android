package g4.com.br.devandroid1;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class BancoDadosActivity extends AppCompatActivity implements View.OnClickListener, PessoaAdapter.OnItemClickListener {

    private EditText edNome;
    private EditText edIdade;

    private Button btnSalvar;
    private Button btnExcluir;

    private RecyclerView listPessoas;
    private PessoaAdapter adapter;

    private PessoaPersistence persistence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banco_dados);

        edNome = (EditText) findViewById(R.id.ed_nome);
        edIdade = (EditText) findViewById(R.id.ed_idade);

        btnSalvar = (Button) findViewById(R.id.btn_salvar);
        btnExcluir = (Button) findViewById(R.id.btn_excluir);

        listPessoas = (RecyclerView) findViewById(R.id.lst_pessoas);
        persistence = new PessoaPersistence(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        btnSalvar.setOnClickListener(this);
        btnExcluir.setOnClickListener(this);

        List<Pessoa> pessoas = persistence.getPessoas();
        adapter = new PessoaAdapter(pessoas, this);
        adapter.setOnItemClickListener(this);

        listPessoas.setAdapter(adapter);
        listPessoas.setHasFixedSize(true);
        listPessoas.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v) {
        if(v.equals(btnSalvar)) {
            Pessoa pessoa = new Pessoa();
            pessoa.setNome(edNome.getText().toString());
            pessoa.setIdade(Integer.parseInt(edIdade.getText().toString()));
            adapter.add(pessoa);

            persistence.salvar(pessoa);

        } else {
            Pessoa pessoa = new Pessoa();
            pessoa.setNome(edNome.getText().toString());
            pessoa.setIdade(Integer.parseInt(edIdade.getText().toString()));
            adapter.remove(pessoa);

            persistence.deletar(pessoa);
        }
    }

    @Override
    public void onItemClick(Pessoa pessoa) {
        edNome.setText(pessoa.getNome());
        edIdade.setText(String.valueOf(pessoa.getIdade()));
    }
}
