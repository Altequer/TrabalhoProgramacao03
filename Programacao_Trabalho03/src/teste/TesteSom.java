package teste;

import java.util.HashSet;

import conexao.SomLivreServidor;
import controle.Cd;
import controle.SomLivreServidorAdapter;

public class TesteSom {
	public static void main(String[] args) {
		SomLivreServidor som = new SomLivreServidor();
		
		System.out.println(som.registrar("Teste"));

		String[] retorno = som.buscaCD();
		
		
		for (int i = 0; i < retorno.length; i++) {
		//	System.out.println(retorno[i]);
		}
		
		SomLivreServidorAdapter adpter = new SomLivreServidorAdapter();
		
		HashSet<Cd> listaAux = adpter.procurar("pitty");
		
		for (Cd cd : listaAux) {
			System.out.println(listaAux.toString());
		}
		
	}
}
