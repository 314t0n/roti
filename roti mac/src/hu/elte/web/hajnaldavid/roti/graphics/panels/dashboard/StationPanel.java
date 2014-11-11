package hu.elte.web.hajnaldavid.roti.graphics.panels.dashboard;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.UIManager;
import java.awt.SystemColor;

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
