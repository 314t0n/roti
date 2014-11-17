package hu.elte.web.hajnaldavid.roti.logic.controllers;

import hu.elte.web.hajnaldavid.roti.graphics.panels.SimulationPanel;
import hu.elte.web.hajnaldavid.roti.graphics.panels.StationsPanel;
import hu.elte.web.hajnaldavid.roti.graphics.panels.dashboard.DashboardPanel;
import hu.elte.web.hajnaldavid.roti.graphics.tablemodels.GenericTableModel;
import hu.elte.web.hajnaldavid.roti.graphics.tablemodels.TableModelRouter;
import hu.elte.web.hajnaldavid.roti.persistence.connection.CrudService;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Station;

public class DashboardController extends BasicController {

	private StationsPanel stationsPanel;
	private StationController stationController;
	private SimulationController simulationController;

	@SuppressWarnings("unchecked")
	public DashboardController(DashboardPanel mainPanel,
			TableModelRouter tableModelRouter) {
		super(mainPanel, tableModelRouter);

		stationsPanel = (StationsPanel) mainPanel.getStationsPanel();

		stationsPanel
				.setTableModel((GenericTableModel<Station, CrudService<Station>>) tableModelRouter
						.getTableModelByName("StationTableModel"));

		mainPanel.init();

		stationController = new StationController(
				(StationsPanel) mainPanel.getStationsPanel(), tableModelRouter);

		simulationController = new SimulationController(
				(SimulationPanel) mainPanel.getSimulationPanel(),
				tableModelRouter);
	}

}
