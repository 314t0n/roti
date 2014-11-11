package hu.elte.web.hajnaldavid.roti.graphics.panels;

import hu.elte.web.hajnaldavid.roti.Main;
import hu.elte.web.hajnaldavid.roti.graphics.frames.MainFrame;
import hu.elte.web.hajnaldavid.roti.graphics.panels.dashboard.BasicSinglePanel;
import hu.elte.web.hajnaldavid.roti.graphics.table.TableFactory;
import hu.elte.web.hajnaldavid.roti.graphics.tablemodels.GenericTableModel;
import hu.elte.web.hajnaldavid.roti.persistence.connection.CrudService;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Station;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StationsPanel extends BasicSinglePanel {

	private static final Logger log4j = LogManager.getLogger(Main.class
			.getName());

	private GenericTableModel<Station, CrudService<Station>> tableModel;

	private JButton addButton;

	/**
	 * Create the panel.
	 */
	public StationsPanel() {

	}

	public void init() {
		setBackground(new Color(245, 215, 110));
		setTable();
		setButtons();

	}

	public void setTable() {

		JTable table = TableFactory.createTable(tableModel);

		/*table.setBackground(new Color(238, 238, 238));

		table.setGridColor(new Color(0, 0, 0));

		table.setForeground(new Color(0, 0, 0));*/
	
		JScrollPane scrollPane = new JScrollPane(table);
		table.setPreferredScrollableViewportSize(new Dimension(
				MainFrame.SIZE_X - 50, 200));

		mainPanel.add(scrollPane, BorderLayout.CENTER);

	}

	public void setTableModel(

	GenericTableModel genericTableModel) {

		this.tableModel = genericTableModel;

	}

	private void setButtons() {

		addButton = new JButton("Felvesz");

		addButton.setFont(new Font("Kartika", Font.PLAIN, 11));

		addButton.setBackground(new Color(52, 152, 219));

		mainPanel.add(addButton, BorderLayout.SOUTH);

	}

	public JButton getAddButton() {
		return addButton;
	}

	public GenericTableModel<Station, CrudService<Station>> getTableModel() {
		return tableModel;
	}

}
