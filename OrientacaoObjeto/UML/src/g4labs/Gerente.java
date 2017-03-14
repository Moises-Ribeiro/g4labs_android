package g4labs;

import g4labs.Funcionario;

public class Gerente extends Funcionario {
	@Override
	public float getSalario() {
		return super.getSalario() * 0.95f;
	}

}