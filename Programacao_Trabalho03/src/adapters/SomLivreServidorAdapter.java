package adapters;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import conexao.SomLivreServidor;

public class SomLivreServidorAdapter extends AdapterGenerico {
	SomLivreServidor somServidor = null;

	public SomLivreServidorAdapter(ArrayList<Cd> lista) {
		this.setListaCds(lista);
		this.carregarLista();
	}

	private void carregarLista() {
		if (this.conectar()) {

			String[] informacoes = this.somServidor.buscaCD();

			for (int i = 0; i < informacoes.length; i++) {

				String[] infos = informacoes[i].replace("|", "\n").split("\n");
				this.AddLista(new Cd(infos[0], "", "", infos[1], Float.parseFloat(infos[2].trim())));
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
			JOptionPane.showMessageDialog(null, "Erro ao tentar fazer conexão", "Atenção", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	@Override
	public boolean deconectar() {

		this.setListaCds(null);
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
