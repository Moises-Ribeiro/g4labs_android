package g4.com.br.devandroid2;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CacheActivity extends AppCompatActivity implements View.OnClickListener, PessoaAdapter.OnItemClickListener {

    private EditText edNome;
    private EditText edIdade;

    private Button btnSalvar;
    private Button btnExcluir;

    private ProgressBar progressBar;

    private RecyclerView listPessoas;
    private PessoaAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banco_dados);

        edNome = (EditText) findViewById(R.id.ed_nome);
        edIdade = (EditText) findViewById(R.id.ed_idade);

        btnSalvar = (Button) findViewById(R.id.btn_salvar);
        btnExcluir = (Button) findViewById(R.id.btn_excluir);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        listPessoas = (RecyclerView) findViewById(R.id.lst_pessoas);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        btnSalvar.setOnClickListener(this);
        btnExcluir.setOnClickListener(this);

        adapter = new PessoaAdapter(this);
        adapter.setOnItemClickListener(this);
        listPessoas.setAdapter(adapter);
        listPessoas.setHasFixedSize(true);
        listPessoas.setLayoutManager(new LinearLayoutManager(this));



        new AsyncTask<Void, Void, List<Pessoa>>() {

            @Override
            protected void onPreExecute() {
                //ProgressDialog dialog = ProgressDialog.show(CacheActivity.this, "G4Labs",
                //        "Carregando. Por favor aguarde...", true);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected List<Pessoa> doInBackground(Void... params) {
                return new PessoaRemote().getPessoas();
            }

            @Override
            protected void onPostExecute(List<Pessoa> pessoas) {
                if (pessoas != null && pessoas.size() > 0) {
                    for (Pessoa pessoa: pessoas){
                        adapter.add(pessoa);
                    }
                }
                progressBar.setVisibility(View.GONE);
            }
        }.execute();
    }

    @Override
    public void onClick(View v) {
        if(v.equals(btnSalvar)) {
            Pessoa pessoa = new Pessoa();
            pessoa.setNome(edNome.getText().toString());
            pessoa.setIdade(Integer.parseInt(edIdade.getText().toString()));
            submit(pessoa);

        } else {
            Pessoa pessoa = new Pessoa();
            pessoa.setNome(edNome.getText().toString());
            pessoa.setIdade(Integer.parseInt(edIdade.getText().toString()));
            adapter.remove(pessoa);
        }
    }

    @Override
    public void onItemClick(Pessoa pessoa) {
        edNome.setText(pessoa.getNome());
        edIdade.setText(String.valueOf(pessoa.getIdade()));

        btnExcluir.setVisibility(View.VISIBLE);
    }


    private void submit(final Pessoa pessoa) {
        new AsyncTask<Pessoa, Void, Pessoa>() {

            @Override
            protected void onPreExecute() {
                //ProgressDialog dialog = ProgressDialog.show(CacheActivity.this, "G4Labs",
                //        "Carregando. Por favor aguarde...", true);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Pessoa doInBackground(Pessoa... params) {
                try {
                    return new PessoaRemote().submitPessoa(params[0]);
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Pessoa pessoa) {
                if (pessoa != null) {
                    adapter.add(pessoa);
                }
                progressBar.setVisibility(View.GONE);
            }
        }.execute(pessoa);
    }


    private class PessoaRemote {
        private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        private final String URL = "http://192.168.0.81:8080/pessoas";

        private OkHttpClient client;

        private  PessoaRemote(){
            client = new OkHttpClient();
        }

        private List<Pessoa> getPessoas() {
            List<Pessoa> pessoas = null;

            Request request = new Request.Builder()
                    .url(URL)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String body = response.body().string();

                JSONObject json = new JSONObject(body);

                JSONArray array = json.getJSONArray("lista");
                pessoas = new ArrayList<Pessoa>();

                for (int i = 0, size = array.length(); i < size; i++) {
                    JSONObject obj = array.getJSONObject(i);

                    Pessoa pessoa = new Pessoa();
                    pessoa.setNome(obj.getString("nome"));
                    pessoa.setIdade(obj.getInt("idade"));
                    pessoas.add(pessoa);
                }

            }catch (Exception e) {
            }

            return pessoas;
        }

        private Pessoa submitPessoa(Pessoa pessoa) throws Exception {
            JSONObject json = new JSONObject();
            int id = pessoa.getId();

            if (id != 0) {
                json.put("id", pessoa.getNome());
            }

            json.put("nome", pessoa.getNome());
            json.put("idade", pessoa.getIdade());

            //json.toString() => dumps
            RequestBody body = RequestBody.create(JSON, json.toString());
            Request request = null;

            if (id == 0) {
                request = new Request.Builder()
                        .url(URL)
                        .post(body)
                        .build();
            } else {
                request = new Request.Builder()
                        .url(URL)
                        .put(body)
                        .build();
            }

            Response response = client.newCall(request).execute();

            String resp = response.body().string();

            // => loads
            JSONObject obj = new JSONObject(resp);

            pessoa.setId(obj.getInt("id"));
            pessoa.setNome(obj.getString("nome"));
            pessoa.setIdade(obj.getInt("idade"));

            return pessoa;
        }
    }
}
