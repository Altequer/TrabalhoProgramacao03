package controle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

@SuppressWarnings("rawtypes")
public class PesquisaPrecoFacade {
	private ArrayList<Cd> listaCds;
	private String Palavrapesquisa;
	private static PesquisaPrecoFacade instaciaFacade = null;
	private String diretorioBusca;
	private HashMap<File, ArrayList> pesquisas = null;

	
	private PesquisaPrecoFacade() {
	}
	
	public String getDiretorioBusca(){
		return diretorioBusca;
	}
	
	public HashMap<File, ArrayList> getPesquisa(){
		return pesquisas;
	}
	
	public static PesquisaPrecoFacade getInstaciaFacade(){
		if(instaciaFacade == null){
			instaciaFacade = new PesquisaPrecoFacade();
		}
		
		return instaciaFacade;
	}

	public ArrayList<Cd> pesquisar(String palavraChave) {
		this.Palavrapesquisa = palavraChave;
		this.listaCds = new ArrayList<>();
		
		Loja submarino = new SubmarinoProductsAdapter();
		Loja somLivre = new SomLivreServidorAdapter();

		ArrayList<Cd> lojaSubmarino = new ArrayList<>(submarino.procurar(palavraChave));
		ArrayList<Cd> lojaSomLivre = new ArrayList<>(somLivre.procurar(palavraChave));

		listaCds.addAll(lojaSubmarino);
		listaCds.addAll(lojaSomLivre);
		
		return this.listaCds;
	}
	
	public void salvar() {
		JFileChooser fc = new JFileChooser();

		// restringe a amostra a diretorios apenas
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int res = fc.showSaveDialog(null);

		if (res == JFileChooser.APPROVE_OPTION) {

			try {

				File arquivo = new File(fc.getSelectedFile() + "\\"	+ (this.Palavrapesquisa.isEmpty() ? "Todos" : this.Palavrapesquisa) + ".pesquisa");

				if (!arquivo.exists()
						|| JOptionPane.showConfirmDialog(null, "O arquivo j� existe!!\n\rDeseja substituir?", "Aten��o",
								JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.CANCEL_OPTION) {
					FileOutputStream fout = new FileOutputStream(arquivo.getAbsolutePath());
					ObjectOutputStream object = new ObjectOutputStream(fout);
					object.writeObject(listaCds);
					fout.close();
					object.close();
				}

				JOptionPane.showMessageDialog(null, "Salvo com sucesso!", "Aten��o", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "N�o foi poss�vel salvar arquivo!", "Aten��o",
						JOptionPane.INFORMATION_MESSAGE);
			}

		} else
			JOptionPane.showMessageDialog(null, "N�o foi poss�vel localizar o caminho escolhido!", "Aten��o",
					JOptionPane.ERROR_MESSAGE);
	}
	
	public void ler() {
		JFileChooser fc = new JFileChooser();
		pesquisas = new HashMap<>();

		// restringe a amostra a diretorios apenas
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int res = fc.showOpenDialog(null);

		if(res == JFileChooser.APPROVE_OPTION){

			File diretorio = fc.getSelectedFile();

			if(diretorio.isDirectory()){

				diretorioBusca = diretorio.getAbsolutePath();
				File[] files = diretorio.listFiles();

				for (int i = 0; i < files.length; i++) {
					File arq = files[i];
					if (arq.exists() && arq.getName().contains(".pesquisa")) {
						try {

							FileInputStream fin = new FileInputStream(arq.getAbsolutePath());
							ObjectInputStream ois = new ObjectInputStream(fin);
							pesquisas.put(arq, (ArrayList<?>) ois.readObject());
							fin.close();
							ois.close();

						} catch (Exception ex) {
							ex.printStackTrace();
							JOptionPane.showMessageDialog(null, "N�o foi poss�vel carregar o arquivo " + arq.getName() + "!", "Aten��o", JOptionPane.ERROR_MESSAGE);
						}	
					}
				}

			}else{
				JOptionPane.showMessageDialog(null, "O diret�rio escolhido � inv�lido!", "Aten��o", JOptionPane.ERROR_MESSAGE);				
			}
		}else
			JOptionPane.showMessageDialog(null, "N�o foi poss�vel localizar o caminho escolhido!", "Aten��o", JOptionPane.ERROR_MESSAGE);
	}
}
