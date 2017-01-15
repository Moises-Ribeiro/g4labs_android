package g4.com.br.devandroid1;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Thiago on 15/01/2017.
 */

public class PessoaViewHolder extends RecyclerView.ViewHolder {

    final View view;

    final TextView tvNome;
    final TextView tvIdade;

    public PessoaViewHolder(View view) {
        super(view);

        this.view = view;

        tvNome = (TextView) view.findViewById(R.id.tv_nome);
        tvIdade = (TextView) view.findViewById(R.id.tv_idade);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        view.setOnClickListener(listener);
    }
}
