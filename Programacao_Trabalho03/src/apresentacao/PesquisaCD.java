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
	//Campo de filtro escolhido de pesquisa
	private JTextField fildNomeArqSel;
	private JLabel lbText;
	//Faz a pesquisa dos cds e preenche a tabela
	private JButton btPesquisar;
	//Abre outra tela onde será disponibilizado para carregar pesquisa salva.
	private JButton btSelecionar;
	//Salva lista de pesquisa selecionada
	private JButton btSalvar;
	//Tabela com os cds.
	private JTable tabela;
	//Tem a pesquisa feito pelo usuário para depois salvar com o nome
	private String Palavrapesquisa = "";
	//Lista carregada com os cds pesquisados
	private ArrayList<Cd> listaCdEscolhido = null;
	//Controla a lista de cds
	private PesquisaPrecoFacade pesquisaPrecoFacade;
	//Controla se será crescente ou descrecente
	private boolean controleOrdem = true;
	
	//Inicia tela de pesquisa
	public PesquisaCD() {
		//Garante que só terá somente uma instacia
		pesquisaPrecoFacade = PesquisaPrecoFacade.getInstaciaFacade();
		//Carrega os componentes e formata jframe
		this.configuraForm();
	}
	
	//Carrega todas as informações e componentes da tela
	private void configuraForm() {
		//Deixa a tela visivel para usuário
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//Redimenciona tela sendo (largura, altura)
		this.setSize(670, 370);
		this.getContentPane().setBackground(Color.WHITE);
		//Coloca titulo na tela
		this.setTitle("Pesquisa de CD's");
		//Centraliza tela
		this.setLocationRelativeTo(null);
		//Disabilita redimensionamento da tela
		this.setResizable(false);
		this.setAlwaysOnTop(false);
		this.setLayout(null);

		// Adiciona componentes no formulário
		addLabels();
		addButton();
		addTextFild();
		addGrid();
		
		//Seleciona campo de filtro ao abrir tela
		this.fildNomeArqSel.requestFocus();

		// Repinta os componentes na tela
		this.repaint();
	}
	
	//Informa que ao lado deve ser informado um filtro de pesquisa
	private void addLabels() {
		lbText = new JLabel("Descrição");
		lbText.setBounds(10, 25, 200, 25);
		this.add(lbText);
	}
	
	//Campo de filtro
	private void addTextFild() {
		fildNomeArqSel = new JTextField();
		fildNomeArqSel.setBounds(75, 25, 432, 25);
		fildNomeArqSel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				//Ao clicar enter no campo de filtro ele já faz a pesquisa
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
				//Deixa a tela principal invisivel
				setVisible(false);
				//busca lista salva e retorna caso tenha selecionado a mesma
				ArrayList<Cd> listaRetorno = new SelecionarPesquisasSalvas().getPesquisas();
				//Coloca a lista salva na lista sendo utilizada na tela principal
				setListaCdEscolhido(listaRetorno);
				//Carrega tabela com a lista carregada anterior
				carregaGrid();
				//Deixa visivel novamente a tela para o usuário
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
				//Verifica se a lista não esta vazia pois se estiver informa o usuário da mesma
				if (getListaCdEscolhido() == null || getListaCdEscolhido().size() == 0) {
					JOptionPane.showMessageDialog(null, "Lista vazia!", "Atenção", JOptionPane.INFORMATION_MESSAGE);
				} else {
					//Salva lista selecionada no diretorio selecionado com o nome do filtro
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
		
		//Ordena lista ao clicar no titulo da coluna
		tabela.getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Verifica se a lista tem conteudo
				if(listaCdEscolhido != null && listaCdEscolhido.size() > 0){
					//Recupera a coluna selecionada
					int col = tabela.columnAtPoint(e.getPoint());
					
					//Verifica se é a coluna "Álbum"(0), "Banda/Artista"(1) ou "Valor"(4) 
					switch (col) {
					case 0:
						//Verifica se está na ordem crescente
						if (controleOrdem) {
							//Altera ordem para crescente
							Collections.sort(listaCdEscolhido, CdComparador.VALOR_ALBUM);
							controleOrdem = false;
						} else {
							//Altera ordem para decrescente
							Collections.reverse(listaCdEscolhido);
							controleOrdem = true;
						}
						break;
					case 1:
						if (controleOrdem) {
							//Altera ordem para crescente
							Collections.sort(listaCdEscolhido, CdComparador.VALOR_BANDA_ARTISTA);
							controleOrdem = false;
						} else {
							//Altera ordem para decrescente
							Collections.reverse(listaCdEscolhido);
							controleOrdem = true;
						}
						break;
					case 4:
						if (controleOrdem) {
							//Altera ordem para crescente
							Collections.sort(listaCdEscolhido);
							controleOrdem = false;
						} else {
							//Altera ordem para decrescente
							Collections.reverse(listaCdEscolhido);
							controleOrdem = true;
						}
						break;
					}
					//Carrega novamente o grid com a lista ordenada
					carregaGrid();
				}
			}
		});
		
		//Adiciona a barra latareal na tabela 
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
		//Controlador de informação que sera mostrada no momento que fica a linha ganha foco
		CellRendererToolTip renderer = new CellRendererToolTip();
		//Configura tabela novamente
		DefaultTableModel tabelaModelo = new DefaultTableModel(null,
				new String[] { "Álbum", "Banda/Artista", "Gênero", "Loja", "Valor" });
		this.tabela.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		
		//Verifica se tem alguma coisa a ser carregado na lista
		if (this.listaCdEscolhido != null) {
			for (int i = 0; i < this.listaCdEscolhido.size(); i++) {
				//Adiciona nova linha
				tabelaModelo.addRow(new String[] { "Álbum", "Banda/Artista", "Gênero", "Loja", "Valor" });
				tabelaModelo.setValueAt(listaCdEscolhido.get(i).getAlbum(), i, 0);
				tabelaModelo.setValueAt(listaCdEscolhido.get(i).getBanda_artista(), i, 1);
				tabelaModelo.setValueAt(listaCdEscolhido.get(i).getGenero(), i, 2);
				tabelaModelo.setValueAt(listaCdEscolhido.get(i).getLoja(), i, 3);
				tabelaModelo.setValueAt(listaCdEscolhido.get(i).getValor(), i, 4);
				
				//Adiciona no controlador a informação que sera carregada nessa linha
				renderer.addToolTip(i,
						"<html> <img src=\"file:" + listaCdEscolhido.get(i).caminhoImg() + "\" ></hmtl>");
			}
		}
		this.tabela.setModel(tabelaModelo);
		
		//Adiciona nas colunas o controlador de informações
		this.tabela.getColumnModel().getColumn(0).setCellRenderer(renderer);
		this.tabela.getColumnModel().getColumn(1).setCellRenderer(renderer);
		this.tabela.getColumnModel().getColumn(2).setCellRenderer(renderer);
		this.tabela.getColumnModel().getColumn(3).setCellRenderer(renderer);
		this.tabela.getColumnModel().getColumn(4).setCellRenderer(renderer);
		this.tabela.setCursor(Cursor.getDefaultCursor());
	}

	public void pesquisar() {
		//Cria uma lista vazia
		this.setListaCdEscolhido(new ArrayList<>());
		//Guarda filtro informado pelo usuario
		this.Palavrapesquisa = this.fildNomeArqSel.getText().trim();
		
		//Coloca a lista de retorno referente ao filtro na lista principal
		this.setListaCdEscolhido(pesquisaPrecoFacade.pesquisar(this.Palavrapesquisa));
		
		//Informa ao usuario que para este filtro nao a nada
		if (this.getListaCdEscolhido().size() == 0) {
			JOptionPane.showMessageDialog(null, "Não foi possível localizar nenhuma informação com este filtro!",
					"Atenção", JOptionPane.INFORMATION_MESSAGE);
		} else {
			// Ordena por valor ordem crescente!!
			Collections.sort(this.listaCdEscolhido);
		}
		//Carrega na tabela a lista escolhida
		this.carregaGrid();
	}

	
}
