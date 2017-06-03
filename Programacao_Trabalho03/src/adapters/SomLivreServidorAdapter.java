package adapters;

import java.util.ArrayList;
import conexao.SomLivreServidor;

public class SomLivreServidorAdapter {
	private ArrayList<Cd> listaCds = null;
	SomLivreServidor somServidor = null;
	
	public SomLivreServidorAdapter() {
		listaCds = new ArrayList<>();
		somServidor =  new SomLivreServidor();
		
		this.carregarLista();
	}
	
	private void AddLista(Cd cd) {
		this.listaCds.add(cd);
	}	
	
	private void carregarLista() {
		this.conectar();
		
		String[] informacoes = this.somServidor.buscaCD();
		
		for (int i = 0; i < informacoes.length; i++) {
			String[] infos = informacoes[i].split("|");
			
			for (int j = 0; j < infos.length; j++) {
				this.AddLista(new Cd(infos[j], "", "", infos[j++], Integer.parseInt(infos[j+2])));
				j += 2;
			}
			
		}
	}
	
	@SuppressWarnings("unused")
	private ArrayList<Cd> procurar(String palavra) {
		ArrayList<Cd> listaRetorno = new ArrayList<>();
		
		for (int i = 0; i < this.listaCds.size(); i++) {
			if(this.listaCds.get(i).comparar(palavra)){
				listaRetorno.add(this.listaCds.get(i));
			}
		}
		
		return listaRetorno;
	}
	
	private void conectar() {
		this.somServidor.registrar("Connectar");
	}
	
	@SuppressWarnings("unused")
	private void deconectar() {
		this.listaCds = null;
	}
}
