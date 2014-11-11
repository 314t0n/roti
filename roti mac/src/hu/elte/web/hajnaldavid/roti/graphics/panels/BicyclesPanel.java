package hu.elte.web.hajnaldavid.roti.graphics.panels;

import hu.elte.web.hajnaldavid.roti.graphics.frames.MainFrame;
import hu.elte.web.hajnaldavid.roti.graphics.panels.dashboard.BasicSinglePanel;
import hu.elte.web.hajnaldavid.roti.graphics.table.TableFactory;
import hu.elte.web.hajnaldavid.roti.graphics.tablemodels.GenericTableModel;
import hu.elte.web.hajnaldavid.roti.persistence.connection.CrudService;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Bicycle;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Station;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class BicyclesPanel extends BasicSinglePanel {

	private GenericTableModel<Bicycle, CrudService<Bicycle>> tableModel;

	private JButton addButton;

	/**
	 * Create the panel.
	 */
	public BicyclesPanel() {

	}

	public void init() {

		setTable();
		setButtons();

	}

	public void setTable() {

		JTable table = TableFactory.createTable(tableModel);
	
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

	public GenericTableModel<Bicycle, CrudService<Bicycle>> getTableModel() {
		return tableModel;
	}
}
