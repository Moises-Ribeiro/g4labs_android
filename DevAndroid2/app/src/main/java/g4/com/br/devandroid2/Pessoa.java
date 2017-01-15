package g4.com.br.devandroid2;

/**
 * Created by Thiago on 15/01/2017.
 */

public class Pessoa {

    private int id;
    private String nome;
    private int idade;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = false;

        if (obj != null && obj instanceof Pessoa) {
            Pessoa pes = (Pessoa) obj;
            equals = getNome().equals(pes.getNome()) && getIdade() == pes.getIdade();
        }
        return equals;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
