package hu.elte.web.hajnaldavid.roti.graphics.panels;

import hu.elte.web.hajnaldavid.roti.graphics.frames.MainFrame;
import hu.elte.web.hajnaldavid.roti.graphics.table.TableFactory;
import hu.elte.web.hajnaldavid.roti.graphics.tablemodels.GenericTableModel;
import hu.elte.web.hajnaldavid.roti.persistence.connection.CrudService;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Bicycle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class BicyclesPanel extends BasicSinglePanel {

	private GenericTableModel<Bicycle, CrudService<Bicycle>> tableModel;
	public static final String ADD_LABEL = "Kerékpár felvétel";

	private JButton addButton;
	private JScrollPane scrollPane;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public BicyclesPanel() {

	}

	public void init() {
		super.init();
		setTable();
		initComponents();
		setButtons();

	}

	public void setTable() {

		table = TableFactory.createTable(tableModel);

		table.setPreferredScrollableViewportSize(new Dimension(
				MainFrame.SIZE_X - 50, 200));
	

	}

	public void setTableModel(

	GenericTableModel<Bicycle, CrudService<Bicycle>> genericTableModel) {

		this.tableModel = genericTableModel;

	}

	private void setButtons() {

		addButton.setFont(new Font("Kartika", Font.PLAIN, 11));

		addButton.setBackground(MainFrame.LIGHT_BTN);

	}

	private void initComponents() {

		scrollPane = new javax.swing.JScrollPane();

		addButton = new javax.swing.JButton();

		scrollPane.setViewportView(table);

		addButton.setText(ADD_LABEL);

		table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														scrollPane,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														375,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		addButton)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
								.addContainerGap(15, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(scrollPane,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										230,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(addButton))
								.addContainerGap(30, Short.MAX_VALUE)));
	}

	public JButton getAddButton() {
		return addButton;
	}

	public GenericTableModel<Bicycle, CrudService<Bicycle>> getTableModel() {
		return tableModel;
	}
}
