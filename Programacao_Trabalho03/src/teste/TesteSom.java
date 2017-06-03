package teste;

import java.util.ArrayList;

import adapters.Cd;
import adapters.SomLivreServidorAdapter;
import conexao.SomLivreServidor;

public class TesteSom {
	public static void main(String[] args) {
		SomLivreServidor som = new SomLivreServidor();
		
		System.out.println(som.registrar("Teste"));

		String[] retorno = som.buscaCD();
		
		
		for (int i = 0; i < retorno.length; i++) {
		//	System.out.println(retorno[i]);
		}
		
		SomLivreServidorAdapter adpter = new SomLivreServidorAdapter();
		
		ArrayList<Cd> listaAux = adpter.procurar("pitty",  new ArrayList<>());
		
		for (int i = 0; i < listaAux.size(); i++) {
			System.out.println(listaAux.get(i).toString());
		}
		
	}
}
