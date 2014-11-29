package hu.elte.web.hajnaldavid.roti.logic.controllers;

import hu.elte.web.hajnaldavid.roti.graphics.frames.MainFrame;
import hu.elte.web.hajnaldavid.roti.graphics.panels.SimulationPanel;
import hu.elte.web.hajnaldavid.roti.graphics.tablemodels.GenericTableModel;
import hu.elte.web.hajnaldavid.roti.graphics.tablemodels.TableModelRouter;
import hu.elte.web.hajnaldavid.roti.logic.domainmodels.LendingDomain;
import hu.elte.web.hajnaldavid.roti.logic.domainmodels.StationDomain;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.EmptyStationException;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.FullCapacityException;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.NonPayAbilityException;
import hu.elte.web.hajnaldavid.roti.persistence.connection.CrudService;
import hu.elte.web.hajnaldavid.roti.persistence.connection.GenericDao;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Customer;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Lending;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Station;
import hu.elte.web.hajnaldavid.roti.persistence.entities.builder.CustomerBuilder;
import hu.elte.web.hajnaldavid.roti.persistence.exception.NoSuchElement;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.SwingWorker;

public class SimulationController extends BasicController {

	private List<Customer> customers = new ArrayList<Customer>();
	private boolean status = true;
	private static int NUMBER_OF_CUSTOMERS = 1;
	private static int SLEEP = 2000;

	private StationDomain stationDomain;
	private LendingDomain lendingDomain;

	public SimulationController(SimulationPanel mainPanel,
			TableModelRouter tableModelRouter) {
		super(mainPanel, tableModelRouter);

		stationDomain = new StationDomain();
		lendingDomain = new LendingDomain();

		mainPanel.init();

		setEventListeners();

	}

	private void setEventListeners() {

		((SimulationPanel) mainPanel).getStartButton().addActionListener(
				p -> doSimulation());
	}

	private Station selectRandomStation() {
		synchronized (this) {
			Random rnd = new Random();
			List<Station> stations = stationDomain.readAll();
			return stations.get(rnd.nextInt(stations.size()));
		}
	}

	private Lending lendBicycle(Customer customer, Station station)
			throws NonPayAbilityException, EmptyStationException, NoSuchElement {

		return lendingDomain.lendRandomBicycle(customer, station);

	}

	private void returnBike(Customer customer, Station station)
			throws FullCapacityException {
		stationDomain.returnBicycle(customer, station);
	}

	private int getSleepTime() {
		try {
			int sleep = Integer.parseInt(((SimulationPanel) mainPanel)
					.getSleepTimeTextArea().getText());
			return sleep;
		} catch (NumberFormatException ex) {
			return SLEEP;
		}
	}

	private int getNumberOfCustomers() {
		try {
			int number = Integer.parseInt(((SimulationPanel) mainPanel)
					.getAmountOfCustomersTextArea().getText());
			return number;
		} catch (NumberFormatException ex) {
			return NUMBER_OF_CUSTOMERS;
		}

	}

	// @TODO refactor
	private void doSimulation() {

		MainFrame.running = !MainFrame.running;

		if (MainFrame.running) {
			((SimulationPanel) mainPanel).getStartButton().setText("Megállít");
		} else {
			((SimulationPanel) mainPanel).getStartButton().setText("Újraindít");
		}

		int sleepTime = getSleepTime();

		int numberOfCustomers = getNumberOfCustomers();

		for (int i = customers.size(); i < numberOfCustomers; i++) {
			customers.add(createCustomer());
		}

		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			public Void doInBackground() throws InterruptedException {

				while (MainFrame.running) {

					for (Customer customer : customers) {

						Station station = selectRandomStation();

						if (customer.getBicycle() != null) {

							log4j.debug("van bringa: " + customer.getName());

							customerReturnBike(station, customer);							
							

						} else {

							log4j.debug("nincs bringa" + customer.getName());

							customerLendBike(station, customer);		

						}

						Thread.sleep(sleepTime);

						updateStationTable(station);
					}

					log4j.debug("sleep");

					// sleep more
					try {
						Thread.sleep(SLEEP);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				return null;
			}

			@Override
			public void done() {
				log4j.debug("done");
				for (Customer customer : customers) {
					Station station = stationDomain
							.readAll()
							.stream()
							.filter(s -> s.getBikes().size() + 1 < s
									.getMaximumCapacity()).findFirst().get();
					try {
						returnBike(customer, station);
					} catch (FullCapacityException e) {					
						e.printStackTrace();
					}
				}
			}
		};

		worker.execute();

	}

	private synchronized void updateStationTable(Station station) {

		((GenericTableModel<Station, CrudService<Station>>) tableModelRouter
				.getTableModelByName("StationTableModel")).update(station);

	}

	private synchronized void customerLendBike(Station station,
			Customer customer) {
		try {

			log(customer.getName() + " kölcsönözni próbál: "
					+ station.getName());
			Lending lending = lendBicycle(customer, station);

			if (lending != null) {
				log(customer.getName() + " felevett egy kerekpárt: "
						+ lending.getBike().getType().toString() + " - "
						+ station.getName());

			}

		} catch (NonPayAbilityException | EmptyStationException | NoSuchElement e1) {
			log(customer.getName() + ": " + e1.getMessage());
			log4j.error(e1.getMessage());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private synchronized void customerReturnBike(Station station,
			Customer customer) {
		try {
			returnBike(customer, station);
			log(customer.getName() + " leadta a kerékpárt: "
					+ station.getName());
		} catch (FullCapacityException e) {
			log(customer.getName()
					+ " nem tudta leadni a kerékpárt, mert tele van az állomás: "
					+ station.getName());
			log4j.debug(e.getMessage());
		}
	}

	private void log(String msg) {
		synchronized (this) {

			StringWriter text = new StringWriter();
			PrintWriter out = new PrintWriter(text);
			String textareaValue = ((SimulationPanel) mainPanel).getTextarea()
					.getText();
			out.println(textareaValue);
			out.printf(msg);
			((SimulationPanel) mainPanel).getTextarea()
					.setText(text.toString());

		}
	}

	private Customer createCustomer() {
		Random rnd = new Random();
		Customer customer = new CustomerBuilder().setName(getRandomName())
				.setCredit(rnd.nextInt(20000)).getInstance();
		customer = new GenericDao<Customer>(Customer.class).create(customer);
		return customer;
	}

	private String getRandomName() {
		String[] last = new String[] { "Nagy", "Kovács", "Illjasov", "Smith",
				"Maximilian", "Sigfrid", "Kis", "Antonio" };
		String[] first = new String[] { "Józsi", "Pista", "Karcsi", "Elemér",
				"Dezsõ", "Poszeidon", "Sulu", "Fjodor" };
		Random rnd = new Random();
		return first[rnd.nextInt(first.length)] + " "
				+ last[rnd.nextInt(last.length)];
	}
}
