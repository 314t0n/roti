package hu.elte.web.hajnaldavid.roti.graphics.panels;

import hu.elte.web.hajnaldavid.roti.Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BasicSinglePanel extends JPanel {

	protected JPanel mainComponentPanel;
	
	protected static final Logger log4j = LogManager.getLogger(Main.class
			.getName());

	public BasicSinglePanel() {
		mainComponentPanel = new JPanel();
		this.add(mainComponentPanel, BorderLayout.CENTER);
	}


	public void init() {
		setBackground(new Color(242, 226, 220));		
	}
	
}
