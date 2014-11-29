package hu.elte.web.hajnaldavid.roti.graphics.dialogs;

import hu.elte.web.hajnaldavid.roti.persistence.entities.Bicycle;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Station;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class AddBicycleDialog extends BasicFormDialog {

	private JTextField lendingPriceField;
	private JComboBox<String> bikesComboBox;
	private JComboBox<String> stationsComboBox;
	private DefaultComboBoxModel<String> bikeTypesListModel;
	private DefaultComboBoxModel<String> stationListModel;
	private JTextField amountOfBicycles;

	public AddBicycleDialog() {
		super();
		contentPanel
				.setLayout(new FormLayout(new ColumnSpec[] {
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));

		JLabel lblNewLabel_1 = new JLabel("Kölcsönzési díj");
		contentPanel.add(lblNewLabel_1, "2, 2, right, default");

		lendingPriceField = new JTextField();
		contentPanel.add(lendingPriceField, "4, 2, fill, default");
		lendingPriceField.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Típus");
		contentPanel.add(lblNewLabel_2, "2, 4, right, default");

		bikeTypesListModel = new DefaultComboBoxModel<String>();
		
		bikesComboBox = new JComboBox<String>(bikeTypesListModel);

		contentPanel.add(bikesComboBox, "4, 4");

		stationListModel = new DefaultComboBoxModel<String>();

		JLabel lblNewLabel_3 = new JLabel("Állomás");
		contentPanel.add(lblNewLabel_3, "2, 6, right, default");

		stationsComboBox = new JComboBox<String>(stationListModel);

		contentPanel.add(stationsComboBox, "4, 6");
		
		amountOfBicycles = new JTextField();
		
		JLabel lblNewLabel_4 = new JLabel("Darabszám (opcionális)");
		contentPanel.add(lblNewLabel_4, "2, 8, right, default");

		contentPanel.add(amountOfBicycles, "4, 8");

	}

	public void setBicycleTypes(Bicycle.BikeType[] types) {

		bikeTypesListModel.removeAllElements();

		for (Bicycle.BikeType type : types) {
			bikeTypesListModel.addElement(type.toString());
		}

		refresh();
	}

	public void setStations(String[] stations) {

		stationListModel.removeAllElements();

		for (String station : stations) {
			stationListModel.addElement(station);
		}

		refresh();
	}

	public JTextField getLendingPriceField() {
		return lendingPriceField;
	}

	public JTextField getStationCapacityField() {
		return lendingPriceField;
	}

	public JComboBox<String> getBikesComboBox() {
		return bikesComboBox;
	}

	public JComboBox<String> getStationsComboBox() {
		return stationsComboBox;
	}

	public DefaultComboBoxModel<String> getBikeTypesListModel() {
		return bikeTypesListModel;
	}

	public DefaultComboBoxModel<String> getStationListModel() {
		return stationListModel;
	}

	public JTextField getAmountOfBicycles() {
		return amountOfBicycles;
	}
	
	

}
