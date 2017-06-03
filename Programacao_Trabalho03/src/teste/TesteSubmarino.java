package teste;

import org.omg.PortableServer.POAPackage.ServantNotActive;

import didatico.SubmarinoProducts;

public class TesteSubmarino {

	public static void main(String[] args) {

		SubmarinoProducts sub = SubmarinoProducts.getInstance();
		
		System.out.println(sub.isConnected());
		try {
			sub.connect("furb", "furb");
		} catch (Exception e) {
			System.out.println("ERRROU");
		}
		System.out.println(sub.isConnected());
		
		try {
			//[*][variavel]
			/*
			 * variavel == 0 - Nome banda
			 * variavel == 1 - Genero
			 * variavel == 2 - Album
			 * variavel == 3 - Valor
			 */
			for (int i = 0; i < sub.getCDProducts().length; i++) {
				System.out.println("Produto "+sub.getCDProducts()[i][4]);
			}
			

		} catch (ServantNotActive e) {
			System.out.println("Errou");
		}
		
		sub.disconnect();
		
		System.out.println(sub.isConnected());
		
		
		
	}

}
