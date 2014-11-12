package hu.elte.web.hajnaldavid.roti.logic.controllers;

import hu.elte.web.hajnaldavid.roti.Main;
import hu.elte.web.hajnaldavid.roti.graphics.tablemodels.TableModelRouter;

import java.awt.Component;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BasicController {
	
	protected static final Logger log4j = LogManager.getLogger(Main.class
			.getName());

	protected TableModelRouter tableModelRouter;
	protected JPanel mainPanel;

	public BasicController(JPanel mainPanel, TableModelRouter tableModelRouter) {
		this.tableModelRouter = tableModelRouter;
		this.mainPanel = mainPanel;	
	}
	
	public Component getMainPanel() {
		return mainPanel;
	}
}
