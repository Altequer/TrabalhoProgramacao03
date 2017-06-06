package adapters;

import java.util.Comparator;

public class CdComparador {
	public static Comparator<Cd> VALOR_ALBUM = new Comparator<Cd>() {
        @Override
        public int compare(Cd o1, Cd o2) {
        	int comparacao = o1.getAlbum().compareTo(o2.getAlbum());
        	
        	if (comparacao == 0) {
        		return o1.compareTo(o2);
        	}
        	return comparacao;
        }
    };
    
    public static Comparator<Cd> VALOR_BANDA_ARTISTA = new Comparator<Cd>() {
        @Override
        public int compare(Cd o1, Cd o2) {
        	if (o1.getBanda_artista().equalsIgnoreCase(o2.getBanda_artista())) {
        		return o1.compareTo(o2);
        	}
        	return o1.getBanda_artista().compareTo(o2.getBanda_artista());
        }
    };
}