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

		retorno = ("Nome cd: " + this.getNome() +
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
	
	public String caminhoImg(){
		switch (this.getNome()){
		case "Admirável Chip Novo":
			return  "./src/Imagens/1.jpg";
		case "{Des}concerto ao Vivo":
			return "./src/Imagens/2.jpg";
		case "Agridoce":
			return "./src/Imagens/3.jpg";
		case "Real Fantasia":
			return "./src/Imagens/4.jpg";
		case "Milagreiro":
			return "./src/Imagens/5.jpg";
		case "Estandarte":
			return "./src/Imagens/6.jpg";
		case "Cosmotron":
			return "./src/Imagens/7.jpg";
		case "Discotecagem Pop":
			return "./src/Imagens/8.jpg";
		case "Folia & Caos":
			return "./src/Imagens/9.jpg";	
		case "La Plata":
			return "./src/Imagens/10.jpg";
		case "Oxigênio":
			return "./src/Imagens/11.jpg";
		case "Só para brilhar":
			return "./src/Imagens/12.jpg";
		case "Velocia":
			return "./src/Imagens/13.jpg";
		case "Skank 91":
			return "./src/Imagens/14.jpg";
		case "Em comum":
			return "./src/Imagens/15.jpg";
		case "Projeto paralelo":
			return "./src/Imagens/16.jpg";
		case "Agora":
			return "./src/Imagens/17.jpg";
		case "Meu toca discos se matou":
			return "./src/Imagens/18.jpg";
		case "1977":
			return "./src/Imagens/19.jpg";
		case "Quando chega a noite":
			return "./src/Imagens/20.jpg";
		case "Parece que foi ontem":
			return "./src/Imagens/21.jpg";
		}
		return "";
	}
}
