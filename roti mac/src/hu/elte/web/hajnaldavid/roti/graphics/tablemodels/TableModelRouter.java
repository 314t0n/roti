package hu.elte.web.hajnaldavid.roti.graphics.tablemodels;

import java.util.HashMap;
import java.util.Map;

public class TableModelRouter {

	@SuppressWarnings("rawtypes")
	private Map<String, GenericTableModel> router;

	@SuppressWarnings("rawtypes")
	public TableModelRouter() {
		this.router = new HashMap<String, GenericTableModel>();
	}

	@SuppressWarnings({ "rawtypes" })
	public <T> GenericTableModel getTableModelByName(String name) {
		if (!router.containsKey(name)) {			
			throw new NullPointerException("Key not found, avaiable keys: "
					+ router.keySet().toString());
		}
		return (GenericTableModel) router.get(name);
	}

	public void addTableModel(String key, GenericTableModel model) {
		router.put(key,  model);
	}

}
