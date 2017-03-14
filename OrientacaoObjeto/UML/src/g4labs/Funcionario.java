package g4labs;

import g4labs.Funcionario;

public class Funcionario {

    private String nome;
    private float salario = 500.0f;

    public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
    
    public float getSalario() {
        return salario;
    }
    
    public void imprimirFolhaPagamento() {
    	System.out.println("====================");
    	System.out.println("Nome: " + getNome());
    	System.out.println("Salario: " + getSalario());
    	System.out.println("====================");
    }

	
}