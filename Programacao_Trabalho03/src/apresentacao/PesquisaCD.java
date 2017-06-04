package apresentacao;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import adapters.Cd;
import adapters.SomLivreServidorAdapter;
import adapters.SubmarinoProductsAdapter;

public class PesquisaCD extends JFrame {

	private static final long serialVersionUID = 1;
	private JTextField fildNomeArqSel;
	private JLabel lbText;
	private JButton btPesquisar;
	private JButton btSelecionar;
	private JButton btSalvar;
	private JTable tabela;
	private String Palavrapesquisa = "";
	private ArrayList<Cd> listaCdEscolhido = null;

	public PesquisaCD() {
		this.configuraForm();
	}

	private void configuraForm(){
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(670, 370);
		this.getContentPane().setBackground(Color.WHITE);
		this.setTitle("Pesquisa de CD's");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setAlwaysOnTop(false);
		this.setLayout(null);

		// Adiciona componentes no formul�rio
		addLabels();
		addButton();
		addTextFild();
		addGrid();

		// Repinta os componentes na tela
		this.repaint();
	}

	private void addLabels() {
		lbText = new JLabel("Descri��o");
		lbText.setBounds(10, 25, 200, 25);
		this.add(lbText);
	}

	private void addTextFild() {
		fildNomeArqSel = new JTextField();
		fildNomeArqSel.setBounds(75, 25, 432, 25);
		fildNomeArqSel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					pesquisar();
				}
			}
		});
		this.add(fildNomeArqSel);
	}

	private void addButton() {
		// Adiciona o bot�o de Pesuisar
		btPesquisar = new JButton("Pesquisar");
		btPesquisar.setBounds(509, 25, 145, 24);
		btPesquisar.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {				 
				pesquisar();
			}
		});
		this.add(btPesquisar);

		//Adiciona o bot�o de Selecionar pesquisa
		btSelecionar = new JButton("Selecionar pesquisa");
		btSelecionar.setBounds(356, 313, 150, 24);
		btSelecionar.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {				 
				setVisible(false);
				ArrayList<Cd> listaRetorno =  new SelecionarPesquisasSalvas().getPesquisas();
				
				setListaCdEscolhido(listaRetorno);
				carregaGrid();
				setVisible(true);			
			}
		});
		this.add(btSelecionar);

		//Adiciona o bot�o de Salvar
		btSalvar = new JButton("Salvar");
		btSalvar.setBounds(509, 313, 145, 24);
		btSalvar.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {				 
				if(getListaCdEscolhido() == null || getListaCdEscolhido().size() == 0){
					JOptionPane.showMessageDialog(null, "Lista vazia!", "Aten��o", JOptionPane.INFORMATION_MESSAGE);
				}else{
					salvar();
				}
			}
		});
		this.add(btSalvar);
	}

	private void addGrid(){
		tabela = new JTable();
		DefaultTableModel model = new DefaultTableModel(null, new String[]{"Nome", "�lbum", "G�nero", "Valor"});
		tabela.setModel(model);
		tabela.setDefaultEditor(Object.class, null);
		tabela.getTableHeader().setReorderingAllowed(false);
		tabela.getTableHeader().setResizingAllowed(true);
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.setShowHorizontalLines(true);
		tabela.setShowVerticalLines(true);
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(10, 60, 645, 250);
		scroll.setViewportView(tabela);
		this.add(scroll);
	}

	public ArrayList<Cd> getListaCdEscolhido() {
		return listaCdEscolhido;
	}

	private void setListaCdEscolhido(ArrayList<Cd> listaCdEscolhido) {
		this.listaCdEscolhido = listaCdEscolhido;
	}

	private void AddLista(ArrayList<Cd> lista){
		if(lista != null){
			this.setListaCdEscolhido(lista);
		}
	}

	private void carregaGrid(){
		DefaultTableModel tabelaModelo = new DefaultTableModel(null, new String[] { "Nome", "�lbum", "G�nero", "Valor" });
		this.tabela.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));


		if(this.listaCdEscolhido != null){
			for (int i = 0; i < this.listaCdEscolhido.size(); i++) {
				tabelaModelo.addRow(new String[] { "Nome", "�lbum", "G�nero", "Valor" });
				tabelaModelo.setValueAt(listaCdEscolhido.get(i).getNome(), i, 0);
				tabelaModelo.setValueAt(listaCdEscolhido.get(i).getAlbum(), i, 1);
				tabelaModelo.setValueAt(listaCdEscolhido.get(i).getGenero(), i, 2);
				tabelaModelo.setValueAt(listaCdEscolhido.get(i).getValor(), i, 3);
			}
		}

		this.tabela.setModel(tabelaModelo);
		this.tabela.setCursor(Cursor.getDefaultCursor());
	}

	public void pesquisar(){
		this.setListaCdEscolhido(new ArrayList<>());
		this.Palavrapesquisa = this.fildNomeArqSel.getText().trim();
		this.AddLista(new SubmarinoProductsAdapter().procurar(this.Palavrapesquisa, getListaCdEscolhido()));
		this.AddLista(new SomLivreServidorAdapter().procurar(this.Palavrapesquisa, getListaCdEscolhido())); 

		if(this.getListaCdEscolhido().size() == 0){
			JOptionPane.showMessageDialog(null, "N�o foi poss�vel localizar nenhuma informa��o com este filtro!", "Aten��o", JOptionPane.INFORMATION_MESSAGE);
		}
		this.carregaGrid();
	}

	public void salvar(){
		JFileChooser fc = new JFileChooser();

		// restringe a amostra a diretorios apenas
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int res = fc.showOpenDialog(null);

		if(res == JFileChooser.APPROVE_OPTION){

			try {

				File arquivo = new File(fc.getSelectedFile() + "\\" + (this.Palavrapesquisa.isEmpty() ? "Todos" : this.Palavrapesquisa) + ".pesquisa");

				if(!arquivo.exists() || JOptionPane.showConfirmDialog(null, "O arquivo j� existe!!\n\rDeseja substituir?", "Aten��o", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.CANCEL_OPTION){
					FileOutputStream fout = new FileOutputStream(arquivo.getAbsolutePath());
					ObjectOutputStream object = new ObjectOutputStream(fout);
					object.writeObject(this.getListaCdEscolhido());
					fout.close();
					object.close();
				}

				JOptionPane.showMessageDialog(null, "Salvo com sucesso!", "Aten��o", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "N�o foi poss�vel salvar arquivo!", "Aten��o", JOptionPane.INFORMATION_MESSAGE);
			}

		}
		else
			JOptionPane.showMessageDialog(null, "N�o foi poss�vel localizar o caminho escolhido!", "Aten��o", JOptionPane.ERROR_MESSAGE);
	}
}
