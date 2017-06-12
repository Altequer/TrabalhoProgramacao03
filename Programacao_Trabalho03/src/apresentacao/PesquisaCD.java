package apresentacao;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import controle.Cd;
import controle.CdComparador;
import controle.PesquisaPrecoFacade;

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
	private PesquisaPrecoFacade pesquisaPrecoFacade;
	boolean controleOrdem = true;

	public PesquisaCD() {
		pesquisaPrecoFacade = PesquisaPrecoFacade.getInstaciaFacade();
		this.configuraForm();
	}

	private void configuraForm() {
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
		
		this.fildNomeArqSel.requestFocus();

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
		// Adiciona o botão de Pesuisar
		btPesquisar = new JButton("Pesquisar");
		btPesquisar.setBounds(509, 25, 145, 24);
		btPesquisar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pesquisar();
			}
		});
		this.add(btPesquisar);

		// Adiciona o botão de Selecionar pesquisa
		btSelecionar = new JButton("Selecionar pesquisa");
		btSelecionar.setBounds(356, 313, 150, 24);
		btSelecionar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				ArrayList<Cd> listaRetorno = new SelecionarPesquisasSalvas().getPesquisas();

				setListaCdEscolhido(listaRetorno);
				carregaGrid();
				setVisible(true);
			}
		});
		this.add(btSelecionar);

		// Adiciona o botão de Salvar
		btSalvar = new JButton("Salvar");
		btSalvar.setBounds(509, 313, 145, 24);
		btSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (getListaCdEscolhido() == null || getListaCdEscolhido().size() == 0) {
					JOptionPane.showMessageDialog(null, "Lista vazia!", "Atenção", JOptionPane.INFORMATION_MESSAGE);
				} else {
					pesquisaPrecoFacade.salvar();
				}
			}
		});
		this.add(btSalvar);
	}

	private void addGrid() {
		tabela = new JTable();
		DefaultTableModel model = new DefaultTableModel(null,
				new String[] { "Álbum", "Banda/Artista", "Gênero", "Loja", "Valor" });
		tabela.setModel(model);
		tabela.setDefaultEditor(Object.class, null);
		tabela.getTableHeader().setReorderingAllowed(false);
		tabela.getTableHeader().setResizingAllowed(true);
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.setShowHorizontalLines(true);
		tabela.setShowVerticalLines(true);

		tabela.getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(listaCdEscolhido != null && listaCdEscolhido.size() > 0){
					int col = tabela.columnAtPoint(e.getPoint());

					switch (col) {
					case 0:
						if (controleOrdem) {
							Collections.sort(listaCdEscolhido, CdComparador.VALOR_ALBUM);
							controleOrdem = false;
						} else {
							Collections.reverse(listaCdEscolhido);
							controleOrdem = true;
						}
						break;
					case 1:
						if (controleOrdem) {
							Collections.sort(listaCdEscolhido, CdComparador.VALOR_BANDA_ARTISTA);
							controleOrdem = false;
						} else {
							Collections.reverse(listaCdEscolhido);
							controleOrdem = true;
						}
						break;
					case 4:
						if (controleOrdem) {
							Collections.sort(listaCdEscolhido);
							controleOrdem = false;
						} else {
							Collections.reverse(listaCdEscolhido);
							controleOrdem = true;
						}
						break;
					}
					carregaGrid();
				}
			}
		});

		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(10, 60, 645, 250);
		scroll.setViewportView(tabela);
		this.add(scroll);
	}

	public ArrayList<Cd> getListaCdEscolhido() {
		return listaCdEscolhido;
	}

	private void setListaCdEscolhido(ArrayList<Cd> listaCdEscolhido) {
		if(listaCdEscolhido != null){
			this.listaCdEscolhido = listaCdEscolhido;
		}
	}

	private void carregaGrid() {
		CellRendererToolTip renderer = new CellRendererToolTip();
		DefaultTableModel tabelaModelo = new DefaultTableModel(null,
				new String[] { "Álbum", "Banda/Artista", "Gênero", "Loja", "Valor" });
		this.tabela.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		if (this.listaCdEscolhido != null) {
			for (int i = 0; i < this.listaCdEscolhido.size(); i++) {
				tabelaModelo.addRow(new String[] { "Álbum", "Banda/Artista", "Gênero", "Loja", "Valor" });
				tabelaModelo.setValueAt(listaCdEscolhido.get(i).getAlbum(), i, 0);
				tabelaModelo.setValueAt(listaCdEscolhido.get(i).getBanda_artista(), i, 1);
				tabelaModelo.setValueAt(listaCdEscolhido.get(i).getGenero(), i, 2);
				tabelaModelo.setValueAt(listaCdEscolhido.get(i).getLoja(), i, 3);
				tabelaModelo.setValueAt(listaCdEscolhido.get(i).getValor(), i, 4);

				renderer.addToolTip(i,
						"<html> <img src=\"file:" + listaCdEscolhido.get(i).caminhoImg() + "\" ></hmtl>");
			}
		}
		this.tabela.setModel(tabelaModelo);
		this.tabela.getColumnModel().getColumn(0).setCellRenderer(renderer);
		this.tabela.getColumnModel().getColumn(1).setCellRenderer(renderer);
		this.tabela.getColumnModel().getColumn(2).setCellRenderer(renderer);
		this.tabela.getColumnModel().getColumn(3).setCellRenderer(renderer);
		this.tabela.getColumnModel().getColumn(4).setCellRenderer(renderer);
		this.tabela.setCursor(Cursor.getDefaultCursor());
	}

	public void pesquisar() {
		this.setListaCdEscolhido(new ArrayList<>());
		this.Palavrapesquisa = this.fildNomeArqSel.getText().trim();

		this.setListaCdEscolhido(pesquisaPrecoFacade.pesquisar(this.Palavrapesquisa));

		if (this.getListaCdEscolhido().size() == 0) {
			JOptionPane.showMessageDialog(null, "Não foi possível localizar nenhuma informação com este filtro!",
					"Atenção", JOptionPane.INFORMATION_MESSAGE);
		} else {
			// Ordena por valor ordem crescente!!
			Collections.sort(this.listaCdEscolhido);
		}
		this.carregaGrid();
	}

	
}
