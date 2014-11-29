package hu.elte.web.hajnaldavid.roti.graphics.panels;

import hu.elte.web.hajnaldavid.roti.graphics.frames.MainFrame;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

public class SimulationPanel extends BasicSinglePanel {

	public static final String START_LABEL = "Szimuláció indítása";

	private JButton startButton;
	private JScrollPane scrollPane;
	private JTextArea textarea;
	private JTextField sleepTimeTextArea;
	private JTextField amountOfCustomersTextArea;

	/**
	 * Create the panel.
	 */
	public SimulationPanel() {

	}

	public void init() {
		super.init();
		initComponents();
		setButtons();

	}

	private void setButtons() {

		startButton.setFont(new Font("Kartika", Font.PLAIN, 11));

		startButton.setBackground(MainFrame.LIGHT_BTN);

	}

	public JButton getStartButton() {
		return startButton;
	}

	public JTextArea getTextarea() {
		return textarea;
	}

	public JTextField getSleepTimeTextArea() {
		return sleepTimeTextArea;
	}

	public JTextField getAmountOfCustomersTextArea() {
		return amountOfCustomersTextArea;
	}

	private void initComponents() {

		textarea = new JTextArea();

		sleepTimeTextArea = new JTextField();

		amountOfCustomersTextArea = new JTextField();

		sleepTimeTextArea.setText("idõ intervallum");

		amountOfCustomersTextArea.setText("vásárlók száma");

		DefaultCaret caret = (DefaultCaret) textarea.getCaret();

		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		scrollPane = new javax.swing.JScrollPane();

		startButton = new javax.swing.JButton();

		scrollPane.setViewportView(textarea);

		startButton.setText(START_LABEL);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														scrollPane,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														375,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		startButton)
																.addComponent(
																		sleepTimeTextArea,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addComponent(
																		amountOfCustomersTextArea,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
								.addContainerGap(15, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(scrollPane,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										230,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(startButton)
												.addComponent(sleepTimeTextArea)
												.addComponent(
														amountOfCustomersTextArea))
								.addContainerGap(30, Short.MAX_VALUE)));
	}

}
