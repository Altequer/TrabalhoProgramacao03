package apresentacao;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class CellRendererToolTip extends DefaultTableCellRenderer{
	// Mantém todos os tooltips com suas linhas.   
    private Map<Long, String> tooltip = new HashMap<Long, String>();  
      
    // Mantém a linha atual que este objeto está renderizando.   
    private int row;  
      
    // Busca qual é a linha atual.   
    public Component getTableCellRendererComponent(JTable table, Object value,  
            boolean isSelected, boolean hasFocus, int row, int column) {  
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);  
          
        this.row = row;  
          
        return c;   
    }  
      
    // Retorna o tooltip baseado no map.   
    public String getToolTipText() {  
        return tooltip.get(new Long(row));  
    }  
      
    // Adiciona um tooltip pela linha.   
    public void addToolTip(int row, String text) {  
        tooltip.put(new Long(row), text);  
    }  
}
