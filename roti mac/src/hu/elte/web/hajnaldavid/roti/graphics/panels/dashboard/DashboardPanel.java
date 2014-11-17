package hu.elte.web.hajnaldavid.roti.graphics.panels.dashboard;

import hu.elte.web.hajnaldavid.roti.graphics.frames.MainFrame;
import hu.elte.web.hajnaldavid.roti.graphics.panels.SimulationPanel;
import hu.elte.web.hajnaldavid.roti.graphics.panels.StationsPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JPanel;

public class DashboardPanel extends JPanel {

	private JPanel stationsPanel;
	private JPanel simulationPanel;

	/**
	 * Create the panel.
	 */
	public DashboardPanel() {

		setBackground(MainFrame.PADDING_BG);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 0, 2};
		gridBagLayout.rowHeights = new int[] {0, 0, 2};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		stationsPanel = new StationsPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(stationsPanel, gbc_panel);
		
		simulationPanel = new SimulationPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 0;
		add(simulationPanel, gbc_panel_1);
		
		JButton btnEgyGomb = new JButton("egy gomb");		
		btnEgyGomb.setFont(new Font("Kartika", Font.PLAIN, 11));

		btnEgyGomb.setBackground(new Color(52, 152, 219));
		
		GridBagConstraints gbc_btnEgyGomb = new GridBagConstraints();
		gbc_btnEgyGomb.insets = new Insets(0, 0, 0, 5);
		gbc_btnEgyGomb.gridx = 0;
		gbc_btnEgyGomb.gridy = 1;
		//add(btnEgyGomb, gbc_btnEgyGomb);
		
		JButton btnMgegyGomb = new JButton("m\u00E9gegy gomb");
		btnMgegyGomb.setBackground(SystemColor.control);
		btnMgegyGomb.setBorderPainted(false);
		btnMgegyGomb.setFocusPainted(false);
		btnMgegyGomb.setContentAreaFilled(false);
		
		GridBagConstraints gbc_btnMgegyGomb = new GridBagConstraints();
		gbc_btnMgegyGomb.gridx = 1;
		gbc_btnMgegyGomb.gridy = 1;
		//add(btnMgegyGomb, gbc_btnMgegyGomb);

	}
	
	public void init(){
		((StationsPanel) stationsPanel).init();
		((SimulationPanel) simulationPanel).init();		
	}	

	public JPanel getSimulationPanel() {
		return simulationPanel;
	}

	public JPanel getStationsPanel() {
		return stationsPanel;
	}
	
}
