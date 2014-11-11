package hu.elte.web.hajnaldavid.roti.graphics.panels.dashboard;

import java.awt.BorderLayout;
import java.awt.SystemColor;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public class StationPanel extends JPanel {
	private JTable table;

	/**
	 * Create the panel.
	 */
	public StationPanel() {
		setBackground(SystemColor.activeCaption);
		setLayout(new BorderLayout(0, 0));
		
		JLabel lblllomsok = new JLabel("\u00C1llom\u00E1sok");
		lblllomsok.setBackground(SystemColor.activeCaption);
		add(lblllomsok, BorderLayout.NORTH);
		
		table = new JTable();
		table.setBackground(SystemColor.activeCaption);
		add(table, BorderLayout.CENTER);
		
	}

}
