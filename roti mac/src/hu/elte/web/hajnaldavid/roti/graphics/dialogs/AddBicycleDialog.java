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
	private JComboBox bikesComboBox;
	private JComboBox stationsComboBox;
	private DefaultComboBoxModel bikeTypesListModel;
	private DefaultComboBoxModel stationListModel;

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

		JLabel lblNewLabel_1 = new JLabel("K�lcs�nz�si d�j");
		contentPanel.add(lblNewLabel_1, "2, 2, right, default");

		lendingPriceField = new JTextField();
		contentPanel.add(lendingPriceField, "4, 2, fill, default");
		lendingPriceField.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("T�pus");
		contentPanel.add(lblNewLabel_2, "2, 4, right, default");

		bikeTypesListModel = new DefaultComboBoxModel();
		
		bikesComboBox = new JComboBox(bikeTypesListModel);

		contentPanel.add(bikesComboBox, "4, 4");

		stationListModel = new DefaultComboBoxModel();

		JLabel lblNewLabel_3 = new JLabel("�llom�s");
		contentPanel.add(lblNewLabel_3, "2, 6, right, default");

		stationsComboBox = new JComboBox(stationListModel);

		contentPanel.add(stationsComboBox, "4, 6");

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

	public JComboBox getBikesComboBox() {
		return bikesComboBox;
	}

	public JComboBox getStationsComboBox() {
		return stationsComboBox;
	}

	public DefaultComboBoxModel getBikeTypesListModel() {
		return bikeTypesListModel;
	}

	public DefaultComboBoxModel getStationListModel() {
		return stationListModel;
	}

}