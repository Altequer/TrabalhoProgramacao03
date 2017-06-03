package adapters;

import conexao.SomLivreServidor;

public class SomLivreServidorAdapter extends AdapterGenerico {
	SomLivreServidor somServidor = null;

	public SomLivreServidorAdapter() {
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
			System.out.println("Verificar conex�o!");
		}
	}

	@Override
	public boolean conectar() {
		try {

			this.somServidor = new SomLivreServidor();
			this.somServidor.registrar("Connectar");

		} catch (Exception e) {
			System.out.println("Erro ao tentar fazer conex�o");
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
