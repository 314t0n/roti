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
import javax.swing.JTextArea;

public class SimulationPanel extends BasicSinglePanel {
	
	public static final String START_LABEL = "Szimuláció indítása";

	private JButton startButton;
    private JScrollPane scrollPane;
	private JTextArea textarea;

	/**
	 * Create the panel.
	 */
	public SimulationPanel() {

	}

	public void init() {
		super.init();		
		initComponents();
		setButtons();

	}

	private void setButtons() {

		startButton.setFont(new Font("Kartika", Font.PLAIN, 11));

		startButton.setBackground(MainFrame.LIGHT_BTN);
	}

	public JButton getStartButton() {
		return startButton;
	}	
	
	public JTextArea getTextarea() {
		return textarea;
	}

	private void initComponents() {
		
		textarea = new JTextArea();

        scrollPane = new javax.swing.JScrollPane();
       
        startButton = new javax.swing.JButton();
         
        scrollPane.setViewportView(textarea);

        startButton.setText(START_LABEL);
       
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(startButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                      ))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startButton)
                   )
                .addContainerGap(30, Short.MAX_VALUE))
        );
    }   
 
}
