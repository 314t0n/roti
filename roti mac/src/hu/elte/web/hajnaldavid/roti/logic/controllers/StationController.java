package hu.elte.web.hajnaldavid.roti.logic.controllers;

import hu.elte.web.hajnaldavid.roti.graphics.dialogs.AddStationDialog;
import hu.elte.web.hajnaldavid.roti.graphics.dialogs.TransferBicycleDialog;
import hu.elte.web.hajnaldavid.roti.graphics.frames.MainFrame;
import hu.elte.web.hajnaldavid.roti.graphics.panels.StationsPanel;
import hu.elte.web.hajnaldavid.roti.graphics.tablemodels.TableModelRouter;
import hu.elte.web.hajnaldavid.roti.logic.domainmodels.StationDomain;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.EmptyStationException;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.FullCapacityException;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.SameStationException;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Station;
import hu.elte.web.hajnaldavid.roti.persistence.entities.builder.StationBuilder;

import java.util.NoSuchElementException;

public class StationController extends BasicController {

	private AddStationDialog addModal;
	private TransferBicycleDialog transferModal;
	private StationDomain stationDomain;

	@SuppressWarnings("unchecked")
	public StationController(StationsPanel mainPanel,
			TableModelRouter tableModelRouter) {

		super(mainPanel, tableModelRouter);

		mainPanel.setTableModel(tableModelRouter
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

			} else {
				MainFrame.showError(notifications.toString());
			}
		});

	}

	private void showTrasnferDialog() {
		transferModal.setFromStations(getStationList());
		transferModal.setToStations(getFreeStationList());
		transferModal.setVisible(true);
	}

	private String[] getStationList() {

		return stationDomain
				.readAll()
				.stream()
				.map(s -> s.getName() + " - (" + s.getBikes().size() + "\\"
						+ s.getMaximumCapacity() + ")")
				.toArray(s -> new String[s]);

	}

	private String[] getFreeStationList() {

		return stationDomain
				.readAll()
				.stream()
				.filter(s -> s.getBikes().size() < s.getMaximumCapacity())
				.map(s -> s.getName() + " - (" + s.getBikes().size() + "\\"
						+ s.getMaximumCapacity() + ")")
				.toArray(s -> new String[s]);

	}

	private StringBuilder transferBicycle() {
		StringBuilder errors = new StringBuilder();
		try {
			checkAmount(errors).doTransferBike(errors);
		} catch (Exception e) {

		}
		return errors;
	}

	private StationController checkAmount(StringBuilder errorList)
			throws Exception {
		String fieldValue = transferModal.getAmount().getText();

		if (!(fieldValue.length() > 0)) {
			errorList.append("A mennyis�g k�telez� mez�.");
			errorList.append("\n");
			throw new Exception();
		}

		try {
			Integer.parseInt(fieldValue);
		} catch (NumberFormatException e) {
			errorList.append("A mennyis�g hib�s sz�m.");
			errorList.append("\n");
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	private StationController doTransferBike(StringBuilder errorList) {

		Integer amount = Integer.parseInt(transferModal.getAmount().getText());

		String from = null, to = null;
		try {
			from = getStationName(transferModal.getFromStation()
					.getSelectedItem().toString());

			to = getStationName(transferModal.getToStation().getSelectedItem()
					.toString());

			Station stationFrom = stationDomain.findByName(from);
			Station stationTo = stationDomain.findByName(to);

			if (stationFrom.equals(stationTo)) {
				throw new SameStationException("V�lassz k�l�nb�z� �llom�st!");
			}

			if (amount > stationFrom.getBikes().size()) {
				throw new Exception("T�l nagy mennyis�g!");
			}

			stationDomain.transferBike(stationFrom, stationTo, amount);

			stationFrom = stationDomain.update(stationFrom);

			stationTo = stationDomain.update(stationTo);

			tableModelRouter.getTableModelByName("StationTableModel").update(
					stationFrom);

		} catch (EmptyStationException | FullCapacityException
				| SameStationException ex) {
			errorList.append(ex.getMessage());
			errorList.append("\n");
		} catch (NoSuchElementException ex) {
			log4j.error(ex.getMessage() + ", " + from + ", " + to);
		} catch (Exception ex) {
			errorList.append(ex.getMessage());
			errorList.append("\n");
		}
		return this;

	}

	/**
	 * @todo values[0]
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
			Integer capactiy = Integer.parseInt(fieldValue);
			if (!(capactiy > 0)) {
				errorList.append("Az �llom�s kapacit�sa hib�s �rt�k");
				errorList.append("\n");
			}
		} catch (NumberFormatException e) {
			errorList.append("Az �llom�s kapacit�sa hib�s sz�m");
			errorList.append("\n");
		}
		return this;
	}

	private StationController checkEmptyName(StringBuilder errorList) {

		if (addModal.getStationNameField().getText().isEmpty()) {
			errorList.append("Az �llom�s neve k�telez�");
			errorList.append("\n");
		}
		return this;
	}

}
