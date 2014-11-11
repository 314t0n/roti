package hu.elte.web.hajnaldavid.roti;

import hu.elte.web.hajnaldavid.roti.graphics.frames.MainFrame;
import hu.elte.web.hajnaldavid.roti.logic.controllers.StationController;
import hu.elte.web.hajnaldavid.roti.persistence.migrations.DefaultMigration;

import java.awt.EventQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

	private static final Logger log4j = LogManager.getLogger(Main.class
			.getName());

	public static void main(String[] args) {

		log4j.info("Started ... ");
		
		//DefaultMigration.migrateAll();
		
		//log4j.info("Migration ... ");

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {	
					new MainFrame().setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
					MainFrame
							.showError("Hiba: " + ex.getMessage());
					log4j.error(ex.getMessage());
				}
			}
		});

	}

}
