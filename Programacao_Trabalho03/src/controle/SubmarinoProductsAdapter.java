package controle;

import java.util.HashSet;

import javax.swing.JOptionPane;

import org.omg.PortableServer.POAPackage.ServantNotActive;

import didatico.SubmarinoProducts;

public class  SubmarinoProductsAdapter implements Loja{
	SubmarinoProducts subMarino = null;

	public SubmarinoProductsAdapter() {
		this.conectar();
	}

	@Override
	public boolean conectar() {
		try {

			this.subMarino = SubmarinoProducts.getInstance();
			this.subMarino.connect("furb", "furb");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao tentar fazer conexão com o servidor Submarino", "Atenção", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	@Override
	public HashSet<Cd> procurar(String palavraChave) {

		HashSet<Cd> listaCds = new HashSet<>();

		if(this.isConnectado()){

			try{
				String[][] informacoes = this.subMarino.getCDProducts();

				if(informacoes != null){
					for (int i = 0; i < informacoes.length; i++) {
						if(this.isItemPesquisa(informacoes, palavraChave, i)){
							listaCds.add(getCd(informacoes, i));
						}
					}
				}
			} catch (ServantNotActive e) {
				JOptionPane.showMessageDialog(null, "Não foi possível carregar lista do Submarino!", "Atenção", JOptionPane.ERROR_MESSAGE);
			}

		} else {
			JOptionPane.showMessageDialog(null, "Verificar conexão com o servidor Submarino", "Atenção", JOptionPane.ERROR_MESSAGE);
		}

		this.desconectar();
		return listaCds;
	}

	private boolean isItemPesquisa(String[][] informacoes, String chave, int index) {
		return ((informacoes[index][2].toLowerCase().contains(chave)) || (informacoes[index][0].toLowerCase().contains(chave)));
	}

	private Cd getCd(String[][] informacoes, int i) {
		return new Cd(informacoes[i][2], informacoes[i][1], informacoes[i][0], "Submarino",Float.parseFloat(informacoes[i][3].trim()));
	}

	@Override
	public boolean desconectar() {
		this.subMarino.disconnect();
		this.subMarino = null;

		return true;
	}

	public boolean isConnectado() {
		return this.subMarino != null;
	}

}
