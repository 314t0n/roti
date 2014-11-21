package hu.elte.web.hajnaldavid.roti.graphics.tablemodels;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class StatusCellRenderer extends DefaultTableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		Component c = super.getTableCellRendererComponent(table, value,	isSelected, hasFocus,  row,  column);
		
		System.out.println(value);
		
		if(column==3){
			c.setBackground(new Color(200,40,30));
		}
		
		return c;
	}
}
