package hu.elte.web.hajnaldavid.roti.graphics.frames;

import hu.elte.web.hajnaldavid.roti.graphics.panels.BicyclesPanel;
import hu.elte.web.hajnaldavid.roti.graphics.panels.StationsPanel;
import hu.elte.web.hajnaldavid.roti.graphics.panels.dashboard.DashboardPanel;
import hu.elte.web.hajnaldavid.roti.graphics.tablemodels.TableModelFactory;
import hu.elte.web.hajnaldavid.roti.graphics.tablemodels.TableModelRouter;
import hu.elte.web.hajnaldavid.roti.logic.controllers.BicycleController;
import hu.elte.web.hajnaldavid.roti.logic.controllers.DashboardController;
import hu.elte.web.hajnaldavid.roti.logic.controllers.StationController;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Bicycle;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Station;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class MainFrame extends BaseFrame {

	public static final int SIZE_X = 600;
	public static final int SIZE_Y = 400;
	private JTabbedPane jTabbedPane;
	private TableModelRouter tableModelRouter;

	private StationController stationController;
	private BicycleController bicycleController;
	private DashboardController dashboardController;
	
	public static final Color PADDING_BG = new Color(57,52,43);
	public static final Color LIGHT_BG = new Color(125,124,98);
	public static final Color DEFAULT_BG = new Color(195,188,172);
	public static final Color LIGHT_BTN = new Color(217,216,212);
	public static final Color DARK_BTN = new Color(34,34,47);

	public MainFrame() throws HeadlessException {

		setTitle("Roti v0.9");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(40, 60);
		setSize(SIZE_X, SIZE_Y);
		setLayout(new BorderLayout());

		setTableModels();

		stationController = new StationController(new StationsPanel(),
				tableModelRouter);

		bicycleController = new BicycleController(new BicyclesPanel(),
				tableModelRouter);

		dashboardController = new DashboardController(new DashboardPanel(),
				tableModelRouter);

		setTabbedPane();

		getContentPane().add(jTabbedPane, BorderLayout.CENTER);

		getContentPane().setBackground(DEFAULT_BG);

		jTabbedPane.setBackground(DEFAULT_BG);

		jTabbedPane.setUI(new BasicTabbedPaneUI() {
			@Override
			protected void installDefaults() {
				super.installDefaults();					 
				highlight = DEFAULT_BG;
				lightHighlight = DEFAULT_BG;
				shadow = DEFAULT_BG;
				darkShadow = DEFAULT_BG;
				focus = DEFAULT_BG;
			}
		});
	}

	private void setTableModels() {

		tableModelRouter = new TableModelRouter();

		try {

			tableModelRouter.addTableModel("BicycleTableModel",
					TableModelFactory.createTableModel(Bicycle.class,
							new String[] { "Azonosító", "Állomás",
									"Kölcsönzési díj", "Típus" }));

			tableModelRouter.addTableModel("StationTableModel",
					TableModelFactory.createTableModel(Station.class,
							new String[] { "Név", "Kapacitás", "Aktuális",
									"Státusz" }));

		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void setTabbedPane() {
		jTabbedPane = new JTabbedPane();

		jTabbedPane
				.addTab("Vezérlõpult", new JScrollPane(dashboardController.getMainPanel()));
		jTabbedPane.addTab("Állomások",
				new JScrollPane(stationController.getMainPanel()));
		jTabbedPane.addTab("Kerékpárok",
				new JScrollPane(bicycleController.getMainPanel()));
	}

}
