package apresentacao;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class SelecionarPesquisasSalvas extends JDialog {

	private static final long serialVersionUID = 1;
	private JButton btCancelar;
	private JButton btSelecionar;
	private JTable tabela;
	private JLabel lbText;

	public SelecionarPesquisasSalvas() {
		super();
		this.configuraForm();
	}

	private void configuraForm(){
		this.setModal(true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setSize(470, 300);
		this.getContentPane().setBackground(Color.WHITE);
		this.setTitle("Pesquisa salvas");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setAlwaysOnTop(false);
		this.setLayout(null);

		// Adiciona componentes no formulário

		addLabel();
		addButton();
		addGrid();

		// Repinta os componentes na tela
		this.repaint();
	}

	private void addLabel() {
		lbText = new JLabel("Selecione a pesquisa salva:");
		lbText.setBounds(10, 5, 200, 20);
		this.add(lbText);
	}

	private void addGrid() {
		String[] coluna = new String[]{"Descrição da pesquisa", "Data"};

		tabela = new JTable();
		DefaultTableModel model = new DefaultTableModel(coluna, 0);
		tabela.setModel(model);
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(10, 30, 445, 210);
		scroll.setViewportView(tabela);
		this.add(scroll);
	}

	private void addButton() {
		//Adiciona o botão de Selecionar Selecionae

		btSelecionar = new JButton("Selecionar");
		btSelecionar.setBounds(252, 243, 100, 24);
		btSelecionar.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {				 
			}
		});
		this.add(btSelecionar);

		// Adiciona o botão de Cancelar
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

}
