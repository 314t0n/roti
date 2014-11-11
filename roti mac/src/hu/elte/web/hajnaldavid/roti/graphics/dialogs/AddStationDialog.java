package hu.elte.web.hajnaldavid.roti.graphics.dialogs;

import hu.elte.web.hajnaldavid.roti.persistence.entities.Bicycle;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class AddStationDialog extends BasicFormDialog {

	private JTextField stationNameField;
	private JTextField stationCapacityField;

	/**
	 * Create the dialog.
	 */
	public AddStationDialog() {
		super();
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"), }));

		JLabel lblNewLabel = new JLabel("Állomás neve");
		contentPanel.add(lblNewLabel, "2, 2, right, default");

		stationNameField = new JTextField();
		contentPanel.add(stationNameField, "4, 2, fill, default");
		stationNameField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Állomás kapacitása");
		contentPanel.add(lblNewLabel_1, "2, 4, right, default");

		stationCapacityField = new JTextField();
		contentPanel.add(stationCapacityField, "4, 4, fill, default");
		stationCapacityField.setColumns(10);

	}

	public JTextField getStationNameField() {
		return stationNameField;
	}

	public JTextField getStationCapacityField() {
		return stationCapacityField;
	}

}
