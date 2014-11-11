package hu.elte.web.hajnaldavid.roti.graphics.tablemodels;

import hu.elte.web.hajnaldavid.roti.Main;
import hu.elte.web.hajnaldavid.roti.persistence.connection.CrudService;
import hu.elte.web.hajnaldavid.roti.persistence.entities.RotiEntity;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 * Generikus táblamodel
 *
 *
 * @author
 * @param <T>
 *            Az adott entitás
 * @param <S>
 *            és az ahhoz tartozó CRUD mûveletek megvalósító adatbázis objetum
 */
public class GenericTableModel<T extends RotiEntity, S extends CrudService<T>>
		extends AbstractTableModel {

	private static final Logger log4j = LogManager.getLogger(Main.class
			.getName());

	protected final S source;
	private final String[] columnNames;
	protected List<T> items;
	private final boolean[] isEditAbleColumn;

	/**
	 *
	 * @param source
	 *            CrudService-t megvalósító DAO
	 * @param columnNames
	 *            propertyNames
	 */
	public GenericTableModel(S source, String[] columnNames) {
		this.source = source;
		this.columnNames = columnNames;
		this.isEditAbleColumn = new boolean[columnNames.length];
		this.items = new ArrayList<T>();
		this.readAll();
	}

	/**
	 * Szerkeszthetõ oszlopok
	 *
	 * @param columnNumber
	 */
	public void setColumnEditAble(int columnNumber) {
		try {
			isEditAbleColumn[columnNumber] = true;
		} catch (ArrayIndexOutOfBoundsException ex) {
			log4j.error("Nem létezõ oszlop. (isEditAbleColumn)", "ERROR");
		}
	}

	@Override
	public int getRowCount() {
		return items.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return isEditAbleColumn[columnIndex];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {

		try {
			return (getRowCount() > 0) ? items.get(0).get(columnIndex)
					.getClass() : null;
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return null;

	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return (getRowCount() > rowIndex) ? items.get(rowIndex)
				.get(columnIndex) : null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		try {
			T item = items.get(rowIndex);
			System.out.println(columnIndex);
			item.set(columnIndex, aValue);

			source.update(item);
			fireTableCellUpdated(rowIndex, columnIndex);
		} catch (DatabaseException ex) {
			System.err.println(ex.getMessage());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	private void readAll() {
		try {
			items = source.readAll();
		} catch (DatabaseException ex) {
			System.err.println(ex.getMessage());
		}
	}

	public void create(T item) {
		try {
			source.create(item);
			readAll();
			fireTableDataChanged();
		} catch (DatabaseException ex) {
			System.err.println(ex.getMessage());
		}
	}

	public void delete(int rowIndex) {
		try {
			source.delete(items.get(rowIndex));
			readAll();
			fireTableDataChanged();
		} catch (DatabaseException ex) {
			System.err.println(ex.getMessage());
		}
	}

	public T read(int rowIndex) {
		try {
			return items.get(rowIndex);
		} catch (DatabaseException ex) {
			System.err.println(ex.getMessage());
			return null;
		}
	}

	public void update(T elem) {
		try {
			source.update(elem);
			readAll();
			fireTableDataChanged();
		} catch (DatabaseException ex) {
			System.err.println(ex.getMessage());
		}
	}

	public void refresh() {
		readAll();
		fireTableDataChanged();
	}

}
