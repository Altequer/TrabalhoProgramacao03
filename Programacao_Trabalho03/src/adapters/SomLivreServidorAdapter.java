package adapters;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import conexao.SomLivreServidor;

public class SomLivreServidorAdapter extends AdapterGenerico {
	SomLivreServidor somServidor = null;

	public SomLivreServidorAdapter() {
		this.conectar();
		this.carregarLista();
		this.deconectar();
	}

	private void carregarLista() {
		if (this.isConnectado()) {
			String[] informacoes = null;
			
			this.setListaCds(new ArrayList<>());
			informacoes = this.somServidor.buscaCD();
			
			for (int i = 0; i < informacoes.length; i++) {

				String[] infos = informacoes[i].replace("|", "\n").split("\n");
				this.AddLista(new Cd(infos[0], "", infos[1], "", Float.parseFloat(infos[2].trim())));
			}
		}else{
			JOptionPane.showMessageDialog(null, "Verificar conexão!", "Atenção", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public boolean conectar() {
		try {

			this.somServidor = new SomLivreServidor();
			this.somServidor.registrar("Connectar");

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao tentar fazer conexão", "Atenção", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	@Override
	public boolean deconectar() {
		this.somServidor = null;
		return true;
	}

	@Override
	public boolean isConnectado() {
		if (this.somServidor != null) {
			return true;
		}
		return false;
	}
}
