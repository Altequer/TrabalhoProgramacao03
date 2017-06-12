package controle;

import java.util.HashSet;

public interface Loja {
	public HashSet<Cd> procurar(String palavraChave);	
	public boolean conectar();
	public boolean desconectar();
}
