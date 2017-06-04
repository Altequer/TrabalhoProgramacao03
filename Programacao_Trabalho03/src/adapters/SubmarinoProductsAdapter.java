package adapters;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.omg.PortableServer.POAPackage.ServantNotActive;
import didatico.SubmarinoProducts;

public class  SubmarinoProductsAdapter extends AdapterGenerico{
	SubmarinoProducts subMarino = null;

	public SubmarinoProductsAdapter() {
		this.conectar();
		this.carregarLista();
		this.deconectar();
	}

	private void carregarLista() {
		if (this.isConnectado()) {
			
			this.setListaCds(new ArrayList<>());
			String[][] informacoes;
			
			try {
				informacoes = this.subMarino.getCDProducts();

				for (int i = 0; i < informacoes.length; i++) {
					this.AddLista(new Cd(informacoes[i][2], informacoes[i][1], informacoes[i][0], "", Float.parseFloat(informacoes[i][3].trim())));
				}
				
			} catch (ServantNotActive e) {
				JOptionPane.showMessageDialog(null, "N�o foi poss�vel carregar lista!", "Aten��o", JOptionPane.ERROR_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(null, "Verificar conex�o!", "Aten��o", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public boolean conectar() {
		try {

			this.subMarino = SubmarinoProducts.getInstance();
			this.subMarino.connect("furb", "furb");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao tentar fazer conex�o", "Aten��o", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	@Override
	public boolean deconectar() {
		this.subMarino.disconnect();
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
