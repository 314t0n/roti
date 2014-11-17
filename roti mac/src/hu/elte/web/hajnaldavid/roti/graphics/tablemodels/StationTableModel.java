package hu.elte.web.hajnaldavid.roti.graphics.tablemodels;

import hu.elte.web.hajnaldavid.roti.logic.domainmodels.StationDomain;
import hu.elte.web.hajnaldavid.roti.persistence.connection.CrudService;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Station;

public class StationTableModel<T extends Station, S extends CrudService<T>>
		extends GenericTableModel<T, S> {

	public StationTableModel(S source, String[] columnNames) {
		super(source, columnNames);				
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(columnIndex == 3){
			return getStatus(rowIndex);
		}
		return (getRowCount() > rowIndex) ? items.get(rowIndex)
				.get(columnIndex) : null;
	}
	
	private String getStatus(int rowIndex){
		
		return ((StationDomain)source).getWarningLevelStatus(items.get(rowIndex)).toString();
		
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		
		if(columnIndex == 3){
			return new String().getClass();
		}

		try {
			return (getRowCount() > 0) ? items.get(0).get(columnIndex)
					.getClass() : null;
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return null;

	}
	
	

	
}
