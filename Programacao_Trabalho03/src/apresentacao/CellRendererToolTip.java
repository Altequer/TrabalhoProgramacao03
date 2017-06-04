package apresentacao;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class CellRendererToolTip extends DefaultTableCellRenderer{
	// Mant�m todos os tooltips com suas linhas.   
    private Map<Long, String> tooltip = new HashMap<Long, String>();  
      
    // Mant�m a linha atual que este objeto est� renderizando.   
    private int row;  
      
    // Busca qual � a linha atual.   
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
