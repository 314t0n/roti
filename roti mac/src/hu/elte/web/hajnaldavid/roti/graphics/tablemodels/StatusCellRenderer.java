package hu.elte.web.hajnaldavid.roti.graphics.tablemodels;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class StatusCellRenderer extends DefaultTableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		Component c = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);		

		if (column == 3) {
			c.setBackground(getColorByStatus(value.toString()));
		}

		return c;
	}

	private Color getColorByStatus(String status) {
		switch (status) {
		case "Rendben":
			return new Color(192, 237, 192);
		case "Vészesen alacsony":
			return new Color(237, 196, 192);
		case "Alacsony":
			return new Color(237, 223, 192);			
		}
		return new Color(250, 250, 250);
	}
}
