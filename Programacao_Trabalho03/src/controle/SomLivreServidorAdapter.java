package controle;

import java.util.HashSet;

import javax.swing.JOptionPane;

import conexao.SomLivreServidor;

public class SomLivreServidorAdapter implements Loja {
	SomLivreServidor somServidor = null;

	public SomLivreServidorAdapter() {
		this.conectar("conectar","");
	}

	@Override
	public HashSet<Cd> procurar(String chave) {

		HashSet<Cd> listaPreencher = new HashSet<>();

		if(this.isConnectado()){

			String[] informacoes = this.somServidor.buscaCD();;

			if(informacoes != null){
				for (int i = 0; i < informacoes.length; i++) {
					String[] infos = informacoes[i].replace("|", "\n").split("\n");

					if(this.isItemPesquisa(infos, chave)){
						listaPreencher.add(getCd(infos));
					}
				}
			}

		} else {
			JOptionPane.showMessageDialog(null, "Verificar conex�o com o servidor Som Livre", "Aten��o", JOptionPane.ERROR_MESSAGE);
		}

		this.desconectar();
		return listaPreencher;
	}

	private Cd getCd(String[] infos) {
		return new Cd(infos[0], "", infos[1], "Som Livre", Float.parseFloat(infos[2].trim()));
	}

	private boolean isItemPesquisa(String[] informacoes, String chave) {
		return ((informacoes[0].toLowerCase().contains(chave)) || (informacoes[1].toLowerCase().contains(chave)));
	}

	@Override
	public boolean conectar(String usuario, String senha) {
		try {

			this.somServidor = new SomLivreServidor();
			this.somServidor.registrar(usuario);

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao tentar fazer conex�o com o servidor Som Livre", "Aten��o", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	@Override
	public boolean desconectar() {
		this.somServidor = null;
		return true;
	}

	public boolean isConnectado() {
		return this.somServidor != null;
	}
}
