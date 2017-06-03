package adapters;

import java.util.ArrayList;
import conexao.SomLivreServidor;

public class SomLivreServidorAdapter {
	private ArrayList<Cd> listaCds = null;
	SomLivreServidor somServidor = null;
	
	public SomLivreServidorAdapter() {
		listaCds = new ArrayList<>();
		this.conectar();
		this.carregarLista();
	}
	
	private void AddLista(Cd cd) {
		this.listaCds.add(cd);
	}	
	
	private void carregarLista() {
		this.conectar();
		
		String[] informacoes = this.somServidor.buscaCD();
		
		for (int i = 0; i < informacoes.length; i++) {
			
			String[] infos = informacoes[i].replace("|", "\n").split("\n");
			this.AddLista(new Cd(infos[0], "", "", infos[1], Float.parseFloat(infos[2].trim())));
		}
	}
	
	public ArrayList<Cd> procurar(String palavra) {
		ArrayList<Cd> listaRetorno = new ArrayList<>();
		
		for (int i = 0; i < this.listaCds.size(); i++) {
			if(this.listaCds.get(i).comparar(palavra)){
				listaRetorno.add(this.listaCds.get(i));
			}
		}
		
		return listaRetorno;
	}
	
	private void conectar() {
		this.somServidor =  new SomLivreServidor();
		this.somServidor.registrar("Connectar");
	}
	
	@SuppressWarnings("unused")
	private void deconectar() {
		this.listaCds = null;
	}
	
	public boolean isConnectado(){
		if(this.somServidor != null){
			return true;
		}
		return false;
	}
}
