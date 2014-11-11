package hu.elte.web.hajnaldavid.roti.graphics.table;

import hu.elte.web.hajnaldavid.roti.Main;
import hu.elte.web.hajnaldavid.roti.graphics.tablemodels.GenericTableModel;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableRowSorter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TableFactory {
	
	private static final Logger log4j = LogManager.getLogger(Main.class
			.getName());
	
	public static JTable createTable(GenericTableModel tableModel) {

		try {

			JTable table = new JTable();
			TableRowSorter<GenericTableModel> tableSorter = new TableRowSorter(
					tableModel);

			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setModel(tableModel);
			table.setRowSorter(tableSorter);
			table.setAutoCreateRowSorter(true);

			return table;

		} catch (Exception e) {
			log4j.error(e.getMessage());
			return null;
		}

	}

}
