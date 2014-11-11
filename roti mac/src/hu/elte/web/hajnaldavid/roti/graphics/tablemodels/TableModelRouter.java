package hu.elte.web.hajnaldavid.roti.graphics.tablemodels;

import java.util.HashMap;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

public class TableModelRouter {

	private Map<String, AbstractTableModel> router;

	public TableModelRouter() {
		this.router = new HashMap<String, AbstractTableModel>();
	}

	public AbstractTableModel getTableModelByName(String name) {
		if (!router.containsKey(name)) {
			System.out.println("nope");
			throw new NullPointerException("Key not found, avaiable keys: "
					+ router.keySet().toString());
		}
		return router.get(name);
	}

	public void addTableModel(String key, AbstractTableModel model) {
		router.put(key, model);
	}

}
