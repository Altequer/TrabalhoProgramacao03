package teste;

import conexao.SomLivreServidor;

public class TesteSom {
	public static void main(String[] args) {
		SomLivreServidor som = new SomLivreServidor();
		
		System.out.println(som.registrar("Teste"));

		String[] retorno = som.buscaCD();
		
		
		for (int i = 0; i < retorno.length; i++) {
			System.out.println(retorno[i]);
		}
		
	}
}
