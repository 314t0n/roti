package hu.elte.web.hajnaldavid.roti.graphics.panels;

import hu.elte.web.hajnaldavid.roti.graphics.frames.MainFrame;
import hu.elte.web.hajnaldavid.roti.graphics.table.TableFactory;
import hu.elte.web.hajnaldavid.roti.graphics.tablemodels.GenericTableModel;
import hu.elte.web.hajnaldavid.roti.persistence.connection.CrudService;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Station;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class StationsPanel extends BasicSinglePanel {

	private GenericTableModel<Station, CrudService<Station>> tableModel;
	
	public static final String ADD_LABEL = "Állomás felvétel";
	public static final String TRANSFER_LABEL = "Kerékpár átszállítás";

	private JButton addButton;

    private JButton transferButton;
    private JScrollPane scrollPane;

	private JTable table;

	/**
	 * Create the panel.
	 */
	public StationsPanel() {

	}

	public void init() {
		super.init();
		setTable();
		setButtons();
	}

	public void setTable() {

		table = TableFactory.createTable(tableModel);
		
		initComponents();

	}

	public void setTableModel(

	GenericTableModel<Station, CrudService<Station>> genericTableModel) {

		this.tableModel = genericTableModel;

	}

	private void setButtons() {

		addButton.setFont(new Font("Kartika", Font.PLAIN, 11));

		addButton.setBackground(MainFrame.LIGHT_BTN);

		transferButton.setFont(new Font("Kartika", Font.PLAIN, 11));

		transferButton.setBackground(MainFrame.LIGHT_BTN);
	}

	public JButton getTransferButton() {
		return transferButton;
	}

	public JButton getAddButton() {
		return addButton;
	}
	
	public GenericTableModel<Station, CrudService<Station>> getTableModel() {
		return tableModel;
	}

	
	private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
       
        addButton = new javax.swing.JButton();
        transferButton = new javax.swing.JButton();
     
        scrollPane.setViewportView(table);

        addButton.setText(ADD_LABEL);

        transferButton.setText(TRANSFER_LABEL);
        
        table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(transferButton)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton)
                    .addComponent(transferButton))
                .addContainerGap(30, Short.MAX_VALUE))
        );
    }   
 
}
