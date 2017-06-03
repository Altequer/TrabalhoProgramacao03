package adapters;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Cd implements Serializable {
	private String nome, genero, album, artista;
	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}

	private float valor;

	public Cd(String nome, String genero, String album, String artista,float valor) {
		this.setNome(nome);
		this.setValor(valor);
		this.setAlbum(album);
		this.setGenero(genero);
		this.setArtista(artista);
	}

	@Override
	public String toString() {
		String retorno;

		retorno = ("Cd: " + this.getNome() +
				(this.getAlbum().isEmpty() ? "" : "\n\rAlbum: " + this.getAlbum()) +
				(this.getArtista().isEmpty() ? "" : "\n\rArtista: " + this.getArtista()) +
				(this.getGenero().isEmpty() ? "" : "\n\rGenero: " + this.getGenero()) +
				(this.getValor() > 0 ? "" : "\n\rValor: " + this.getValor()));

		return retorno;
	}

	public boolean comparar(String palavra) {

		if(palavra.isEmpty() || this.getNome().toLowerCase().contains(palavra) || this.getAlbum().toLowerCase().contains(palavra) || this.getGenero().toLowerCase().contains(palavra) || this.getArtista().toLowerCase().contains(palavra)){
			return true;
		}

		return false;
	}


	public String getNome() {
		if(this.nome != null){
			return nome;
		}else{
			return "";
		}
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getGenero() {
		if(this.genero != null){
			return genero;
		}else{
			return "";
		}
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getAlbum() {
		if(this.album != null){
		return album;
		}else{
			return "";
		}
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}
}
