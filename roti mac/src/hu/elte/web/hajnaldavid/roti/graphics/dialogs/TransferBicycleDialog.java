package hu.elte.web.hajnaldavid.roti.graphics.dialogs;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class TransferBicycleDialog extends BasicFormDialog {

	private JTextField amount;
	private JComboBox<String> fromStation;
	private JComboBox<String> toStation;
	private DefaultComboBoxModel<String> fromList;
	private DefaultComboBoxModel<String> toList;

	/**
	 * Create the panel.
	 */
	public TransferBicycleDialog() {
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"), }));

		JLabel lblNewLabel = new JLabel("Mennyiség");
		contentPanel.add(lblNewLabel, "2, 2, right, default");

		amount = new JTextField();
		contentPanel.add(amount, "4, 2, fill, default");
		amount.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Honnan");
		contentPanel.add(lblNewLabel_1, "2, 4, right, default");
		
		fromStation = new JComboBox<String>();
		toStation = new JComboBox<String>();
		fromList = new DefaultComboBoxModel<String>();
		toList = new DefaultComboBoxModel<String>();

		contentPanel.add(fromStation, "4, 4, fill, fill");

		JLabel lblHova = new JLabel("Hova");
		contentPanel.add(lblHova, "2, 6");

		contentPanel.add(toStation, "4, 6, fill, fill");

		fromStation.setModel(fromList);
		toStation.setModel(toList);

	}

	private void setStations(String[] stations,
			DefaultComboBoxModel<String> list) {

		list.removeAllElements();

		for (String station : stations) {
			list.addElement(station);
		}
		
	}

	public void setFromStations(String[] stations) {
		setStations(stations, fromList);
		refresh();
	}
	
	public void setToStations(String[] stations) {
		setStations(stations, toList);
		refresh();
	}


	public JTextField getAmount() {
		return amount;
	}

	public JComboBox<String> getFromStation() {
		return fromStation;
	}

	public JComboBox<String> getToStation() {
		return toStation;
	}

	public DefaultComboBoxModel<String> getFromList() {
		return fromList;
	}

	public DefaultComboBoxModel<String> getToList() {
		return toList;
	}

}
