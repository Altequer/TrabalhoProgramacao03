package adapters;

import java.util.Comparator;

public class CdComparador {
	public static Comparator<Cd> VALOR_ALBUM = new Comparator<Cd>() {
        @Override
        public int compare(Cd o1, Cd o2) {
        	if (o1.getAlbum() == o2.getAlbum()) {
        		return o1.compareTo(o2);
        	}
//        	if (o1.getAlbum() > o2.getAlbum()) {
//        		return 1;
//        	} else {
//        		return -1;
//        	} 
        	return -1;
        }
    };
    public static Comparator<Cd> VALOR_BANDA_ARTISTA = new Comparator<Cd>() {
        @Override
        public int compare(Cd o1, Cd o2) {
        	int modelComparation = o1.getBanda_artista().compareTo(o2.getBanda_artista());
        	if (modelComparation == 0) {
        		
        	}
        	return modelComparation;

        }
    };
}
