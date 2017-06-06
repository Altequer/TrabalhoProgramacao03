package apresentacao;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import adapters.Cd;

public class SelecionarPesquisasSalvas extends JDialog {

	private static final long serialVersionUID = 1;
	private JButton btCancelar, btSelecionar, btSelecionarDiretorio;
	private JTextField fieldDiretorio;
	private JTable tabela;
	private JLabel lbText, lbDiretorio;

	@SuppressWarnings("rawtypes")
	private HashMap<File, ArrayList> pesquisas = null;


	public SelecionarPesquisasSalvas() {
		super();
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
		this.pesquisas = new HashMap<>();

		// Adiciona componentes no formulário
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

		lbDiretorio = new JLabel("Selecione diretório:");
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

		//Adiciona o botão de Selecionar Selecionae
		btSelecionar = new JButton("Selecionar");
		btSelecionar.setBounds(252, 293, 100, 24);
		btSelecionar.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {	
				dispose();
			}
		});
		this.add(btSelecionar);

		// Adiciona o botão de Cancelar
		btCancelar = new JButton("Cancelar");
		btCancelar.setBounds(354, 293, 100, 24);
		btCancelar.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				pesquisas = null;
				dispose();
			}
		});
		this.add(btCancelar);

		// Adiciona o botão de Selecionar diretório
		btSelecionarDiretorio = new JButton("Selecionar");
		btSelecionarDiretorio.setBounds(354, 20, 100, 24);
		btSelecionarDiretorio.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {				 
				buscaArquivos();
				carregaGrid();
			}
		});
		this.add(btSelecionarDiretorio);
	}

	@SuppressWarnings("rawtypes")
	private void buscaArquivos() {
		JFileChooser fc = new JFileChooser();

		// restringe a amostra a diretorios apenas
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int res = fc.showOpenDialog(null);

		if(res == JFileChooser.APPROVE_OPTION){

			File diretorio = fc.getSelectedFile();

			if(diretorio.isDirectory()){

				fieldDiretorio.setText(diretorio.getAbsolutePath());
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
							ex.printStackTrace();
							JOptionPane.showMessageDialog(null, "Não foi possível carregar o arquivo " + arq.getName() + "!", "Atenção", JOptionPane.ERROR_MESSAGE);
						}	
					}
				}

				this.carregaGrid();
			}else{
				JOptionPane.showMessageDialog(null, "O diretório escolhido é inválido!", "Atenção", JOptionPane.ERROR_MESSAGE);				
			}
		}else
			JOptionPane.showMessageDialog(null, "Não foi possível localizar o caminho escolhido!", "Atenção", JOptionPane.ERROR_MESSAGE);
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
