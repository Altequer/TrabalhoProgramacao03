package apresentacao;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import controle.Cd;
import controle.PesquisaPrecoFacade;

public class SelecionarPesquisasSalvas extends JDialog {

	private static final long serialVersionUID = 1;
	//Botao de rotorno para a tela principal sem escolher nada
	//Botao para retornar a lista que foi escolhida
	//Bot�o para o usuario escolher um diretorio onde sera pesquisado pelas listas salvas
	private JButton btCancelar, btSelecionar, btSelecionarDiretorio;
	//Mostra diretorio escolhido pelo usuario
	private JTextField fieldDiretorio;
	//Tabela que informa todas as listas salvas
	private JTable tabela;
	//Informa o que deve ser feito na tela
	//Informa o que deve ser feito no bot�o seleciona diretorio
	private JLabel lbText, lbDiretorio;
	
	@SuppressWarnings("rawtypes")
	//Uma colecao com as listas salvas<Arquivo, lista>
	private HashMap<File, ArrayList> pesquisas;
	//Controla a lista de cds
	private PesquisaPrecoFacade pesquisaPrecoFacade;
	
	public SelecionarPesquisasSalvas() {
		super();
		//Inicia lista vazia
		this.pesquisas = new HashMap<>();
		this.pesquisaPrecoFacade = PesquisaPrecoFacade.getInstaciaFacade();
		//Carrega componentes em tela
		this.configuraForm();
	}

	private void configuraForm(){
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setSize(470, 350);
		this.getContentPane().setBackground(Color.WHITE);
		this.setTitle("Pesquisa salvas");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setAlwaysOnTop(false);
		this.setLayout(null);

		// Adiciona componentes no formul�rio
		addLabel();
		addButton();
		addGrid();
		addField();

		// Repinta os componentes na tela
		this.repaint();
		this.setModal(true);
		this.setVisible(true);
	}

	private void addField() {
		fieldDiretorio = new JTextField();
		fieldDiretorio.setBounds(10, 20, 340, 24);
		fieldDiretorio.setEnabled(false);
		this.add(fieldDiretorio);
	}

	private void addLabel() {
		lbText = new JLabel("Selecione a pesquisa salva:");
		lbText.setBounds(10, 48, 200, 20);
		this.add(lbText);

		lbDiretorio = new JLabel("Selecione diret�rio:");
		lbDiretorio.setBounds(10, 0, 200, 20);
		this.add(lbDiretorio);
	}

	private void addGrid() {
		tabela = new JTable();
		DefaultTableModel model = new DefaultTableModel(null, new String[]{"Nome", "Data-Hora", "Caminho"});
		tabela.setModel(model);
		tabela.setDefaultEditor(Object.class, null);
		tabela.getTableHeader().setReorderingAllowed(false);
		tabela.getTableHeader().setResizingAllowed(true);
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.setShowHorizontalLines(true);
		tabela.setShowVerticalLines(true);
		tabela.addMouseListener(new MouseAdapter() {  
		    public void mouseClicked(MouseEvent e)  
		    {  
		        if (e.getClickCount() == 2)  
		        {  
		           dispose();  
		        }  
		    }  
		});  
		
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(10, 70, 445, 220);
		scroll.setViewportView(tabela);
		this.add(scroll);
	}

	private void addButton() {

		//Adiciona o bot�o de Selecionar Selecionae
		btSelecionar = new JButton("Selecionar");
		btSelecionar.setBounds(252, 293, 100, 24);
		btSelecionar.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {	
				dispose();
			}
		});
		this.add(btSelecionar);

		// Adiciona o bot�o de Cancelar
		btCancelar = new JButton("Cancelar");
		btCancelar.setBounds(354, 293, 100, 24);
		btCancelar.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		this.add(btCancelar);

		// Adiciona o bot�o de Selecionar diret�rio
		btSelecionarDiretorio = new JButton("Selecionar");
		btSelecionarDiretorio.setBounds(354, 20, 100, 24);
		btSelecionarDiretorio.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				selecionaPesquisa();
				carregaGrid();
			}
		});
		this.add(btSelecionarDiretorio);
	}

	protected void selecionaPesquisa() {
		this.pesquisaPrecoFacade.ler();
		this.fieldDiretorio.setText(pesquisaPrecoFacade.getDiretorioBusca());
		this.pesquisas = pesquisaPrecoFacade.getPesquisa();		
	}

	private void carregaGrid(){
		String toolltip = "Cds: ";
		CellRendererToolTip renderer = new CellRendererToolTip(); 
		DefaultTableModel tabelaModelo = new DefaultTableModel(null, new String[] { "Nome", "Data-Hora", "Caminho", "Listas"});
		this.tabela.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				
		
		if(this.pesquisas != null && this.pesquisas.size() > 0){
			int qtd = 0;

			for (File file : this.pesquisas.keySet()) {
				tabelaModelo.addRow(new String[] { "Nome", "Data-Hora", "Caminho", "Listas"});
				tabelaModelo.setValueAt(file.getName().replace(".pesquisa", ""), qtd, 0);
				tabelaModelo.setValueAt(new SimpleDateFormat("dd/MM/yyyy - HH:MM:ss").format(file.lastModified()), qtd, 1);
				tabelaModelo.setValueAt(file.getAbsolutePath(), qtd, 2);
				tabelaModelo.setValueAt(this.pesquisas.get(file), qtd, 3);
				
				for (int i = 0; i < this.pesquisas.get(file).size(); i++) {
					toolltip += (toolltip.equals("Cds: ") ? "" : ", ") + ((Cd) this.pesquisas.get(file).get(i)).getAlbum();
				}
						
				renderer.addToolTip(qtd, toolltip);
				qtd++;
			}
		}
		this.tabela.setModel(tabelaModelo);
		this.tabela.getColumnModel().getColumn(0).setCellRenderer(renderer);
		this.tabela.getColumnModel().getColumn(1).setCellRenderer(renderer);
		this.tabela.getColumnModel().getColumn(2).setCellRenderer(renderer);
		this.tabela.getColumnModel().removeColumn(this.tabela.getColumnModel().getColumn(3));
		this.tabela.setAutoCreateRowSorter(true);
		this.tabela.setCursor(Cursor.getDefaultCursor());
		
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Cd> getPesquisas() {
		int linhaSelecionada = this.tabela.getSelectedRow();

		if(linhaSelecionada > -1 && this.pesquisas != null && this.pesquisas.size() > 0){
			return (ArrayList<Cd>) this.tabela.getModel().getValueAt(linhaSelecionada, 3);
		}
		return null;
	}
}
