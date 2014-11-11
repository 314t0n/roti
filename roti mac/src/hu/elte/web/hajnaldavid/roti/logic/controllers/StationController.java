package hu.elte.web.hajnaldavid.roti.logic.controllers;

import hu.elte.web.hajnaldavid.roti.graphics.dialogs.AddStationDialog;
import hu.elte.web.hajnaldavid.roti.graphics.frames.MainFrame;
import hu.elte.web.hajnaldavid.roti.graphics.panels.StationsPanel;
import hu.elte.web.hajnaldavid.roti.graphics.tablemodels.GenericTableModel;
import hu.elte.web.hajnaldavid.roti.graphics.tablemodels.TableModelRouter;
import hu.elte.web.hajnaldavid.roti.persistence.connection.CrudService;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Station;
import hu.elte.web.hajnaldavid.roti.persistence.entities.builder.StationBuilder;

public class StationController extends BasicController {

	private AddStationDialog addModal;

	@SuppressWarnings("unchecked")
	public StationController(StationsPanel mainPanel,
			TableModelRouter tableModelRouter) {

		super(mainPanel, tableModelRouter);

		mainPanel.setTableModel((GenericTableModel<Station, CrudService<Station>>) tableModelRouter
				.getTableModelByName("StationTableModel"));

		this.addModal = new AddStationDialog();

		mainPanel.init();

		setActionListeners();
	}

	private void setActionListeners() {

		((StationsPanel) mainPanel).getAddButton().addActionListener(p -> {
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
		((StationsPanel) mainPanel).getTableModel().create(station);
	}

	private StringBuilder checkNewStationData() {

		StringBuilder errorList = new StringBuilder();

		checkEmptyName(errorList).checkNumberField(errorList);

		return errorList;
	}

	private StationController checkNumberField(StringBuilder errorList) {

		String fieldValue = addModal.getStationCapacityField().getText();
		try {
			Integer.parseInt(fieldValue);
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
