package hu.elte.web.hajnaldavid.roti.logic.controllers;

import hu.elte.web.hajnaldavid.roti.graphics.dialogs.AddBicycleDialog;
import hu.elte.web.hajnaldavid.roti.graphics.frames.MainFrame;
import hu.elte.web.hajnaldavid.roti.graphics.panels.BicyclesPanel;
import hu.elte.web.hajnaldavid.roti.graphics.tablemodels.GenericTableModel;
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

	public BicycleController(BicyclesPanel mainPanel, TableModelRouter tableModelRouter) {

		super(mainPanel, tableModelRouter);

		mainPanel.setTableModel((GenericTableModel) tableModelRouter
				.getTableModelByName("BicycleTableModel"));

		this.stationDomain = new StationDomain();	
		this.addModal = new AddBicycleDialog();

		mainPanel.init();

		setActionListeners();
	}

	private void setActionListeners() {

		((BicyclesPanel) mainPanel).getAddButton().addActionListener(p -> showAddDialog());

		addModal.getCancelButton().addActionListener(p -> hideAddDialog());

		addModal.getOkButton().addActionListener(p -> createBicycle());

	}

	private void createBicycle() {

		try {

			Station station = getSelectedStation();

			Bicycle bicycle = createBicycleInstance();

			station = stationDomain.addBike(station, bicycle);

			refreshTables();

			addModal.setVisible(false);

		} catch (FullCapacityException e) {
			MainFrame.showError("Az állomás tele van.");
		} catch (NumberFormatException e) {
			MainFrame.showError("Hibás ár: " + e.getMessage());
		} catch (NullPointerException e) {
			MainFrame.showError("Nincs kiválasztott állomás.");
		}catch (Exception e) {
			MainFrame.showError("Rendszer hiba: " + e.getMessage() + ".");
		}

	}

	private void refreshTables() {

		((BicyclesPanel) mainPanel).getTableModel().refresh();

		((GenericTableModel) tableModelRouter
				.getTableModelByName("StationTableModel")).refresh();

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

		String name = addModal.getStationListModel().getSelectedItem()
				.toString();
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

		return stationDomain.readAll().stream()
				.filter(s -> s.getBikes().size() < s.getMaximumCapacity())
				.map(s -> s.getName()).toArray(s -> new String[s]);

	}

	public Component getMainPanel() {
		return mainPanel;
	}

}
