
import g4labs.Analista;
import g4labs.Desenvolvedor;
import g4labs.Funcionario;
import g4labs.Gerente;

public class Main {

	public static void main(String[] args) {
		Funcionario gerente = new Gerente();
		gerente.imprimirFolhaPagamento();

		Funcionario dev = new Desenvolvedor();
		dev.imprimirFolhaPagamento();

		Funcionario analista = new Analista();
		analista.imprimirFolhaPagamento();
	}

}