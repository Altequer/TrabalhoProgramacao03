package controle;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Cd implements Serializable, Comparable<Cd> {
	private String album, genero, banda_artista, loja;
	private float valor;

	public Cd(String album, String genero, String banda_artista, String loja,float valor) {
		this.setAlbum(album);
		this.setValor(valor);
		this.setBanda_artista(banda_artista);
		this.setGenero(genero);
		this.setLoja(loja);
	}

	@Override
	public String toString() {
		String retorno;

		retorno = ("album cd: " + this.getAlbum() +
				(this.getBanda_artista().isEmpty() ? "" : "\n\rbanda_artista: " + this.getBanda_artista()) +
				(this.getLoja().isEmpty() ? "" : "\n\rLoja: " + this.getLoja()) +
				(this.getGenero().isEmpty() ? "" : "\n\rGenero: " + this.getGenero()) +
				(this.getValor() > 0 ? "" : "\n\rValor: " + this.getValor()));

		return retorno;
	}

	public boolean comparar(String palavra) {

		if(palavra.isEmpty() || this.getAlbum().toLowerCase().contains(palavra) || this.getBanda_artista().toLowerCase().contains(palavra) || this.getGenero().toLowerCase().contains(palavra) || this.getLoja().toLowerCase().contains(palavra)){
			return true;
		}

		return false;
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

	public String getLoja() {
		return loja;
	}

	public void setLoja(String loja) {
		this.loja = loja;
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

	public String getBanda_artista() {
		if(this.banda_artista != null){
			return banda_artista;
		}else{
			return "";
		}
	}

	public void setBanda_artista(String banda_artista) {
		this.banda_artista = banda_artista;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public String caminhoImg(){
		switch (this.getAlbum()){
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
	
	@Override
	public int compareTo(Cd outro) {
		return new Float(this.getValor()).compareTo(outro.getValor());
	}
}
