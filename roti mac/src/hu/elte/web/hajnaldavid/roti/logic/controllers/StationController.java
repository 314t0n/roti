package hu.elte.web.hajnaldavid.roti.logic.controllers;

import hu.elte.web.hajnaldavid.roti.Main;
import hu.elte.web.hajnaldavid.roti.graphics.dialogs.AddStationDialog;
import hu.elte.web.hajnaldavid.roti.graphics.frames.MainFrame;
import hu.elte.web.hajnaldavid.roti.graphics.panels.StationsPanel;
import hu.elte.web.hajnaldavid.roti.graphics.tablemodels.GenericTableModel;
import hu.elte.web.hajnaldavid.roti.graphics.tablemodels.TableModelFactory;
import hu.elte.web.hajnaldavid.roti.graphics.tablemodels.TableModelRouter;
import hu.elte.web.hajnaldavid.roti.logic.domainmodels.BicycleDomain;
import hu.elte.web.hajnaldavid.roti.logic.domainmodels.StationDomain;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Bicycle;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Station;
import hu.elte.web.hajnaldavid.roti.persistence.entities.builder.StationBuilder;

import java.util.List;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StationController {

	private static final Logger log4j = LogManager.getLogger(Main.class
			.getName());

	private StationDomain<Station> stationDomain;
	private BicycleDomain<Bicycle> bicycleDomain;
	private StationsPanel mainPanel;
	private AddStationDialog addModal;

	public StationController(StationDomain<Station> dao,
			StationsPanel mainPanel, TableModelRouter tableModelRouter) {

		mainPanel.setTableModel((GenericTableModel) tableModelRouter
				.getTableModelByName("StationTableModel"));

		bicycleDomain = new BicycleDomain<Bicycle>(Bicycle.class);

		this.stationDomain = dao;
		this.mainPanel = mainPanel;
		this.addModal = new AddStationDialog();

		mainPanel.init();

		setActionListeners();
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	private List<Bicycle> getBicycles() {
		return bicycleDomain.readAll();
	}

	private void setActionListeners() {

		mainPanel.getAddButton().addActionListener(p -> {		
			addModal.setVisible(true);
		});

		addModal.getCancelButton().addActionListener(
				p -> addModal.setVisible(false));

		addModal.getOkButton().addActionListener(p -> {

			StringBuilder notifications = checkNewStationData();

			if (notifications.length() == 0) {

				saveStation();

				addModal.setVisible(false);

			} else {

				MainFrame.showError(notifications.toString());

			}

		});

	}

	private void saveStation() {

		Station station = new StationBuilder()
				.setName(addModal.getStationNameField().getText())
				.setMaximumCapacity(
						Integer.parseInt(addModal.getStationCapacityField()
								.getText())).getInstance();

		createStation(station);

	}

	private void createStation(Station station) {
		mainPanel.getTableModel().create(station);
	}

	private StringBuilder checkNewStationData() {

		StringBuilder errorList = new StringBuilder();

		checkEmptyName(errorList).checkNumberField(errorList);

		return errorList;
	}

	private StationController checkNumberField(StringBuilder errorList) {

		String fieldValue = addModal.getStationCapacityField().getText();
		try {
			int value = Integer.parseInt(fieldValue);
		} catch (NumberFormatException e) {
			errorList.append("Az állomás kapacitása hibás szám");
			errorList.append("\n");
		}
		return this;
	}

	private StationController checkEmptyName(StringBuilder errorList) {

		if (addModal.getStationNameField().getText().isEmpty()) {
			errorList.append("Az állomás neve kötelezõ");
			errorList.append("\n");
		}
		return this;
	}

}
