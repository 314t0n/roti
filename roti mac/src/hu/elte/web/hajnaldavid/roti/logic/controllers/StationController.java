package hu.elte.web.hajnaldavid.roti.logic.controllers;

import hu.elte.web.hajnaldavid.roti.graphics.dialogs.AddStationDialog;
import hu.elte.web.hajnaldavid.roti.graphics.dialogs.TransferBicycleDialog;
import hu.elte.web.hajnaldavid.roti.graphics.frames.MainFrame;
import hu.elte.web.hajnaldavid.roti.graphics.panels.StationsPanel;
import hu.elte.web.hajnaldavid.roti.graphics.tablemodels.GenericTableModel;
import hu.elte.web.hajnaldavid.roti.graphics.tablemodels.TableModelRouter;
import hu.elte.web.hajnaldavid.roti.logic.domainmodels.StationDomain;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.EmptyStationException;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.FullCapacityException;
import hu.elte.web.hajnaldavid.roti.persistence.connection.CrudService;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Station;
import hu.elte.web.hajnaldavid.roti.persistence.entities.builder.StationBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class StationController extends BasicController {

	private AddStationDialog addModal;
	private TransferBicycleDialog transferModal;
	private StationDomain stationDomain;

	@SuppressWarnings("unchecked")
	public StationController(StationsPanel mainPanel,
			TableModelRouter tableModelRouter) {

		super(mainPanel, tableModelRouter);

		mainPanel
				.setTableModel((GenericTableModel<Station, CrudService<Station>>) tableModelRouter
						.getTableModelByName("StationTableModel"));

		this.addModal = new AddStationDialog();

		this.transferModal = new TransferBicycleDialog();

		stationDomain = new StationDomain();

		mainPanel.init();

		setActionListeners();
	}

	/**
	 * 
	 */
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

		((StationsPanel) mainPanel).getTransferButton().addActionListener(
				p -> showTrasnferDialog());

		transferModal.getCancelButton().addActionListener(
				p -> transferModal.setVisible(false));

		transferModal.getOkButton().addActionListener(p -> {

			StringBuilder notifications = transferBicycle();

			if (notifications.length() == 0) {

				transferModal.setVisible(false);

			}else{
				MainFrame.showError(notifications.toString());
			}
		});

	}

	private void showTrasnferDialog() {
		transferModal.setFromStations(getStationList());
		transferModal.setToStations(getStationList());
		transferModal.setVisible(true);
	}

	private String[] getStationList() {

		return stationDomain
				.readAll()
				.stream()
				// .filter(s -> s.getBikes().size() < s.getMaximumCapacity())
				.map(s -> s.getName() + " - (" + s.getBikes().size() + "\\"
						+ s.getMaximumCapacity() + ")")
				.toArray(s -> new String[s]);

	}

	private StringBuilder transferBicycle() {
		StringBuilder errors = new StringBuilder();
		checkAmount(errors).doTransferBike(errors);
		return errors;
	}

	private StationController checkAmount(StringBuilder errorList) {
		String fieldValue = transferModal.getAmount().getText();

		try {
			Integer.parseInt(fieldValue);
		} catch (NumberFormatException e) {
			errorList.append("A mennyiség hibás szám.");
			errorList.append("\n");
		}
		return this;
	}

	private StationController doTransferBike(StringBuilder errorList) {
		String from = null, to = null;
		try {
			from = getStationName(transferModal.getFromStation()
					.getSelectedItem().toString());

			to = getStationName(transferModal.getToStation().getSelectedItem()
					.toString());

			Station stationFrom = stationDomain.findByName(from);
			Station stationTo = stationDomain.findByName(to);
			stationDomain.transferBike(stationFrom, stationTo);

		} catch (EmptyStationException | FullCapacityException ex) {
			errorList.append(ex.getMessage());
			errorList.append("\n");
		} catch (NoSuchElementException ex) {
			log4j.error(ex.getMessage() + ", " + from + ", " + to);
		}
		return this;

	}

	/**
	 * @todo values[0] is evil
	 * @param fieldValue
	 * @return
	 */
	private String getStationName(String fieldValue) {
		String[] values = fieldValue.split("-");
		return values[0].trim();
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
