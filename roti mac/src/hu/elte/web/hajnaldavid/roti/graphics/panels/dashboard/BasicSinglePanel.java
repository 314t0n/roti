package hu.elte.web.hajnaldavid.roti.graphics.panels.dashboard;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class BasicSinglePanel extends JPanel {

	protected JPanel mainPanel;

	public BasicSinglePanel() {
		mainPanel = new JPanel();
		setMainPanel();
	}

	private void setMainPanel() {
		BorderLayout layout = new BorderLayout();
		mainPanel.setLayout(layout);
		// buttonPanel.setLayout(new FlowLayout());
		// mainPanel.add(buttonPanel, BorderLayout.NORTH);
		this.add(mainPanel);
	}

}
