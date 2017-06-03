package adapters;

public class Cd {
	private String nome, genero, album;
	private float valor;
	
	public Cd(String nome, String genero, String album, float valor) {
		this.setNome(nome);
		this.setValor(valor);
		this.setAlbum(album);
		this.setGenero(genero);
	}
	
	public boolean comparar(String palavra) {
		
		if(this.getNome().toLowerCase().contains(palavra) || this.getAlbum().toLowerCase().contains(palavra) || this.getGenero().toLowerCase().contains(palavra)){
			return true;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		String retorno;
		
		return super.toString();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getAlbum() {
		return album;
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
