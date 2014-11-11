package hu.elte.web.hajnaldavid.roti.logic.controllers;

import hu.elte.web.hajnaldavid.roti.Main;
import hu.elte.web.hajnaldavid.roti.graphics.dialogs.AddBicycleDialog;
import hu.elte.web.hajnaldavid.roti.graphics.panels.BicyclesPanel;
import hu.elte.web.hajnaldavid.roti.graphics.tablemodels.GenericTableModel;
import hu.elte.web.hajnaldavid.roti.graphics.tablemodels.TableModelRouter;
import hu.elte.web.hajnaldavid.roti.logic.domainmodels.StationDomain;
import hu.elte.web.hajnaldavid.roti.persistence.connection.GenericDao;
import hu.elte.web.hajnaldavid.roti.persistence.entities.RotiEntity;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Station;

import java.awt.Component;
import java.util.List;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BasicController {
	
	protected static final Logger log4j = LogManager.getLogger(Main.class
			.getName());

	protected TableModelRouter tableModelRouter;
	protected JPanel mainPanel;

	@SuppressWarnings("unchecked")
	public BasicController(JPanel mainPanel, TableModelRouter tableModelRouter) {
		this.tableModelRouter = tableModelRouter;
		this.mainPanel = mainPanel;	
	}
	
	public Component getMainPanel() {
		return mainPanel;
	}
}
