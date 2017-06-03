package adapters;

import org.omg.PortableServer.POAPackage.ServantNotActive;
import didatico.SubmarinoProducts;

public class  SubmarinoProductsAdapter extends AdapterGenerico{
	SubmarinoProducts subMarino = null;

	public SubmarinoProductsAdapter() {
		this.carregarLista();
	}

	private void carregarLista() {
		if (this.conectar()) {

			String[][] informacoes;
			try {
				informacoes = this.subMarino.getCDProducts();

				for (int i = 0; i < informacoes.length; i++) {
					this.AddLista(new Cd(informacoes[i][0], informacoes[i][1], informacoes[i][2], "", Float.parseFloat(informacoes[i][3].trim())));
				}
				
			} catch (ServantNotActive e) {
				System.out.println("Não foi possível carregar lista!");
			}
		}else{
			System.out.println("Verificar conexão!");
		}
	}

	@Override
	public boolean conectar() {
		try {

			this.subMarino = SubmarinoProducts.getInstance();
			this.subMarino.connect("furb", "furb");

		} catch (Exception e) {
			System.out.println("Erro ao tentar fazer conexão");
			return false;
		}

		return true;
	}

	@Override
	public boolean deconectar() {

		this.setListaCds(null);
		this.subMarino = null;

		return true;
	}

	@Override
	public boolean isConnectado() {
		if (this.subMarino != null) {
			return true;
		}
		return false;
	}
}
