package hu.elte.web.hajnaldavid.roti.logic.controllers;

import hu.elte.web.hajnaldavid.roti.graphics.dialogs.AddBicycleDialog;
import hu.elte.web.hajnaldavid.roti.graphics.frames.MainFrame;
import hu.elte.web.hajnaldavid.roti.graphics.panels.BicyclesPanel;
import hu.elte.web.hajnaldavid.roti.graphics.tablemodels.TableModelRouter;
import hu.elte.web.hajnaldavid.roti.logic.domainmodels.StationDomain;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.FullCapacityException;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Bicycle;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Station;
import hu.elte.web.hajnaldavid.roti.persistence.entities.builder.BicycleBuilder;

import java.awt.Component;

public class BicycleController extends BasicController {

	private StationDomain stationDomain;
	private AddBicycleDialog addModal;

	@SuppressWarnings("unchecked")
	public BicycleController(BicyclesPanel mainPanel,
			TableModelRouter tableModelRouter) {

		super(mainPanel, tableModelRouter);

		mainPanel.setTableModel(tableModelRouter
				.getTableModelByName("BicycleTableModel"));

		this.stationDomain = new StationDomain();
		this.addModal = new AddBicycleDialog();

		mainPanel.init();

		setActionListeners();
	}

	private void setActionListeners() {

		((BicyclesPanel) mainPanel).getAddButton().addActionListener(
				p -> showAddDialog());

		addModal.getCancelButton().addActionListener(p -> hideAddDialog());

		addModal.getOkButton().addActionListener(p -> addBicycles());

	}

	private void addBicycles() {

		Integer amount = getBicycleAmount();

		Station station = getSelectedStation();

		if (station.getBikes().size() + amount <= station.getMaximumCapacity()) {

			for (int i = 0; i < amount; i++) {

				createBicycle();

			}

			refreshTables();

			addModal.setVisible(false);

		} else {
			MainFrame.showError("Az állomásra nem fér ennyi kerékpár.");
		}
	}

	private void createBicycle() {

		try {

			Station station = getSelectedStation();

			Bicycle bicycle = createBicycleInstance();

			station = stationDomain.addBike(station, bicycle);

		} catch (FullCapacityException e) {
			MainFrame.showError("Az állomás tele van.");
		} catch (NumberFormatException e) {
			MainFrame.showError("Hibás szám [ár]");
		} catch (NullPointerException e) {
			e.printStackTrace();
			MainFrame.showError("Nincs kiválasztott állomás.");
		} catch (Exception e) {
			MainFrame.showError("Rendszer hiba: " + e.getMessage() + ".");
		}

	}

	private Integer getBicycleAmount() {
		if (addModal.getAmountOfBicycles().getText().length() > 0) {

			try {

				Integer amount = Integer.parseInt(addModal
						.getAmountOfBicycles().getText());

				return amount;

			} catch (NumberFormatException e) {
				MainFrame.showError("Hibás szám [mennyiség]");
			}

		}

		return 0;
	}

	private void refreshTables() {

		((BicyclesPanel) mainPanel).getTableModel().refresh();

		(tableModelRouter.getTableModelByName("StationTableModel")).refresh();

	}

	private Bicycle createBicycleInstance() {

		int price = getLendingPrice();
		String type = getBicycleType();

		return new BicycleBuilder().setLendingPrice(price)
				.setType(Bicycle.BikeType.valueOf(type)).getInstance();

	}

	private String getBicycleType() {
		return addModal.getBikeTypesListModel().getSelectedItem().toString();
	}

	private Station getSelectedStation() {

		String textValue = addModal.getStationListModel().getSelectedItem()
				.toString();

		String name = textValue.replaceAll("[(\\d/\\d)]", "").trim();

		return stationDomain.findByName(name);

	}

	private int getLendingPrice() {
		int price = Integer.parseInt(addModal.getLendingPriceField().getText());
		if (price > 0) {
			return price;
		}
		throw new NumberFormatException("Negatív ár");
	}

	private void showAddDialog() {
		addModal.setBicycleTypes(Bicycle.BikeType.values());
		addModal.setStations(getStationList());
		addModal.setVisible(true);
	}

	private void hideAddDialog() {
		addModal.setVisible(false);
	}

	private String[] getStationList() {

		return stationDomain
				.readAll()
				.stream()
				.filter(s -> s.getBikes().size() < s.getMaximumCapacity())
				.map(s -> s.getName() + " (" + s.getBikes().size() + "/"
						+ s.getMaximumCapacity() + ")")
				.toArray(s -> new String[s]);

	}

	public Component getMainPanel() {
		return mainPanel;
	}

}
