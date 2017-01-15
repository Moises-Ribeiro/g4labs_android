package g4.com.br.devandroid2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thiago on 15/01/2017.
 */

public class PessoaAdapter extends RecyclerView.Adapter<PessoaViewHolder> {

    private List<Pessoa> pessoas;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Pessoa pessoa);
    }

    public PessoaAdapter(Context context) {
        this(new ArrayList<Pessoa>(), context);
    }

    public PessoaAdapter(List<Pessoa> pessoas, Context context) {
        this.context = context;
        this.pessoas = pessoas;
    }


    @Override
    public PessoaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pessoa, parent, false);
        PessoaViewHolder holder = new PessoaViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PessoaViewHolder holder, int position) {
        final Pessoa pessoa = pessoas.get(position);

        //bind do holder ou fazer individualmente como abaixo
        holder.tvNome.setText(pessoa.getNome());
        holder.tvIdade.setText(String.valueOf(pessoa.getIdade()));
        if (listener != null)
            holder.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    listener.onItemClick(pessoa);
                }
            });

    }

    @Override
    public int getItemCount() {
        return pessoas != null ? pessoas.size() : 0;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void add(Pessoa pessoa) {
        this.pessoas.add(pessoa);
        notifyDataSetChanged();
    }

    public void remove(Pessoa pessoa) {
        for (Pessoa pes: pessoas) {
            if (pes.equals(pessoa)) {
                pessoa = pes;
                break;
            }
        }

        this.pessoas.remove(pessoa);
        notifyDataSetChanged();
    }
}
