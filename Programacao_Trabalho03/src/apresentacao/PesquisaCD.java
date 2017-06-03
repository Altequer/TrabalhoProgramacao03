package apresentacao;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
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

		// Adiciona componentes no formulário
		addLabels();
		addButton();
		addTextFild();
		addGrid();

		// Repinta os componentes na tela
		this.repaint();
	}

	private void addLabels() {
		lbText = new JLabel("Descrição");
		lbText.setBounds(10, 25, 200, 25);
		this.add(lbText);
	}

	private void addTextFild() {
		fildNomeArqSel = new JTextField();
		fildNomeArqSel.setBounds(75, 25, 432, 25);
		this.add(fildNomeArqSel);
	}

	private void addButton() {
		// Adiciona o botão de Pesuisar
		btPesquisar = new JButton("Pesquisar");
		btPesquisar.setBounds(509, 25, 145, 24);
		btPesquisar.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {				 
				setListaCdEscolhido(new ArrayList<>());
				new SubmarinoProductsAdapter(getListaCdEscolhido());
				new SomLivreServidorAdapter(getListaCdEscolhido()); 
				
			}
		});
		this.add(btPesquisar);
		
		//Adiciona o botão de Selecionar pesquisa
		
		btSelecionar = new JButton("Selecionar pesquisa");
		btSelecionar.setBounds(356, 313, 150, 24);
		btSelecionar.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {				 
				setVisible(false);
				new SelecionarPesquisasSalvas();
				setVisible(true);			
			}
		});
		this.add(btSelecionar);
		
		//Adiciona o botão de Salvar
		btSalvar = new JButton("Salvar");
		btSalvar.setBounds(509, 313, 145, 24);
		btSalvar.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {				 

			}
		});
		this.add(btSalvar);
	}
	
	private void addGrid(){
		String[] coluna = new String[]{"Nome", "Álbum", "Gênero", "Valor"};
		
		tabela = new JTable();
		DefaultTableModel model = new DefaultTableModel(coluna, 0);
		tabela.setModel(model);
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(10, 60, 645, 250);
		scroll.setViewportView(tabela);
		this.add(scroll);
	}

	public ArrayList<Cd> getListaCdEscolhido() {
		return listaCdEscolhido;
	}

	public void setListaCdEscolhido(ArrayList<Cd> listaCdEscolhido) {
		this.listaCdEscolhido = listaCdEscolhido;
	}

}
