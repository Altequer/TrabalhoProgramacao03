package adapters;

import java.util.ArrayList;

public abstract class AdapterGenerico {

	private ArrayList<Cd> listaCds = null;

	public void AddLista(Cd cd) {
		this.listaCds.add(cd);
	}

	public ArrayList<Cd> procurar(String palavra) {
		ArrayList<Cd> listaRetorno = new ArrayList<>();

		for (int i = 0; i < this.listaCds.size(); i++) {
			if (this.listaCds.get(i).comparar(palavra)) {
				listaRetorno.add(this.listaCds.get(i));
			}
		}

		return listaRetorno;
	}
	
	public ArrayList<Cd> getListaCds() {
		return listaCds;
	}
	
	public void setListaCds(ArrayList<Cd> listaCds) {
		this.listaCds = listaCds;
	}
	
	public abstract boolean conectar();

	public abstract boolean deconectar();

	public abstract boolean isConnectado();
}
