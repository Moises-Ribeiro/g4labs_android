package g4labs;

import g4labs.Funcionario;

public class Desenvolvedor extends Funcionario {
	@Override
    public float getSalario() {
        return super.getSalario() * 0.70f;
    }

}