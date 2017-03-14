package g4labs;
import g4labs.Funcionario;

public class Analista extends Funcionario {
	@Override
     public float getSalario() {
        return super.getSalario() * 0.80f;
    }


}