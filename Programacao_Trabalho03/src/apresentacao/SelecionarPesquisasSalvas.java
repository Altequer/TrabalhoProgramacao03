package apresentacao;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import adapters.Cd;

public class SelecionarPesquisasSalvas extends JDialog {

	private static final long serialVersionUID = 1;
	private JButton btCancelar;
	private JButton btSelecionar;
	private JTable tabela;
	private JLabel lbText;
	@SuppressWarnings("rawtypes")
	private HashMap<File, ArrayList> pesquisas = null;


	public SelecionarPesquisasSalvas() {
		super();
		this.configuraForm();
	}

	private void configuraForm(){
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setSize(470, 300);
		this.getContentPane().setBackground(Color.WHITE);
		this.setTitle("Pesquisa salvas");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setAlwaysOnTop(false);
		this.setLayout(null);
		this.pesquisas = new HashMap<>();

		// Adiciona componentes no formul�rio

		addLabel();
		addButton();
		addGrid();
		buscaArquivos();
		carregaGrid();

		// Repinta os componentes na tela
		this.repaint();
		this.setModal(true);
		this.setVisible(true);
	}

	private void addLabel() {
		lbText = new JLabel("Selecione a pesquisa salva:");
		lbText.setBounds(10, 5, 200, 20);
		this.add(lbText);
	}

	private void addGrid() {
		tabela = new JTable();
		DefaultTableModel model = new DefaultTableModel(new String[]{"Nome", "Caminho"}, 0);
		tabela.setModel(model);
		tabela.setDefaultEditor(Object.class, null);
		tabela.getTableHeader().setReorderingAllowed(false);
		tabela.getTableHeader().setResizingAllowed(true);
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.setShowHorizontalLines(true);
		tabela.setShowVerticalLines(true);
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(10, 30, 445, 210);
		scroll.setViewportView(tabela);
		this.add(scroll);
	}

	private void addButton() {
		//Adiciona o bot�o de Selecionar Selecionae

		btSelecionar = new JButton("Selecionar");
		btSelecionar.setBounds(252, 243, 100, 24);
		btSelecionar.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {	
				dispose();
			}
		});
		this.add(btSelecionar);

		// Adiciona o bot�o de Cancelar
		btCancelar = new JButton("Cancelar");
		btCancelar.setBounds(354, 243, 100, 24);
		btCancelar.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {				 
				dispose();
			}
		});
		this.add(btCancelar);

	}

	@SuppressWarnings("rawtypes")
	private void buscaArquivos() {
		JFileChooser fc = new JFileChooser();

		// restringe a amostra a diretorios apenas
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int res = fc.showOpenDialog(null);

		if(res == JFileChooser.APPROVE_OPTION){
			File diretorio = fc.getSelectedFile();

			File[] files = diretorio.listFiles();

			for (int i = 0; i < files.length; i++) {
				File arq = files[i];
				if (arq.exists() && arq.getName().contains(".pesquisa")) {
					try {

						FileInputStream fin = new FileInputStream(arq.getAbsolutePath());
						ObjectInputStream ois = new ObjectInputStream(fin);
						pesquisas.put(arq, (ArrayList) ois.readObject());
						fin.close();
						ois.close();

					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "N�o foi poss�vel carregar o arquivo " + arq.getName() + "!", "Aten��o", JOptionPane.ERROR_MESSAGE);
					}	
				}
			}

			this.carregaGrid();

		}else
			JOptionPane.showMessageDialog(null, "N�o foi poss�vel localizar o caminho escolhido!", "Aten��o", JOptionPane.ERROR_MESSAGE);
	}

	private void carregaGrid(){
		DefaultTableModel tabelaModelo = new DefaultTableModel(null, new String[] { "Nome", "Caminho" });
		this.tabela.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		if(this.pesquisas != null && this.pesquisas.size() > 0){
			int qtd = 0;
			
			for (File file : this.pesquisas.keySet()) {		
				tabelaModelo.addRow(new String[] { "Nome", "Caminho" });
				tabelaModelo.setValueAt(file.getName().replace(".pesquisa", ""), qtd, 0);
				tabelaModelo.setValueAt(file.getAbsolutePath(), qtd, 1);
				qtd++;
			}
		}

		this.tabela.setModel(tabelaModelo);
		this.tabela.setCursor(Cursor.getDefaultCursor());
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Cd> getPesquisas() {
		int linhaSelecionada = this.tabela.getSelectedRow();

		if(linhaSelecionada > -1 && this.pesquisas != null && this.pesquisas.size() > 0){
			for (File file : this.pesquisas.keySet()) {	
				if(this.tabela.getValueAt(linhaSelecionada, 1) == file.getAbsolutePath()){
					return this.pesquisas.get(file);
				}
			}
		}
		return null;
	}
}
